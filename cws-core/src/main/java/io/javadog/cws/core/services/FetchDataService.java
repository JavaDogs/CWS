/*
 * CWS, Cryptographic Web Store - open source Cryptographic Storage system.
 * Copyright (C) 2016-2021, JavaDog.io
 * mailto: cws AT JavaDog DOT io
 *
 * CWS is free software; you can redistribute it and/or modify it under the
 * terms of the Apache License, as published by the Apache Software Foundation.
 *
 * CWS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the Apache License for more details.
 *
 * You should have received a copy of the Apache License, version 2, along with
 * this program; If not, you can download a copy of the License
 * here: https://www.apache.org/licenses/
 */
package io.javadog.cws.core.services;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.common.Utilities;
import io.javadog.cws.api.dtos.Metadata;
import io.javadog.cws.api.requests.FetchDataRequest;
import io.javadog.cws.api.responses.FetchDataResponse;
import io.javadog.cws.core.enums.Permission;
import io.javadog.cws.core.enums.SanityStatus;
import io.javadog.cws.core.exceptions.CWSException;
import io.javadog.cws.core.model.DataDao;
import io.javadog.cws.core.model.Settings;
import io.javadog.cws.core.model.entities.DataEntity;
import io.javadog.cws.core.model.entities.DataTypeEntity;
import io.javadog.cws.core.model.entities.MetadataEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;

/**
 * <p>Business Logic implementation for the CWS FetchData request.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class FetchDataService extends Serviceable<DataDao, FetchDataResponse, FetchDataRequest> {

    public FetchDataService(final Settings settings, final EntityManager entityManager) {
        super(settings, new DataDao(entityManager));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchDataResponse perform(final FetchDataRequest request) {
        // Pre-checks, & destruction of credentials
        verifyRequest(request, Permission.FETCH_DATA);
        Arrays.fill(request.getCredential(), (byte) 0);

        // It is not possible to directly compare a sub-object against a defined
        // value, if the methods are all final - this is because it prevents the
        // Hibernate proxy, using ByteBuddy, to override the method. Hence, the
        // Object it should be compared against is first read out - since this
        // will help overriding the proxying.
        //   See: https://github.com/JavaDogs/cws/issues/45
        final MetadataEntity root = findRootMetadata(request);

        if (root == null) {
            throw new CWSException(ReturnCode.IDENTIFICATION_WARNING, "No information could be found for the given Id.");
        }

        final DataTypeEntity folder = dao.findDataTypeByName(Constants.FOLDER_TYPENAME);
        final FetchDataResponse response;

        if (Objects.equals(folder, root.getType())) {
            final int pageNumber = request.getPageNumber();
            final int pageSize = request.getPageSize();
            final List<MetadataEntity> found = dao.findMetadataByMemberAndFolder(member, root.getId(), pageNumber, pageSize);
            final long count = dao.countFolderContent(root.getId());
            response = prepareResponse(root.getExternalId(), found, count);
        } else {
            response = readCompleteDataObject(root);
        }

        return response;
    }

    /**
     * <p>When getting a request, we first need to find out what data it is that
     * is being requested. As all we have to go on is Id's, the information can
     * be hard to specify. Hence, we simply start by looking up the base
     * Metadata entity for the request, which can be either the root (if only a
     * Circle is given) folder, a folder or a Data record (if DataId is
     * given).</p>
     *
     * <p>If no record is found, then a null value is returned.</p>
     *
     * @param request Request Object with Circle & Data Id's
     * @return Root Metadata Record, to base the rest of the processing on
     */
    private MetadataEntity findRootMetadata(final FetchDataRequest request) {
        final String circleId = request.getCircleId();
        final String dataId = request.getDataId();
        final String dataName = request.getDataName();
        final MetadataEntity found;

        if (dataId != null) {
            found = dao.findMetadataByMemberAndExternalId(member.getId(), dataId);
        } else if (dataName != null) {
            found = dao.findMetadataByMemberAndName(member.getId(), dataName);
        } else {
            found = dao.findRootByMemberCircle(member.getId(), circleId);
        }

        return found;
    }

    private FetchDataResponse readCompleteDataObject(final MetadataEntity metadata) {
        // Following Query will read out a specific Data Record with meta data
        // information, if the person is allowed, which includes checks for
        // Circle Membership and right TrustLevel of the Member. If no Entity
        // is found, then there can be multiple reasons.
        final DataEntity entity = dao.findDataByMemberAndExternalId(member, metadata.getExternalId());
        final var response = new FetchDataResponse();
        final MetadataEntity parent = dao.find(MetadataEntity.class, metadata.getParentId());
        final var metaData = DataDao.convert(metadata, parent.getExternalId());
        final List<Metadata> metadataList = new ArrayList<>(1);
        metadataList.add(metaData);

        if (entity != null) {
            final String checksum = crypto.generateChecksum(entity.getData());
            if (Objects.equals(checksum, entity.getChecksum())) {
                final byte[] bytes = decryptData(entity);

                // The Object may have the Status Failed, but was corrected. But
                // as we're going to update the Object anyway, let's just update
                // it with an Ok flag also.
                entity.setSanityStatus(SanityStatus.OK);
                response.setMetadata(metadataList);
                response.setRecords(1L);
                response.setData(bytes);
            } else {
                // Let's update the DB with the information that the data is
                // invalid, and return the error.
                entity.setSanityStatus(SanityStatus.FAILED);

                // Note, no Exception is thrown here, since the DB changes have
                // not yet been completed.
                response.setReturnCode(ReturnCode.INTEGRITY_ERROR);
                response.setReturnMessage("The Encrypted Data Checksum is invalid, the data appears to have been corrupted.");
            }

            // Regardless what the Status is, let's update the Object, so the
            // information is persisted. This will also prevent that the Object
            // is checked too soon.
            entity.setSanityChecked(Utilities.newDate());
            dao.persist(entity);
        } else {
            response.setMetadata(metadataList);
        }

        return response;
    }

    private static FetchDataResponse prepareResponse(final String folderId, final Collection<MetadataEntity> records, final long count) {
        final List<Metadata> list = new ArrayList<>(records.size());

        for (final MetadataEntity metadata : records) {
            final Metadata data = DataDao.convert(metadata, folderId);
            list.add(data);
        }

        final var response = new FetchDataResponse();
        response.setMetadata(list);
        response.setRecords(count);

        return response;
    }
}

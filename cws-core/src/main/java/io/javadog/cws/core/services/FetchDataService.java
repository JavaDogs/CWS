/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.services;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.dtos.Metadata;
import io.javadog.cws.api.requests.FetchDataRequest;
import io.javadog.cws.api.responses.FetchDataResponse;
import io.javadog.cws.core.enums.Permission;
import io.javadog.cws.core.enums.SanityStatus;
import io.javadog.cws.core.jce.SecretCWSKey;
import io.javadog.cws.core.model.Settings;
import io.javadog.cws.core.model.entities.DataEntity;
import io.javadog.cws.core.model.entities.MetadataEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class FetchDataService extends Serviceable<FetchDataResponse, FetchDataRequest> {

    public FetchDataService(final Settings settings, final EntityManager entityManager) {
        super(settings, entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchDataResponse perform(final FetchDataRequest request) {
        verifyRequest(request, Permission.FETCH_DATA);
        final MetadataEntity root = findRootMetadata(request);
        final FetchDataResponse response;

        if (root != null) {
            if (Objects.equals(root.getType().getName(), Constants.FOLDER_TYPENAME)) {
                final int pageNumber = request.getPageNumber();
                final int pageSize = request.getPageSize();
                final List<MetadataEntity> found = dao.findMetadataByMemberAndFolder(member, root, pageNumber, pageSize);
                response = prepareResponse(root.getExternalId(), found);
            } else {
                response = readCompleteDataObject(root);
            }
        } else {
            response = new FetchDataResponse(ReturnCode.IDENTIFICATION_WARNING, "No information could be found for the given Id.");
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
        final MetadataEntity found;

        if (dataId != null) {
            found = dao.findMetaDataByMemberAndExternalId(member.getId(), dataId);
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
        final DataEntity entity = dao.findDataByMemberAndExternalId(member.getId(), metadata.getExternalId());
        final FetchDataResponse response = new FetchDataResponse();
        final MetadataEntity parent = dao.find(MetadataEntity.class, metadata.getParentId());
        final Metadata metaData = convert(metadata, parent.getExternalId());

        if (entity != null) {
            final String checksum = crypto.generateChecksum(entity.getData());
            if (Objects.equals(checksum, entity.getChecksum())) {
                final SecretCWSKey key = extractCircleKey(entity);
                key.setSalt(entity.getInitialVector());
                final byte[] bytes = crypto.decrypt(key, entity.getData());
                final List<Metadata> metadataList = new ArrayList<>(1);
                metadataList.add(metaData);

                response.setMetadata(metadataList);
                response.setData(bytes);
            } else {
                // Let's update the DB with the information that the data is
                // invalid, and return the error.
                entity.setSanityStatus(SanityStatus.FAILED);
                entity.setSanityChecked(new Date());
                dao.persist(entity);

                response.setReturnCode(ReturnCode.INTEGRITY_ERROR);
                response.setReturnMessage("The Encrypted Data Checksum is invalid, the data appears to have been corrupted.");
            }
        }

        return response;
    }

    private static FetchDataResponse prepareResponse(final String folderId, final List<MetadataEntity> records) {
        final List<Metadata> list = new ArrayList<>(records.size());

        for (final MetadataEntity metadata : records) {
            final Metadata data = convert(metadata, folderId);
            list.add(data);
        }

        final FetchDataResponse response = new FetchDataResponse();
        response.setMetadata(list);

        return response;
    }

    private static Metadata convert(final MetadataEntity entity, final String folderId) {
        final Metadata metaData = new Metadata();

        metaData.setDataId(entity.getExternalId());
        metaData.setCircleId(entity.getCircle().getExternalId());
        metaData.setFolderId(folderId);
        metaData.setDataName(entity.getName());
        metaData.setTypeName(entity.getType().getName());
        metaData.setAdded(entity.getAdded());

        return metaData;
    }
}

/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.services;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.dtos.Sanity;
import io.javadog.cws.api.requests.SanityRequest;
import io.javadog.cws.api.responses.SanityResponse;
import io.javadog.cws.core.enums.Permission;
import io.javadog.cws.core.model.Settings;
import io.javadog.cws.core.model.entities.DataEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class SanityService extends Serviceable<SanityResponse, SanityRequest> {

    public SanityService(final Settings settings, final EntityManager entityManager) {
        super(settings, entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SanityResponse perform(final SanityRequest request) {
        verifyRequest(request, Permission.SANITY);
        final List<DataEntity> found = findRecords(request);
        final List<Sanity> sanities = convertRecords(found);
        final SanityResponse response = new SanityResponse();
        response.setSanities(sanities);

        return response;
    }

    private List<DataEntity> findRecords(final SanityRequest request) {
        final Date since = (request.getSince() == null) ? new Date(0L) : request.getSince();
        final List<DataEntity> found;

        if (request.getCircleId() != null) {
            // Find for specific Circle
            found = dao.findFailedRecords(request.getCircleId(),since);
        } else if (Objects.equals(member.getName(), Constants.ADMIN_ACCOUNT)) {
            // The System Administrator is allowed to retrieve all records for
            // all Circles.
            found = dao.findFailedRecords(since);
        } else {
            // Find for specific Member, which will retrieve all records which
            // the member is Administrator for
            found = dao.findFailedRecords(member, since);
        }

        return found;
    }

    private static List<Sanity> convertRecords(final List<DataEntity> found) {
        final List<Sanity> sanities = new ArrayList<>();

        for (final DataEntity entity : found) {
            final Sanity sanity = new Sanity();
            sanity.setDataId(entity.getMetadata().getExternalId());
            sanity.setChanged(entity.getSanityChecked());
            sanities.add(sanity);
        }

        return sanities;
    }
}

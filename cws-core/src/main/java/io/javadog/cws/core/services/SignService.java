/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.services;

import io.javadog.cws.api.requests.SignRequest;
import io.javadog.cws.api.responses.SignResponse;
import io.javadog.cws.common.Settings;
import io.javadog.cws.core.Permission;
import io.javadog.cws.core.Serviceable;
import io.javadog.cws.model.entities.SignatureEntity;

import javax.persistence.EntityManager;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class SignService extends Serviceable<SignResponse, SignRequest> {

    public SignService(final Settings settings, final EntityManager entityManager) {
        super(settings, entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SignResponse perform(final SignRequest request) {
        verifyRequest(request, Permission.CREATE_SIGNATURE);
        final SignResponse response = new SignResponse();

        final String signature = crypto.sign(keyPair.getPrivate().getKey(), request.getData());
        final String checksum = crypto.generateChecksum(signature);
        final SignatureEntity existing = dao.findByChecksum(checksum);

        if (existing == null) {
            final SignatureEntity entity = new SignatureEntity();
            entity.setMember(member);
            entity.setChecksum(checksum);
            entity.setExpires(request.getExpires());
            dao.persist(entity);
        } else {
            response.setReturnMessage("This document has already been signed.");
        }

        response.setSignature(signature);
        return response;
    }
}

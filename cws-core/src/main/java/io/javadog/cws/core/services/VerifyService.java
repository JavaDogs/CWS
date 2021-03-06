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

import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.common.Utilities;
import io.javadog.cws.api.requests.VerifyRequest;
import io.javadog.cws.api.responses.VerifyResponse;
import io.javadog.cws.core.enums.Permission;
import io.javadog.cws.core.exceptions.CWSException;
import io.javadog.cws.core.exceptions.IdentificationException;
import io.javadog.cws.core.model.Settings;
import io.javadog.cws.core.model.SignatureDao;
import io.javadog.cws.core.model.entities.SignatureEntity;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import javax.persistence.EntityManager;

/**
 * <p>Business Logic implementation for the CWS Verify request.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class VerifyService extends Serviceable<SignatureDao, VerifyResponse, VerifyRequest> {

    public VerifyService(final Settings settings, final EntityManager entityManager) {
        super(settings, new SignatureDao(entityManager));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VerifyResponse perform(final VerifyRequest request) {
        // Pre-checks, & destruction of credentials
        verifyRequest(request, Permission.VERIFY_SIGNATURE);
        Arrays.fill(request.getCredential(), (byte) 0);

        final byte[] signature = Base64.getDecoder().decode(request.getSignature());
        final String checksum = crypto.generateChecksum(signature);
        final SignatureEntity entity = dao.findByChecksum(checksum);

        if (entity == null) {
            throw new IdentificationException("It was not possible to find the Signature.");
        }

        final LocalDateTime expires = entity.getExpires();
        if ((expires != null) && expires.isBefore(Utilities.newDate())) {
            throw new CWSException(ReturnCode.SIGNATURE_WARNING, "The Signature has expired.");
        }

        final var publicKey = crypto.dearmoringPublicKey(entity.getPublicKey());
        final boolean verified = crypto.verify(publicKey, request.getData(), signature);

        if (verified) {
            entity.setVerifications(entity.getVerifications() + 1);
            dao.persist(entity);
        }

        final var response = new VerifyResponse("The signature has successfully been verified.");
        response.setVerified(verified);

        return response;
    }
}

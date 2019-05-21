/*
 * CWS, Cryptographic Web Store - open source Cryptographic Storage system.
 * Copyright (C) 2016-2019, JavaDog.io
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
package io.javadog.cws.api.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.javadog.cws.api.TestUtilities;
import io.javadog.cws.api.common.Constants;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class FetchTrusteeRequestTest {

    @Test
    public void testClassflow() {
        final String circleId = UUID.randomUUID().toString();

        final FetchTrusteeRequest request = new FetchTrusteeRequest();
        request.setAccountName(Constants.ADMIN_ACCOUNT);
        request.setCredential(TestUtilities.convert(Constants.ADMIN_ACCOUNT));
        request.setCircleId(circleId);

        final Map<String, String> errors = request.validate();
        assertTrue(errors.isEmpty());
        assertEquals(Constants.ADMIN_ACCOUNT, request.getAccountName());
        assertEquals(Constants.ADMIN_ACCOUNT, TestUtilities.convert(request.getCredential()));
        assertEquals(circleId, request.getCircleId());
    }

    @Test
    public void testEmptyClass() {
        final FetchTrusteeRequest request = new FetchTrusteeRequest();
        final Map<String, String> errors = request.validate();

        assertFalse(errors.isEmpty());
        assertEquals(2, errors.size());
        assertEquals("The Session (Credential) is missing.", errors.get(Constants.FIELD_CREDENTIAL));
        assertEquals("The Circle Id is missing or invalid.", errors.get(Constants.FIELD_CIRCLE_ID));
    }

    @Test
    public void testClassWithInvalidValues() {
        final String circleId = "Invalid Circle Id";

        final FetchTrusteeRequest request = new FetchTrusteeRequest();
        request.setCircleId(circleId);

        final Map<String, String> errors = request.validate();
        assertFalse(errors.isEmpty());
        assertEquals(2, errors.size());
        assertEquals("The Session (Credential) is missing.", errors.get(Constants.FIELD_CREDENTIAL));
        assertEquals("The Circle Id is missing or invalid.", errors.get(Constants.FIELD_CIRCLE_ID));
    }
}

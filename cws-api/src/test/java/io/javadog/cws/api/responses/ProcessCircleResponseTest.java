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
package io.javadog.cws.api.responses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.javadog.cws.api.common.ReturnCode;
import java.util.UUID;
import org.junit.jupiter.api.Test;

/**
 * @author Kim Jensen
 * @since CWS 1.0
 */
class ProcessCircleResponseTest {

    @Test
    void testClassFlow() {
        final String circleId = UUID.randomUUID().toString();

        final ProcessCircleResponse response = new ProcessCircleResponse();
        response.setCircleId(circleId);

        assertEquals(ReturnCode.SUCCESS.getCode(), response.getReturnCode());
        assertEquals("Ok", response.getReturnMessage());
        assertTrue(response.isOk());
        assertEquals(circleId, response.getCircleId());
    }

    @Test
    void testMessageConstructor() {
        final String message = "Request was successfully processed.";
        final ProcessCircleResponse response = new ProcessCircleResponse(message);

        assertEquals(ReturnCode.SUCCESS.getCode(), response.getReturnCode());
        assertEquals(message, response.getReturnMessage());
        assertNull(response.getCircleId());
    }

    @Test
    void testError() {
        final String msg = "ProcessCircle Request failed due to Verification Problems.";
        final ProcessCircleResponse response = new ProcessCircleResponse(ReturnCode.VERIFICATION_WARNING, msg);

        assertEquals(ReturnCode.VERIFICATION_WARNING.getCode(), response.getReturnCode());
        assertEquals(msg, response.getReturnMessage());
        assertFalse(response.isOk());
        assertNull(response.getCircleId());
    }
}

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

import io.javadog.cws.api.common.ReturnCode;
import org.junit.jupiter.api.Test;

/**
 * @author Kim Jensen
 * @since CWS 1.0
 */
final class CwsResponseTest {

    @Test
    void testClassFlow() {
        final CwsResponse response = new CwsResponse();
        response.setReturnCode(ReturnCode.ERROR);
        response.setReturnMessage(ReturnCode.ERROR.getDescription());

        assertEquals(ReturnCode.ERROR.getCode(), response.getReturnCode());
        assertEquals(ReturnCode.ERROR.getDescription(), response.getReturnMessage());
        assertFalse(response.isOk());
    }

    @Test
    void testError() {
        final String msg = "FetchCircle Request failed due to Verification Problems.";
        final CwsResponse response = new CwsResponse(ReturnCode.VERIFICATION_WARNING, msg);

        assertEquals(ReturnCode.VERIFICATION_WARNING.getCode(), response.getReturnCode());
        assertEquals(msg, response.getReturnMessage());
        assertFalse(response.isOk());
    }
}

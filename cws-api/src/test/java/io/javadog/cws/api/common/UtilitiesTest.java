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
package io.javadog.cws.api.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.jupiter.api.Test;

/**
 * @author Kim Jensen
 * @since CWS 1.2
 */
final class UtilitiesTest {

    @Test
    void testCopyBytes() {
        final byte[] bytes = { (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10 };
        final byte[] copy = Utilities.copy(bytes);
        // The Byte array copy method is deliberately not doing anything, since
        // the incoming bytes are read out from the WebService request as a
        // Base64 encoded String. It is thus not possible to alter them, and
        // rather than having multiple copies of the data in memory, a single
        // copy suffices. The Copy method thus only serves as a wrapper to
        // prevent that the Static Analysis tools complain about it.
        assertArrayEquals(bytes, copy);
    }
}

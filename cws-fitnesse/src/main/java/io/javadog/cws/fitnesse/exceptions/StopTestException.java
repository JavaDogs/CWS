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
package io.javadog.cws.fitnesse.exceptions;

import io.javadog.cws.api.common.Constants;

/**
 * <p>FitNesse requires that an exception with the first part of the name being
 * &quot;StopTest&quot; is used if an error occurs and the test should be
 * stopped.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class StopTestException extends RuntimeException {

    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    public StopTestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public StopTestException(final String message) {
        super(message);
    }

    public StopTestException(final Throwable cause) {
        super(cause);
    }
}

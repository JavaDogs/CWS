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

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.ReturnCode;

/**
 * <p>The Response Object from the processing Trustee request. The Object is not
 * containing any other response information than the general.</p>
 *
 * <p>Please see {@link CwsResponse} for information about the result of the
 * processing.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class ProcessTrusteeResponse extends CwsResponse {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public ProcessTrusteeResponse() {
        // Empty Constructor, required for WebServices
    }

    /**
     * Constructor for more detailed responses.
     *
     * @param returnMessage The CWS Return Message
     */
    public ProcessTrusteeResponse(final String returnMessage) {
        super(returnMessage);
    }

    /**
     * Error Constructor, used if an error occurred, and the request could not
     * complete successfully.
     *
     * @param returnCode    The CWS Return Code
     * @param returnMessage The CWS Return Message
     */
    public ProcessTrusteeResponse(final ReturnCode returnCode, final String returnMessage) {
        super(returnCode, returnMessage);
    }
}

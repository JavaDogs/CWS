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
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.io.Serializable;

/**
 * <p>General Response Object, embedded in all other Response Objects, as it
 * contains the processing result, i.e. return code &amp; message. If everything
 * went good, the return code will the code 200, same as the HTTP protocol will
 * return if everything went well. The return message will simple be 'Ok' in
 * this case.</p>
 *
 * <p>If a problem occurred, either a warning (problem which can be corrected by
 * the invoking system/member), or an error (internal problem, most likely a
 * resource issue). The return code &amp; message should hopefully provide
 * enough information for the System Administrator to correct the problem.</p>
 *
 * <p>The class {@link ReturnCode} for more information, and clarification of
 * the individual warnings or errors which may occur.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
@JsonbPropertyOrder({ Constants.FIELD_RETURN_CODE, Constants.FIELD_RETURN_MESSAGE })
public class CwsResponse implements Serializable {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    @JsonbProperty(Constants.FIELD_RETURN_CODE)
    private int returnCode = ReturnCode.SUCCESS.getCode();

    @JsonbProperty(Constants.FIELD_RETURN_MESSAGE)
    private String returnMessage = "Ok";

    // =========================================================================
    // Object Constructors
    // =========================================================================

    /**
     * Empty Constructor, to use if the setters are invoked. This is required
     * for WebServices to work properly.
     */
    public CwsResponse() {
        // Required for WebServices to work. Comment added to please Sonar.
    }

    /**
     * Constructor for more detailed responses.
     *
     * @param returnMessage The CWS Return Message
     */
    public CwsResponse(final String returnMessage) {
        this.returnMessage = returnMessage;
    }

    /**
     * Error Constructor, used if an error occurred, and the request could not
     * complete successfully.
     *
     * @param returnCode    The CWS Return Code
     * @param returnMessage The CWS Return Message
     */
    public CwsResponse(final ReturnCode returnCode, final String returnMessage) {
        this.returnCode = returnCode.getCode();
        this.returnMessage = returnMessage;
    }

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public final void setReturnCode(final ReturnCode returnCode) {
        this.returnCode = returnCode.getCode();
    }

    public final int getReturnCode() {
        return returnCode;
    }

    public final void setReturnMessage(final String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public final String getReturnMessage() {
        return returnMessage;
    }

    public final boolean isOk() {
        return returnCode == ReturnCode.SUCCESS.getCode();
    }
}

/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-common)
 * =============================================================================
 */
package io.javadog.cws.common.exceptions;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.ReturnCode;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class ModelException extends CWSException {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    public ModelException(final ReturnCode returnCode, final String message) {
        super(returnCode, message);
    }

    public ModelException(final ReturnCode returnCode, final String message, final Throwable cause) {
        super(returnCode, message, cause);
    }
}

/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-client)
 * =============================================================================
 */
package io.javadog.cws.client;

import io.javadog.cws.api.common.Constants;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class CWSClientException extends RuntimeException {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    public CWSClientException(final Throwable cause) {
        super(cause);
    }
}
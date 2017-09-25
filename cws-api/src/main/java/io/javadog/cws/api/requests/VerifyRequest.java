/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-api)
 * =============================================================================
 */
package io.javadog.cws.api.requests;

import static io.javadog.cws.api.common.Utilities.copy;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.dtos.Authentication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verifyRequest", propOrder = { "signature", "data" })
public final class VerifyRequest extends Authentication {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;
    private static final String FIELD_SIGNATURE = "signature";
    private static final String FIELD_DATA = "data";

    @XmlElement(name = FIELD_SIGNATURE, required = true)
    private String signature = null;

    @XmlElement(name = FIELD_DATA, required = true)
    private byte[] data = null;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setSignature(final String signature) {
        ensureNotNull(FIELD_SIGNATURE, signature);

        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public void setData(final byte[] data) {
        ensureNotNull(FIELD_DATA, data);
        this.data = copy(data);
    }

    public byte[] getData() {
        return copy(data);
    }

    // =========================================================================
    // Standard Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> errors = super.validate();

        checkNotNullOrEmpty(errors, FIELD_SIGNATURE, signature, "The Signature is missing.");
        checkNotNull(errors, FIELD_DATA, data, "The Data Object to check the Signature against is missing.");

        return errors;
    }
}

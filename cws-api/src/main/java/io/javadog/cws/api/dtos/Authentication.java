/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-api)
 * =============================================================================
 */
package io.javadog.cws.api.dtos;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.CredentialType;
import io.javadog.cws.api.common.Verifiable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Common Object for All requests to the CWS, which contain enough information
 * to properly authenticate a member and thus authorize this member to access
 * the System.
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authentication", propOrder = { "account", "credentialType", "credential" })
public class Authentication extends Verifiable {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;
    private static final String FIELD_ACCOUNT = "account";
    private static final String FIELD_TYPE = "credentialType";
    private static final String FIELD_CREDENTIAL = "credential";
    private static final int NAME_MAX_LENGTH = 75;

    @XmlElement(name = FIELD_ACCOUNT, required = true)
    private String account = null;

    @XmlElement(name = FIELD_TYPE, required = true)
    private CredentialType credentialType = null;

    @XmlElement(name = FIELD_CREDENTIAL, required = true)
    private char[] credential = null;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    @NotNull
    @Size(min = 1, max = NAME_MAX_LENGTH)
    public void setAccount(final String account) {
        ensureNotNull(FIELD_ACCOUNT, account);
        ensureNotEmptyOrTooLong(FIELD_ACCOUNT, account, NAME_MAX_LENGTH);
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    /**
     * Sets the type of Credentials to be used, either a PassPhrase (default) or
     * an Asymmetric Key (RSA based Private Key or Public/Private Key Pair).
     *
     * @param credentialType Member Credential Type, i.e. Key or PassPhrase
     */
    @NotNull
    public void setCredentialType(final CredentialType credentialType) {
        ensureNotNull(FIELD_TYPE, credentialType);
        this.credentialType = credentialType;
    }

    public CredentialType getCredentialType() {
        return credentialType;
    }

    /**
     * <p>Sets the Member's Credentials. This can be either a PassPhrase
     * (default) or an Asymmetric Key (RSA based Private Key or Public/Private
     * Key Pair).</p>
     *
     * <p>If the Credentials is a Key, then the information given must be
     * armored, i.e. Base64 encoded.</p>
     *
     * @param credential Member Credentials, i.e. Key or PassPhrase
     */
    @NotNull
    public void setCredential(final char[] credential) {
        ensureNotNull(FIELD_CREDENTIAL, credential);
        // Arrays should not be referenced directly, as it can lead to problems
        // with encapsulation. However, in our case it is important for 2
        // reasons:
        //   1. All requests is coming in via a WebService (REST/SOAP), and it
        //      is therefore not likely that any changes can be made
        //      uncontrolled.
        //   2. The information stored here is highly sensitive, it is therefore
        //      important that we can overwrite it ASAP, when we no longer need
        //      to reference it. The Garbage Collector will clean up the mess,
        //      but as we don't know when the Garbage Collector will run, it is
        //      better to control this ourselves.
        this.credential = credential;
    }

    public char[] getCredential() {
        // See comment above.
        return credential;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> errors = new ConcurrentHashMap<>();

        checkNotNull(errors, FIELD_ACCOUNT, account, "Account is missing, null or invalid.");
        checkNotEmpty(errors, FIELD_ACCOUNT, account, "Account may not be empty.");
        checkNotTooLong(errors, FIELD_ACCOUNT, account, NAME_MAX_LENGTH, "Name is exceeding the maximum allowed length " + NAME_MAX_LENGTH + '.');
        checkNotNull(errors, FIELD_CREDENTIAL, credential, "Credential is missing, null or invalid.");
        checkNotNull(errors, FIELD_TYPE, credentialType, "CredentialType is missing, null or invalid.");

        return errors;
    }
}

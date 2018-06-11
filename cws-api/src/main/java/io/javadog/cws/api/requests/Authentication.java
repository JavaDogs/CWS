/*
 * =============================================================================
 * Copyright (c) 2016-2018, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-api)
 * =============================================================================
 */
package io.javadog.cws.api.requests;

import static io.javadog.cws.api.common.Utilities.copy;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.CredentialType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Base Authentication Object for all incoming Requests. It contains the
 * name of the Account, plus the credentials to unlock the Account. Both the
 * name and credentials are mandatory, whereas the type of credential is
 * optional with a fallback to PassPhrase.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authentication", propOrder = { Constants.FIELD_ACCOUNT_NAME, Constants.FIELD_CREDENTIAL, Constants.FIELD_CREDENTIALTYPE })
public class Authentication extends Verifiable {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    @NotNull
    @Size(min = 1, max = Constants.MAX_NAME_LENGTH)
    @XmlElement(name = Constants.FIELD_ACCOUNT_NAME, required = true)
    private String accountName = null;

    @NotNull
    @XmlElement(name = Constants.FIELD_CREDENTIAL, required = true)
    private byte[] credential = null;

    @NotNull
    @XmlElement(name = Constants.FIELD_CREDENTIALTYPE, required = true)
    private CredentialType credentialType = CredentialType.PASSPHRASE;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public final void setAccountName(final String accountName) {
        this.accountName = accountName;
    }

    public final String getAccountName() {
        return accountName;
    }

    public final void setCredential(final byte[] credential) {
        this.credential = copy(credential);
    }

    public final byte[] getCredential() {
        return copy(credential);
    }

    public final void setCredentialType(final CredentialType credentialType) {
        this.credentialType = credentialType;
    }

    public final CredentialType getCredentialType() {
        return credentialType;
    }

    // =========================================================================
    // Standard Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> errors = new ConcurrentHashMap<>();

        checkNotNullEmptyOrTooLong(errors, Constants.FIELD_ACCOUNT_NAME, accountName, Constants.MAX_NAME_LENGTH, "AccountName is missing, null or invalid.");
        checkNotNullOrEmpty(errors, Constants.FIELD_CREDENTIAL, credential, "The Credential is missing.");
        if (credentialType == null) {
            credentialType = CredentialType.PASSPHRASE;
        }

        return errors;
    }
}

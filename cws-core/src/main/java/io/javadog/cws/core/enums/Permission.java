/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.enums;

import io.javadog.cws.api.common.TrustLevel;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public enum Permission {

    /**
     * The list of Circles is open to everyone to retrieve.
     */
    FETCH_CIRCLE(TrustLevel.ALL, "Fetch Circles."),

    /** The list of Members is open to everyone to retrieve, but there is some
     * settings which may limit the listing.
     */
    FETCH_MEMBER(TrustLevel.ALL, "Fetch Members."),

    /**
     * Only someone who is a Member of a Circle, may read the data in it.
     */
    FETCH_DATA(TrustLevel.READ, "Fetch Data."),

    /**
     * Only if a Member is present in a Circle, may the member receive the list
     * of DataTypes present.
     */
    FETCH_DATA_TYPE(TrustLevel.READ, "Fetch Data Types."),

    /**
     * Fetching Signatures is a Member only feature, whereby a Member may
     * receive a list their own Signatures, with status information.
     */
    FETCH_SIGNATURES(TrustLevel.ALL, "Fetch Signatures."),

    /**
     * Everybody is allowed to create Circles, but only Circle Administrators
     * may be allowed to add Members, update or remove Members.
     */
    PROCESS_CIRCLE(TrustLevel.ALL, "Process a Circle."),

    /**
     * Processing of Members is limited to the System Administrator. However,
     * any given Member may update their own settings, which include accountName
     * and Passphrase.
     */
    PROCESS_MEMBER(TrustLevel.ALL, "Process a Member."),

    /**
     * Only someone with writing permissions in a Circle, may add, update or
     * remove data from it.
     */
    PROCESS_DATA(TrustLevel.WRITE, "Process Data."),

    /**
     * DataTypes is used, if the Clients wishes to have more control over what
     * an Object represents, which may be needed if multiple clients is
     * accessing the same instance without sharing other information. However,
     * only Circle Administrators may add new DataTypes for Circles.
     */
    PROCESS_DATA_TYPE(TrustLevel.WRITE, "Process Data Type."),

    /**
     * Anyone may create a Digital Signature for a given Document.
     */
    CREATE_SIGNATURE(TrustLevel.ALL, "Create Digital Signature."),

    /**
     * Anyone may verify a digital Signature for a Document.
     */
    VERIFY_SIGNATURE(TrustLevel.ALL, "Verify Digital Signature."),

    /**
     * Altering the settings, is only allowed to be performed by the System
     * Administrator, as part of the setup or later altering the system.
     */
    SETTING(TrustLevel.SYSOP, "Process Settings."),

    /**
     * The Sanity checks is something, which is limited to the System or Circle
     * Administrators, the System Administrator may read the information for all
     * Circles, but Circle Administrators are only allowed to read the data for
     * their own Circle(s).
     */
    SANITY(TrustLevel.ADMIN, "Process last Sanity Check.");

    // =========================================================================
    // Internal Functionality
    // =========================================================================

    private final TrustLevel trustLevel;
    private final String description;

    Permission(final TrustLevel trustLevel, final String description) {
        this.trustLevel = trustLevel;
        this.description = description;
    }

    public TrustLevel getTrustLevel() {
        return trustLevel;
    }

    public String getDescription() {
        return description;
    }
}

/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-model)
 * =============================================================================
 */
package io.javadog.cws.model.entities;

import io.javadog.cws.common.enums.KeyAlgorithm;
import io.javadog.cws.common.enums.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Entity
@Table(name = "keys")
public class KeyEntity extends CWSEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", nullable = false, updatable = false, length = 10)
    private KeyAlgorithm algorithm = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expires", updatable = false)
    private Date expires = null;

    @Column(name = "grace_period", updatable = false)
    private Integer gracePeriod = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setAlgorithm(final KeyAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public KeyAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setExpires(final Date expires) {
        this.expires = (expires != null) ? new Date(expires.getTime()) : null;
    }

    public Date getExpires() {
        return (expires != null) ? new Date(expires.getTime()) : null;
    }

    public void setGracePeriod(final Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Integer getGracePeriod() {
        return gracePeriod;
    }
}

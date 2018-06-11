/*
 * =============================================================================
 * Copyright (c) 2016-2018, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.model.entities;

import static io.javadog.cws.api.common.Constants.MAX_STRING_LENGTH;
import static io.javadog.cws.api.common.Utilities.copy;

import io.javadog.cws.core.enums.SanityStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * <p>CWS Data Entity, maps the Data table from the Database.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "data.findByMetadata",
                query = "select d " +
                        "from DataEntity d " +
                        "where d.metadata = :metadata"),
        @NamedQuery(name = "data.findAllWithState",
                query = "select d " +
                        "from DataEntity d " +
                        "where d.sanityStatus = :status" +
                        "  and d.sanityChecked >= :since"),
        @NamedQuery(name = "data.findAllWithStateForCircle",
                query = "select d " +
                        "from DataEntity d " +
                        "where d.sanityStatus = :status" +
                        "  and d.sanityChecked >= :since" +
                        "  and d.metadata.circle.externalId = :externalId"),
        @NamedQuery(name = "data.findAllWithStateForMember",
                query = "select d " +
                        "from DataEntity d, TrusteeEntity t " +
                        "where t.circle = d.metadata.circle" +
                        "  and d.sanityStatus = :status" +
                        "  and d.sanityChecked >= :since" +
                        "  and t.member = :member"),
        @NamedQuery(name = "data.findByMemberAndExternalId",
                query = "select d " +
                        "from DataEntity d," +
                        "     TrusteeEntity t " +
                        "where d.metadata.circle.id = t.circle.id" +
                        "  and d.metadata.externalId = :externalId" +
                        "  and t.member = :member" +
                        "  and t.trustLevel in :trustLevels"),
        @NamedQuery(name = "data.findIdsForSanityCheck",
                query = "select d.id " +
                        "from DataEntity d " +
                        "where d.sanityStatus = :status" +
                        "  and d.sanityChecked <= :date " +
                        "order by d.id asc")
})
@Table(name = "cws_data")
public class DataEntity extends CWSEntity {

    @OneToOne(targetEntity = MetadataEntity.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id", nullable = false, updatable = false)
    private MetadataEntity metadata = null;

    @ManyToOne(targetEntity = KeyEntity.class, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "key_id", referencedColumnName = "id", nullable = false, updatable = false)
    private KeyEntity key = null;

    @Column(name = "encrypted_data", nullable = false)
    private byte[] data = null;

    @Column(name = "initial_vector", nullable = false, updatable = false, length = MAX_STRING_LENGTH)
    private String initialVector = null;

    @Column(name = "checksum", nullable = false, length = MAX_STRING_LENGTH)
    private String checksum = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "sanity_status", nullable = false, length = MAX_STRING_LENGTH)
    private SanityStatus sanityStatus = null;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sanity_checked", nullable = false)
    private Date sanityChecked = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public final void setMetadata(final MetadataEntity object) {
        this.metadata = object;
    }

    public final MetadataEntity getMetadata() {
        return metadata;
    }

    public final void setKey(final KeyEntity key) {
        this.key = key;
    }

    public final KeyEntity getKey() {
        return key;
    }

    public final void setData(final byte[] data) {
        this.data = copy(data);
    }

    public final byte[] getData() {
        return copy(data);
    }

    public final void setInitialVector(final String initialVector) {
        this.initialVector = initialVector;
    }

    public final String getInitialVector() {
        return initialVector;
    }

    public final void setChecksum(final String checksum) {
        this.checksum = checksum;
    }

    public final String getChecksum() {
        return checksum;
    }

    public final void setSanityStatus(final SanityStatus sanityStatus) {
        this.sanityStatus = sanityStatus;
    }

    public final SanityStatus getSanityStatus() {
        return sanityStatus;
    }

    public final void setSanityChecked(final Date sanityChecked) {
        this.sanityChecked = copy(sanityChecked);
    }

    public final Date getSanityChecked() {
        return copy(sanityChecked);
    }
}

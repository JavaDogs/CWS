/*
 * =============================================================================
 * Copyright (c) 2016-2018, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.model.entities;

import static io.javadog.cws.api.common.Constants.MAX_NAME_LENGTH;
import static io.javadog.cws.api.common.Utilities.copy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * <p>CWS Circle Entity, maps the Circle table from the Database.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Entity
@Table(name = "cws_circles")
@NamedQueries(
        @NamedQuery(name = "circle.findByName",
                    query = "select c from CircleEntity c " +
                            "where lower(name) = lower(:name)")
)
public class CircleEntity extends Externable {

    @Column(name = "name", unique = true, nullable = false, length = MAX_NAME_LENGTH)
    private String name = null;

    @Column(name = "external_key")
    private byte[] circleKey = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void setCircleKey(final byte[] circleKey) {
        this.circleKey = copy(circleKey);
    }

    public final byte[] getCircleKey() {
        return copy(circleKey);
    }
}

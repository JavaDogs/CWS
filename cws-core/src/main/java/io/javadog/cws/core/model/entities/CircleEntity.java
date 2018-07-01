/*
 * =============================================================================
 * Copyright (c) 2016-2018, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.model.entities;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.Utilities;

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
                            "where c.name = :name")
)
public class CircleEntity extends Externable {

    @Column(name = "name", unique = true, nullable = false, length = Constants.MAX_NAME_LENGTH)
    private String name = null;

    @Column(name = "external_key")
    private byte[] circleKey = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCircleKey(final byte[] circleKey) {
        this.circleKey = Utilities.copy(circleKey);
    }

    public byte[] getCircleKey() {
        return Utilities.copy(circleKey);
    }
}

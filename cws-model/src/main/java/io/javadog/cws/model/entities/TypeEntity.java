/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-model)
 * =============================================================================
 */
package io.javadog.cws.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "type.findAll",
                query = "select t " +
                        "from TypeEntity t " +
                        "order by t.name asc"),
        @NamedQuery(name = "type.findMatching",
                query = "select t from TypeEntity t " +
                        "where lower(t.name) = lower(:name)"),
        @NamedQuery(name = "type.countUsage",
                query = "select count(o.id) " +
                        "from ObjectEntity o " +
                        "where o.type.id = :id")
})
@Table(name = "types")
public final class TypeEntity extends CWSEntity {

    @Column(name = "type_name", unique = true, nullable = false)
    private String name = null;

    @Column(name = "type_value", nullable = false)
    private String type = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

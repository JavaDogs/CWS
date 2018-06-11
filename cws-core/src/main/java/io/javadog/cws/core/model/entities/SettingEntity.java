/*
 * =============================================================================
 * Copyright (c) 2016-2018, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-core)
 * =============================================================================
 */
package io.javadog.cws.core.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * <p>CWS Setting Entity, maps the Setting table from the Database.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "setting.readAll",
                    query = "select s from SettingEntity s " +
                            "order by name asc")
)
@Table(name = "cws_settings")
public class SettingEntity extends CWSEntity {

    @Column(name = "name", nullable = false)
    private String name = null;

    @Column(name = "setting")
    private String setting = null;

    // =========================================================================
    // Entity Setters & Getters
    // =========================================================================

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void setSetting(final String setting) {
        this.setting = setting;
    }

    public final String getSetting() {
        return setting;
    }
}

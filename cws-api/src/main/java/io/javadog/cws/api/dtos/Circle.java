/*
 * CWS, Cryptographic Web Store - open source Cryptographic Storage system.
 * Copyright (C) 2016-2019, JavaDog.io
 * mailto: cws AT JavaDog DOT io
 *
 * CWS is free software; you can redistribute it and/or modify it under the
 * terms of the Apache License, as published by the Apache Software Foundation.
 *
 * CWS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the Apache License for more details.
 *
 * You should have received a copy of the Apache License, version 2, along with
 * this program; If not, you can download a copy of the License
 * here: https://www.apache.org/licenses/
 */
package io.javadog.cws.api.dtos;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.Utilities;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Circles is part of the core functionality of CWS, as all data is assigned
 * or belongs to a Circle. Circles can be considered as a Group or Collection of
 * known and trusted members who may be granted access to the data.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = Constants.FIELD_CIRCLE, propOrder = { Constants.FIELD_CIRCLE_ID, Constants.FIELD_CIRCLE_NAME, Constants.FIELD_CIRCLE_KEY, Constants.FIELD_ADDED })
public final class Circle implements Serializable {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    @XmlElement(name = Constants.FIELD_CIRCLE_ID, required = true)
    private String circleId = null;

    @XmlElement(name = Constants.FIELD_CIRCLE_NAME, required = true)
    private String circleName = null;

    // The Circle Key is an optional value which may or may not be provided,
    // hence it is only stored but not used for anything. For the same reason,
    // it is not used as part of the Standard Object methods, #equals(),
    // #hashCode() and #toString().
    @XmlElement(name = Constants.FIELD_CIRCLE_KEY, nillable = true)
    private String circleKey = null;

    @XmlElement(name = Constants.FIELD_ADDED, required = true)
    private Date added = null;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    public void setCircleId(final String circleId) {
        this.circleId = circleId;
    }

    public String getCircleId() {
        return circleId;
    }

    public void setCircleName(final String circleName) {
        this.circleName = circleName;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleKey(final String circleKey) {
        this.circleKey = circleKey;
    }

    public String getCircleKey() {
        return circleKey;
    }

    public void setAdded(final Date added) {
        this.added = Utilities.copy(added);
    }

    public Date getAdded() {
        return Utilities.copy(added);
    }

    // =========================================================================
    // Standard Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Circle{" +
                "circleId='" + circleId + '\'' +
                ", circleName='" + circleName + '\'' +
                ", circleKey='" + circleKey + '\'' +
                ", added=" + added +
                '}';
    }
}

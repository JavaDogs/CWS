/*
 * CWS, Cryptographic Web Store - open source Cryptographic Storage system.
 * Copyright (C) 2016-2021, JavaDog.io
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
package io.javadog.cws.api.requests;

import io.javadog.cws.api.common.Action;
import io.javadog.cws.api.common.Constants;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.Map;

/**
 * <p>When processing a DataType, it can be to either create a new or update an
 * existing. By default, 2 DataTypes exist, which cannot be modified.</p>
 *
 * <p>To create or update a custom DataType, the name of the DataType is needed
 * together with the type itself. Generally, the name is a shorthand description
 * of name of the actual Type, as the type may be a anything from a simple MIME
 * Type to a rule to extract the content of a stored Object.</p>
 *
 * <p>For more details, please see the 'processDataType' request in the Management
 * interface: {@link io.javadog.cws.api.Share#processDataType(ProcessDataTypeRequest)}</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
@JsonbPropertyOrder({
        Constants.FIELD_ACTION,
        Constants.FIELD_TYPENAME,
        Constants.FIELD_TYPE })
public final class ProcessDataTypeRequest extends Authentication implements ActionRequest {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    @JsonbProperty(value = Constants.FIELD_ACTION, nillable = true)
    private Action action = Action.PROCESS;

    @JsonbProperty(value = Constants.FIELD_TYPENAME, nillable = true)
    private String typeName = null;

    @JsonbProperty(value = Constants.FIELD_TYPE, nillable = true)
    private String type = null;

    // =========================================================================
    // Standard Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAction(final Action action) {
        this.action = action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getAction() {
        return action;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // =========================================================================
    // Standard Methods
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String> validate() {
        final Map<String, String> errors = super.validate();

        if (action == null) {
            errors.put(Constants.FIELD_ACTION, "No action has been provided.");
        } else {
            switch (action) {
                case PROCESS:
                    checkNotNullEmptyOrTooLong(errors, Constants.FIELD_TYPENAME, typeName, Constants.MAX_NAME_LENGTH, "The name of the DataType is missing or invalid.");
                    checkNotNullEmptyOrTooLong(errors, Constants.FIELD_TYPE, type, Constants.MAX_STRING_LENGTH, "The type of the DataType is missing or invalid.");
                    break;
                case DELETE:
                    checkNotNullOrEmpty(errors, Constants.FIELD_TYPENAME, typeName, "The name of the DataType is missing or invalid.");
                    break;
                default:
                    errors.put(Constants.FIELD_ACTION, "Not supported Action has been provided.");
                    break;
            }
        }

        return errors;
    }
}

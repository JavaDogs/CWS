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
package io.javadog.cws.api.requests;

import io.javadog.cws.api.common.Constants;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.Map;

/**
 * <p>Object is used to retrieve a list of Metadata from the CWS database.</p>
 *
 * <p>For more details, please see the 'inventory' request in the Management
 * interface: {@link io.javadog.cws.api.Management#inventory(InventoryRequest)}</p>
 *
 * @author Kim Jensen
 * @since CWS 1.2
 */
@JsonbPropertyOrder({ Constants.FIELD_PAGE_NUMBER, Constants.FIELD_PAGE_SIZE })
public class InventoryRequest extends Authentication {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    @JsonbProperty(value = Constants.FIELD_PAGE_NUMBER, nillable = true)
    private Integer pageNumber = 1;

    @JsonbProperty(value = Constants.FIELD_PAGE_SIZE, nillable = true)
    private Integer pageSize = Constants.MAX_PAGE_SIZE;

    // =========================================================================
    // Setters & Getters
    // =========================================================================

    public void setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
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

        checkIntegerWithMax(errors, Constants.FIELD_PAGE_NUMBER, pageNumber, Integer.MAX_VALUE, "The Page Number must be a positive number, starting with 1.");
        checkIntegerWithMax(errors, Constants.FIELD_PAGE_SIZE, pageSize, Constants.MAX_PAGE_SIZE, "The Page Size must be a positive number, starting with 1.");

        return errors;
    }
}

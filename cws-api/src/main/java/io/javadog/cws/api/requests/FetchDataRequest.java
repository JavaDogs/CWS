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

import io.javadog.cws.api.common.Constants;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.Map;

/**
 * <p>The Request Object must be filled with either a DataId or a CircleId and
 * pagination information. The pagination include the page size which must be
 * at least 1 and maximum 100. The page number starts with 1 for the first page,
 * and any positive number. If the number exceeds the number of records, it will
 * simply result in an empty list of Objects being returned.</p>
 *
 * <p>For more details, please see the 'fetchData' request in the Share
 * interface: {@link io.javadog.cws.api.Share#fetchData(FetchDataRequest)}</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
@JsonbPropertyOrder({
        Constants.FIELD_CIRCLE_ID,
        Constants.FIELD_DATA_ID,
        Constants.FIELD_PAGE_NUMBER,
        Constants.FIELD_PAGE_SIZE,
        Constants.FIELD_DATA_NAME })
public final class FetchDataRequest extends Authentication implements CircleIdRequest {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

    @JsonbProperty(value = Constants.FIELD_CIRCLE_ID, nillable = true)
    private String circleId = null;

    @JsonbProperty(value = Constants.FIELD_DATA_ID, nillable = true)
    private String dataId = null;

    @JsonbProperty(value = Constants.FIELD_PAGE_NUMBER, nillable = true)
    private Integer pageNumber = 1;

    @JsonbProperty(value = Constants.FIELD_PAGE_SIZE, nillable = true)
    private Integer pageSize = Constants.MAX_PAGE_SIZE;

    @JsonbProperty(value = Constants.FIELD_DATA_NAME, nillable = true)
    private String dataName = null;

    // =========================================================================
    // Setters & Getters
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCircleId(final String circleId) {
        this.circleId = circleId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCircleId() {
        return circleId;
    }

    public void setDataId(final String dataId) {
        this.dataId = dataId;
    }

    public String getDataId() {
        return dataId;
    }

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

    public void setDataName(final String dataName) {
        this.dataName = dataName;
    }

    public String getDataName() {
        return dataName;
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

        if ((circleId == null) && (dataId == null) && (dataName == null)) {
            errors.put(Constants.FIELD_IDS, "Either a Circle Id, Data Id, or Data Name must be provided.");
        }

        checkValidId(errors, Constants.FIELD_CIRCLE_ID, circleId, "The Circle Id is invalid.");
        checkValidId(errors, Constants.FIELD_DATA_ID, dataId, "The Data Id is invalid.");
        checkIntegerWithMax(errors, Constants.FIELD_PAGE_NUMBER, pageNumber, Integer.MAX_VALUE, "The Page Number must be a positive number, starting with 1.");
        checkIntegerWithMax(errors, Constants.FIELD_PAGE_SIZE, pageSize, Constants.MAX_PAGE_SIZE, "The Page Size must be a positive number, starting with 1.");

        return errors;
    }
}

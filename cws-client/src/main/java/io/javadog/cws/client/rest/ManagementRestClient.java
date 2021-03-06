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
package io.javadog.cws.client.rest;

import io.javadog.cws.api.Management;
import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.requests.ActionRequest;
import io.javadog.cws.api.requests.Authentication;
import io.javadog.cws.api.requests.FetchCircleRequest;
import io.javadog.cws.api.requests.FetchMemberRequest;
import io.javadog.cws.api.requests.FetchTrusteeRequest;
import io.javadog.cws.api.requests.InventoryRequest;
import io.javadog.cws.api.requests.MasterKeyRequest;
import io.javadog.cws.api.requests.ProcessCircleRequest;
import io.javadog.cws.api.requests.ProcessMemberRequest;
import io.javadog.cws.api.requests.ProcessTrusteeRequest;
import io.javadog.cws.api.requests.SanityRequest;
import io.javadog.cws.api.requests.SettingRequest;
import io.javadog.cws.api.responses.AuthenticateResponse;
import io.javadog.cws.api.responses.FetchCircleResponse;
import io.javadog.cws.api.responses.FetchMemberResponse;
import io.javadog.cws.api.responses.FetchTrusteeResponse;
import io.javadog.cws.api.responses.InventoryResponse;
import io.javadog.cws.api.responses.MasterKeyResponse;
import io.javadog.cws.api.responses.ProcessCircleResponse;
import io.javadog.cws.api.responses.ProcessMemberResponse;
import io.javadog.cws.api.responses.ProcessTrusteeResponse;
import io.javadog.cws.api.responses.SanityResponse;
import io.javadog.cws.api.responses.SettingResponse;
import io.javadog.cws.api.responses.VersionResponse;

/**
 * <p>Gson based REST Client for the CWS Management functionality.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class ManagementRestClient extends GsonRestClient implements Management {

    private static final String UNSUPPORTED_OPERATION = "Unsupported Operation: ";
    private static final String INVALID_REQUEST = "Cannot perform request, as the Request Object is missing or incomplete.";

    /**
     * Constructor for the CWS System REST Client. It takes the base URL for the
     * CWS Instance to communicate with, which is the protocol, hostname, port
     * and deployment name. For example; &quot;http://localhost:8080/cws&quot;.
     *
     * @param baseURL Base URL for the CWS Instance
     */
    public ManagementRestClient(final String baseURL) {
        super(baseURL);
    }

    // =========================================================================
    // Implementation of the Management Interface
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public VersionResponse version() {
        return runRequest(VersionResponse.class, Constants.REST_VERSION, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SettingResponse settings(final SettingRequest request) {
        return runRequest(SettingResponse.class, Constants.REST_SETTINGS, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MasterKeyResponse masterKey(final MasterKeyRequest request) {
        return runRequest(MasterKeyResponse.class, Constants.REST_MASTERKEY, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SanityResponse sanitized(final SanityRequest request) {
        return runRequest(SanityResponse.class, Constants.REST_SANITIZED, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InventoryResponse inventory(final InventoryRequest request) {
        return runRequest(InventoryResponse.class, Constants.REST_INVENTORY, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthenticateResponse authenticated(final Authentication request) {
        return runRequest(AuthenticateResponse.class, Constants.REST_AUTHENTICATED, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchMemberResponse fetchMembers(final FetchMemberRequest request) {
        return runRequest(FetchMemberResponse.class, Constants.REST_MEMBERS_BASE + Constants.REST_MEMBERS_FETCH, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessMemberResponse processMember(final ProcessMemberRequest request) {
        final String base = Constants.REST_MEMBERS_BASE;
        final ProcessMemberResponse response;
        throwExceptionIfInvalid(request);

        switch (request.getAction()) {
            case CREATE:
                response = runRequest(ProcessMemberResponse.class, base + Constants.REST_MEMBERS_CREATE, request);
                break;
            case INVITE:
                response = runRequest(ProcessMemberResponse.class, base + Constants.REST_MEMBERS_INVITE, request);
                break;
            case LOGIN:
                response = runRequest(ProcessMemberResponse.class, base + Constants.REST_MEMBERS_LOGIN, request);
                break;
            case LOGOUT:
                response = runRequest(ProcessMemberResponse.class, base + Constants.REST_MEMBERS_LOGOUT, request);
                break;
            case ALTER:
                response = runRequest(ProcessMemberResponse.class, base + Constants.REST_MEMBERS_ALTER, request);
                break;
            case UPDATE:
                response = runRequest(ProcessMemberResponse.class, base + Constants.REST_MEMBERS_UPDATE, request);
                break;
            case INVALIDATE:
                response = runRequest(ProcessMemberResponse.class, base + Constants.REST_MEMBERS_INVALIDATE, request);
                break;
            case DELETE:
                response = runRequest(ProcessMemberResponse.class, base + Constants.REST_MEMBERS_DELETE, request);
                break;
            default:
                throw new RESTClientException(UNSUPPORTED_OPERATION + request.getAction());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchCircleResponse fetchCircles(final FetchCircleRequest request) {
        return runRequest(FetchCircleResponse.class, Constants.REST_CIRCLES_BASE + Constants.REST_CIRCLES_FETCH, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessCircleResponse processCircle(final ProcessCircleRequest request) {
        final String base = Constants.REST_CIRCLES_BASE;
        final ProcessCircleResponse response;
        throwExceptionIfInvalid(request);

        switch (request.getAction()) {
            case CREATE:
                response = runRequest(ProcessCircleResponse.class, base + Constants.REST_CIRCLES_CREATE, request);
                break;
            case UPDATE:
                response = runRequest(ProcessCircleResponse.class, base + Constants.REST_CIRCLES_UPDATE, request);
                break;
            case DELETE:
                response = runRequest(ProcessCircleResponse.class, base + Constants.REST_CIRCLES_DELETE, request);
                break;
            default:
                throw new RESTClientException(UNSUPPORTED_OPERATION + request.getAction());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FetchTrusteeResponse fetchTrustees(final FetchTrusteeRequest request) {
        return runRequest(FetchTrusteeResponse.class, Constants.REST_TRUSTEES_BASE + Constants.REST_TRUSTEES_FETCH, request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessTrusteeResponse processTrustee(final ProcessTrusteeRequest request) {
        final String base = Constants.REST_TRUSTEES_BASE;
        final ProcessTrusteeResponse response;
        throwExceptionIfInvalid(request);

        switch (request.getAction()) {
            case ADD:
                response = runRequest(ProcessTrusteeResponse.class, base + Constants.REST_TRUSTEES_ADD, request);
                break;
            case ALTER:
                response = runRequest(ProcessTrusteeResponse.class, base + Constants.REST_TRUSTEES_ALTER, request);
                break;
            case REMOVE:
                response = runRequest(ProcessTrusteeResponse.class, base + Constants.REST_TRUSTEES_REMOVE, request);
                break;
            default:
                throw new RESTClientException(UNSUPPORTED_OPERATION + request.getAction());
        }

        return response;
    }

    private static void throwExceptionIfInvalid(final ActionRequest request) {
        if ((request == null) || (request.getAction() == null)) {
            throw new RESTClientException(INVALID_REQUEST);
        }
    }
}

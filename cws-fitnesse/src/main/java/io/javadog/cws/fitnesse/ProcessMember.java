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
package io.javadog.cws.fitnesse;

import io.javadog.cws.api.common.Action;
import io.javadog.cws.api.common.MemberRole;
import io.javadog.cws.api.requests.ProcessMemberRequest;
import io.javadog.cws.api.responses.ProcessMemberResponse;
import io.javadog.cws.fitnesse.callers.CallManagement;
import io.javadog.cws.fitnesse.utils.Converter;

/**
 * <p>FitNesse Fixture for the CWS ProcessMember feature.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
public final class ProcessMember extends CwsRequest<ProcessMemberResponse> {

    private Action action = null;
    private MemberRole memberRole = null;
    private String memberId = null;
    private String publicKey = null;
    private String newAccountName = null;
    private byte[] newCredential = null;

    // =========================================================================
    // Request & Response Setters and Getters
    // =========================================================================

    public void setAction(final String action) {
        this.action = Converter.findAction(action);
    }

    public void setMemberRole(final String memberRole) {
        this.memberRole = Converter.findMemberRole(memberRole);
    }

    public void setMemberId(final String memberId) {
        this.memberId = getId(Converter.preCheck(memberId));
    }

    public void setPublicKey(final String publicKey) {
        this.publicKey = Converter.preCheck(publicKey);
    }

    public void setNewAccountName(final String newAccountName) {
        this.newAccountName = Converter.preCheck(newAccountName);
    }

    public void setNewCredential(final String newCredential) {
        this.newCredential = Converter.convertBytes(newCredential);
    }

    public String memberId() {
        return ((newAccountName != null) && (response.getMemberId() != null)) ? (newAccountName + EXTENSION_ID) : null;
    }

    public String signature() {
        return ((newAccountName != null) && (response.getSignature() != null)) ? (newAccountName + EXTENSION_SIGNATURE) : null;
    }

    // =========================================================================
    // Standard FitNesse Fixture method(s)
    // =========================================================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        final ProcessMemberRequest request = prepareRequest(ProcessMemberRequest.class);
        request.setAction(action);
        request.setMemberRole(memberRole);
        request.setMemberId(memberId);
        request.setPublicKey(publicKey);
        request.setNewAccountName(newAccountName);
        request.setNewCredential(newCredential);

        response = CallManagement.processMember(requestUrl, request);

        // Ensuring that the internal mapping of Ids with accounts being
        // used is synchronized.
        processId(action, memberId, newAccountName, response);
        setSignature(newAccountName + EXTENSION_SIGNATURE, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        super.reset();

        // Reset internal values
        action = null;
        memberRole = null;
        memberId = null;
        publicKey = null;
        newAccountName = null;
        newCredential = null;
    }
}

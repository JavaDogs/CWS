/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-rest)
 * =============================================================================
 */
package io.javadog.cws.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.requests.FetchMemberRequest;
import io.javadog.cws.api.requests.ProcessMemberRequest;
import io.javadog.cws.core.SystemBean;
import io.javadog.cws.core.exceptions.CWSException;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public final class MemberServiceTest extends BeanSetup {

    @Test
    public void testCreate() {
        final MemberService service = prepareService();
        final ProcessMemberRequest request = new ProcessMemberRequest();

        final Response response = service.create(request);
        assertThat(response.getStatus(), is(ReturnCode.VERIFICATION_WARNING.getHttpCode()));
    }

    @Test
    public void testFlawedCreate() {
        final MemberService service = prepareFlawedService();
        final ProcessMemberRequest request = new ProcessMemberRequest();

        final Response response = service.create(request);
        assertThat(response.getStatus(), is(ReturnCode.ERROR.getHttpCode()));
    }

    @Test
    public void testInvite() {
        final MemberService service = prepareService();
        final ProcessMemberRequest request = new ProcessMemberRequest();

        final Response response = service.invite(request);
        assertThat(response.getStatus(), is(ReturnCode.VERIFICATION_WARNING.getHttpCode()));
    }

    @Test
    public void testFlawedInvite() {
        final MemberService service = prepareFlawedService();
        final ProcessMemberRequest request = new ProcessMemberRequest();

        final Response response = service.invite(request);
        assertThat(response.getStatus(), is(ReturnCode.ERROR.getHttpCode()));
    }

    @Test
    public void testUpdate() {
        final MemberService service = prepareService();
        final ProcessMemberRequest request = new ProcessMemberRequest();

        final Response response = service.update(request);
        assertThat(response.getStatus(), is(ReturnCode.VERIFICATION_WARNING.getHttpCode()));
    }

    @Test
    public void testFlawedUpdate() {
        final MemberService service = prepareFlawedService();
        final ProcessMemberRequest request = new ProcessMemberRequest();

        final Response response = service.update(request);
        assertThat(response.getStatus(), is(ReturnCode.ERROR.getHttpCode()));
    }

    @Test
    public void testDelete() {
        final MemberService service = prepareService();
        final ProcessMemberRequest request = new ProcessMemberRequest();

        final Response response = service.delete(request);
        assertThat(response.getStatus(), is(ReturnCode.VERIFICATION_WARNING.getHttpCode()));
    }

    @Test
    public void testFlawedDelete() {
        final MemberService service = prepareFlawedService();
        final ProcessMemberRequest request = new ProcessMemberRequest();

        final Response response = service.delete(request);
        assertThat(response.getStatus(), is(ReturnCode.ERROR.getHttpCode()));
    }

    @Test
    public void testFetch() {
        final MemberService service = prepareService();
        final FetchMemberRequest request = new FetchMemberRequest();

        final Response response = service.fetch(request);
        assertThat(response.getStatus(), is(ReturnCode.VERIFICATION_WARNING.getHttpCode()));
    }

    @Test
    public void testFlawedFetch() {
        final MemberService service = prepareFlawedService();
        final FetchMemberRequest request = new FetchMemberRequest();

        final Response response = service.fetch(request);
        assertThat(response.getStatus(), is(ReturnCode.ERROR.getHttpCode()));
    }

    // =========================================================================
    // Internal Test Setup Methods
    // =========================================================================

    private static MemberService prepareFlawedService() {
        try {

            final MemberService service = MemberService.class.getConstructor().newInstance();
            setField(service, "bean", null);

            return service;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new CWSException(ReturnCode.ERROR, "Cannot instantiate Service Object", e);
        }
    }

    private MemberService prepareService() {
        try {
            final SystemBean bean = SystemBean.class.getConstructor().newInstance();
            setField(bean, "entityManager", entityManager);
            setField(bean, "settingBean", prepareSettingBean());

            final MemberService service = MemberService.class.getConstructor().newInstance();
            setField(service, "bean", bean);

            return service;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new CWSException(ReturnCode.ERROR, "Cannot instantiate Service Object", e);
        }
    }
}
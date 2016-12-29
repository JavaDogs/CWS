package io.javadog.cws.war;

import io.javadog.cws.api.System;
import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.requests.FetchCircleRequest;
import io.javadog.cws.api.requests.FetchMemberRequest;
import io.javadog.cws.api.requests.ProcessCircleRequest;
import io.javadog.cws.api.requests.ProcessMemberRequest;
import io.javadog.cws.api.responses.FetchCircleResponse;
import io.javadog.cws.api.responses.FetchMemberResponse;
import io.javadog.cws.api.responses.ProcessCircleResponse;
import io.javadog.cws.api.responses.ProcessMemberResponse;
import io.javadog.cws.api.responses.VersionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING)
@WebService(name = "systemWS", serviceName = "systemWSService", portName = "systemWS", targetNamespace = "http://ws.cws.javadog.io/")
public class SystemSOAPService implements System {

    private static final Logger log = LoggerFactory.getLogger(SystemSOAPService.class);

    private static final String GENERAL_RETURN_MESSAGE = "An unknown error occurred. Please consult the CWS System Log.";

    @Inject private SystemBean bean;

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    public VersionResponse version() {
        VersionResponse response;

        try {
            response = bean.version();
        } catch (RuntimeException e) {
            // If an error occurs that has so far not been resolved, this is the
            // final level where it can be handled. Errors can be Persistence
            // problems or other things that will affect the reliability and/or
            // performance of the system.
            log.error(e.getMessage(), e);
            response = new VersionResponse(Constants.ERROR, GENERAL_RETURN_MESSAGE);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    public FetchMemberResponse fetchMembers(final FetchMemberRequest request) {
        FetchMemberResponse response;

        try {
            response = bean.fetchMembers(request);
        } catch (RuntimeException e) {
            // If an error occurs that has so far not been resolved, this is the
            // final level where it can be handled. Errors can be Persistence
            // problems or other things that will affect the reliability and/or
            // performance of the system.
            log.error(e.getMessage(), e);
            response = new FetchMemberResponse(Constants.ERROR, GENERAL_RETURN_MESSAGE);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    public ProcessMemberResponse processMember(final ProcessMemberRequest request) {
        ProcessMemberResponse response;

        try {
            response = bean.processMember(request);
        } catch (RuntimeException e) {
            // If an error occurs that has so far not been resolved, this is the
            // final level where it can be handled. Errors can be Persistence
            // problems or other things that will affect the reliability and/or
            // performance of the system.
            log.error(e.getMessage(), e);
            response = new ProcessMemberResponse(Constants.ERROR, GENERAL_RETURN_MESSAGE);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    public FetchCircleResponse fetchCircles(final FetchCircleRequest request) {
        FetchCircleResponse response;

        try {
            response = bean.fetchCircles(request);
        } catch (RuntimeException e) {
            // If an error occurs that has so far not been resolved, this is the
            // final level where it can be handled. Errors can be Persistence
            // problems or other things that will affect the reliability and/or
            // performance of the system.
            log.error(e.getMessage(), e);
            response = new FetchCircleResponse(Constants.ERROR, GENERAL_RETURN_MESSAGE);
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @WebMethod
    public ProcessCircleResponse processCircle(final ProcessCircleRequest request) {
        ProcessCircleResponse response;

        try {
            response = bean.processCircle(request);
        } catch (RuntimeException e) {
            // If an error occurs that has so far not been resolved, this is the
            // final level where it can be handled. Errors can be Persistence
            // problems or other things that will affect the reliability and/or
            // performance of the system.
            log.error(e.getMessage(), e);
            response = new ProcessCircleResponse(Constants.ERROR, GENERAL_RETURN_MESSAGE);
        }

        return response;
    }
}
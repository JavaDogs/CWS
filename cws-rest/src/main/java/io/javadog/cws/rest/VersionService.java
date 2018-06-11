/*
 * =============================================================================
 * Copyright (c) 2016-2018, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-rest)
 * =============================================================================
 */
package io.javadog.cws.rest;

import static io.javadog.cws.api.common.Constants.REST_VERSION;

import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.responses.VersionResponse;
import io.javadog.cws.core.ManagementBean;
import io.javadog.cws.core.misc.LoggingUtil;
import io.javadog.cws.core.model.Settings;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * <p>REST interface for the Version functionality.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Path(REST_VERSION)
public class VersionService {

    private static final Logger log = Logger.getLogger(VersionService.class.getName());

    private final Settings settings = Settings.getInstance();
    @Inject private ManagementBean bean;

    @GET
    @POST
    @Produces(RestUtils.PRODUCES)
    public Response version() {
        final Long startTime = System.nanoTime();
        VersionResponse response;

        try {
            response = bean.version();
            log.log(Settings.INFO, () -> LoggingUtil.requestDuration(settings.getLocale(), REST_VERSION, startTime));
        } catch (RuntimeException e) {
            log.log(Settings.ERROR, () -> LoggingUtil.requestDuration(settings.getLocale(), REST_VERSION, startTime, e));
            response = new VersionResponse(ReturnCode.ERROR, e.getMessage());
        }

        return RestUtils.buildResponse(response);
    }
}

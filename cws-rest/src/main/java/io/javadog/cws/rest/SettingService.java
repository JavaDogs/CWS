/*
 * =============================================================================
 * Copyright (c) 2016-2018, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-rest)
 * =============================================================================
 */
package io.javadog.cws.rest;

import static io.javadog.cws.api.common.Constants.REST_SETTINGS;

import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.requests.SettingRequest;
import io.javadog.cws.api.responses.SettingResponse;
import io.javadog.cws.core.ManagementBean;
import io.javadog.cws.core.misc.LoggingUtil;
import io.javadog.cws.core.model.Settings;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * <p>REST interface for the Setting functionality.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@Path(REST_SETTINGS)
public class SettingService {

    private static final Logger log = Logger.getLogger(SettingService.class.getName());

    private final Settings settings = Settings.getInstance();
    @Inject private ManagementBean bean;

    @POST
    @Consumes(RestUtils.CONSUMES)
    @Produces(RestUtils.PRODUCES)
    public Response settings(@NotNull final SettingRequest settingRequest) {
        final Long startTime = System.nanoTime();
        SettingResponse response;

        try {
            response = bean.settings(settingRequest);
            log.log(Settings.INFO, () -> LoggingUtil.requestDuration(settings.getLocale(), REST_SETTINGS, startTime));
        } catch (RuntimeException e) {
            log.log(Settings.ERROR, () -> LoggingUtil.requestDuration(settings.getLocale(), REST_SETTINGS, startTime, e));
            response = new SettingResponse(ReturnCode.ERROR, e.getMessage());
        }

        return RestUtils.buildResponse(response);
    }
}

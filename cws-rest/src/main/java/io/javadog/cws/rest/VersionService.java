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
package io.javadog.cws.rest;

import io.javadog.cws.api.common.Constants;
import io.javadog.cws.api.common.ReturnCode;
import io.javadog.cws.api.responses.VersionResponse;
import io.javadog.cws.core.ManagementBean;
import io.javadog.cws.core.misc.LoggingUtil;
import io.javadog.cws.core.model.Settings;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * <p>REST interface for the Version functionality.</p>
 *
 * @author Kim Jensen
 * @since CWS 1.0
 */
@Path(Constants.REST_VERSION)
public class VersionService {

    private static final Logger LOG = Logger.getLogger(VersionService.class.getName());

    @Inject
    private ManagementBean bean;
    private final Settings settings = Settings.getInstance();

    @POST
    @Produces(RestUtils.PRODUCES)
    public Response version() {
        final long startTime = System.nanoTime();
        VersionResponse response;

        try {
            response = bean.version();
            LOG.log(Settings.INFO, () -> LoggingUtil.requestDuration(settings.getLocale(), Constants.REST_VERSION, startTime));
        } catch (RuntimeException e) {
            LOG.log(Settings.ERROR, () -> LoggingUtil.requestDuration(settings.getLocale(), Constants.REST_VERSION, startTime, e));
            response = new VersionResponse(ReturnCode.ERROR, e.getMessage());
        }

        return RestUtils.buildResponse(response);
    }
}

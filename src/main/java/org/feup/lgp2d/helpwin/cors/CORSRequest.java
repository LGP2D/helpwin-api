package org.feup.lgp2d.helpwin.cors;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.logging.Logger;

public class CORSRequest implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger(CORSRequest.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if (requestContext.getRequest().getMethod().equalsIgnoreCase("OPTIONS")){
            log.info("HTTP Method (OPTIONS) - Detected!");

            requestContext.abortWith(Response.status(Response.Status.OK).entity("OPTIONS OK!").build());
        }
    }
}
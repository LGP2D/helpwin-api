package org.feup.lgp2d.helpwin.cors;

import org.glassfish.jersey.message.internal.EntityInputStream;
import org.glassfish.jersey.server.ContainerResponse;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;

@Provider
public class CORSFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        /*if ("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
            requestContext.abortWith(Response.status(Response.Status.OK).build());
        }*/
        String method = requestContext.getRequest().getMethod();
        if (requestContext.getRequest().getMethod().equalsIgnoreCase("OPTIONS")){
            //requestContext.abortWith(Response.status(Response.Status.OK).build());
            if (responseContext.getStatus() == 200) { return; }
            responseContext.setStatus(200);
        }
    }
}

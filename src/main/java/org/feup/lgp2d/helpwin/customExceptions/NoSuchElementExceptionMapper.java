package org.feup.lgp2d.helpwin.customExceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NoSuchElementExceptionMapper extends WebApplicationException {
    public NoSuchElementExceptionMapper(String message) {
        super(Response.status(Response.Status.UNAUTHORIZED)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}

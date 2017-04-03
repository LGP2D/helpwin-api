package org.feup.lgp2d.helpwin.authentication;

import org.glassfish.jersey.internal.util.Base64;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.UNAUTHORIZED)
            .entity("Access blocked for all users").build();

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        /**
         * Get the request's target method
         */
        Method method = resourceInfo.getResourceMethod();

        /**
         * Case PermitAll annotation is present
         */
        if (!method.isAnnotationPresent(PermitAll.class)) {
            /**
             * Case DenyAll annotation is present
             */
            if (method.isAnnotationPresent(DenyAll.class)) {
                containerRequestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            /**
             * Get Request's headers
             * Fetch authorization headers
             */
            final MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            /**
             * If no authorization is present block access
             */
            if (authorization == null || authorization.isEmpty()) {
                containerRequestContext.abortWith(ACCESS_DENIED);
                return;
            }
            /**
             * Get encoded username and password and decode it
             */
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
            String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

            /**
             * Split username and password tokens
             */
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            /**
             * Case the request's target method has Roles annotation
             */
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                /**
                 * Get the request's target method annotation Roles
                 */
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                /**
                 * Case user is not valid
                 */
                if (!isUserAllowed(username, password, rolesSet)) {
                    containerRequestContext.abortWith(ACCESS_DENIED);
                    return;
                }
            }
        }
    }

    private boolean isUserAllowed(String username, String password, Set<String> rolesSet) {
        boolean isAllowed = false;
        /**
         * This is only for test purpose. We'll be needing to query the database for this, and also change the auth method to JWT
         */
        if (username.equals("nuno") && password.equals("nuno")) {
            String userRole = "ADMIN";

            /**
             * Verify user role
             */
            if (rolesSet.contains(userRole)) {
                isAllowed = true;
            }
        }
        return isAllowed;
    }
}

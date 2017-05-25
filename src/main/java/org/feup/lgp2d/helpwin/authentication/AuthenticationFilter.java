package org.feup.lgp2d.helpwin.authentication;

import org.feup.lgp2d.helpwin.authentication.util.TokenHelper;
import org.feup.lgp2d.helpwin.customExceptions.InvalidUserDataException;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.models.Role;
import org.feup.lgp2d.helpwin.models.User;
import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.ContainerRequest;

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

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    @Context
    private ResourceInfo resourceInfo;

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
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Access blocked for all users").build());
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
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("You cannot access this resource").build());
                return;
            }
            /**
             * Get encoded token and decode it
             */
            String authorizationHeader = authorization.get(0);
            String token = extractJwtTokenFromAuthorizationHeader(authorizationHeader);
            // Check if token is valid
            if (!TokenHelper.isValid(token)) {
                throw new InvalidUserDataException("Invalid token");
            }
            // Check if token issuer is valid
            /*if (Objects.equals(TokenHelper.getIssuer(token), "Helpwin-Security")) {
                throw new InvalidUserDataException("Invalid token issuer");
            }*/

            /**
             * Case the request's target method has Roles annotation
             */
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                /**
                 * Extract target method annotation Roles
                 */
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                /**
                 * Extract email from token
                 */
                String email = TokenHelper.getEmail(token);

                /**
                 * Case user is not valid
                 */
                if (!isUserAllowed(email, rolesSet)) {
                    containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                            .entity("You cannot access this resource").build());
                    return;
                }
            }
        }
    }

    private boolean isUserAllowed(String email, Set<String> rolesSet) {
        boolean isAllowed = false;

        UserRepository userRepository = new UserRepository();
        User user = userRepository.getOne(p -> p.getEmail().contentEquals(email));

        if (rolesSet.contains(user.getRole().getDescription())){
            isAllowed = true;
        }

        return isAllowed;
    }

    private static String extractJwtTokenFromAuthorizationHeader(String auth) {
        //Replacing "Bearer Token" to "Token" directly
        return auth.replaceFirst("[B|b][E|e][A|a][R|r][E|e][R|r] ", "")
                .replace(" ", "");
    }
}

package org.feup.lgp2d.helpwin.endpoints;


import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.models.User;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("user")
public class UserController {

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.getAll();
        return Response.ok(users).build();
    }

    /**
     * Create a user into the database
     * @param user - (User) the model of the user
     * @return Response - the response with the created user embedded
     */
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        UserRepository userRepository = new UserRepository();
        User userToRetrieve = userRepository.create(user);
        return Response.ok(userToRetrieve).build();
    }
}

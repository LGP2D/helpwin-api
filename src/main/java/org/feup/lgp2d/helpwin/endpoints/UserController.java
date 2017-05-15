package org.feup.lgp2d.helpwin.endpoints;


import org.feup.lgp2d.helpwin.authentication.util.TokenHelper;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.models.User;
import org.jboss.logging.annotations.Param;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Null;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.List;

@Path("user")
public class UserController {

    @OPTIONS
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response options() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.getAll();
        return Response.ok(users).build();
    }

    /**
     * Create a user into the database
     *
     * @param user - (User) the model of the user
     * @return Response - the response with the created user embedded
     */
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        UserRepository userRepository = new UserRepository();
        if (user.getUniqueId() == null || user.getUniqueId().isEmpty()) {
            user.generateUniqueId();
        }
        User userToRetrieve = userRepository.create(user);
        return Response.ok(userToRetrieve).build();
    }

    @DELETE
    @PermitAll
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserById(@PathParam("id") int id) {
        UserRepository userRepository = new UserRepository();
        userRepository.delete(id);
        return Response.ok().build();
    }

    @POST
    @PermitAll
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User user) {
        UserRepository userRepository = new UserRepository();
        User userToRetrieve = userRepository.authenticateUser(user);
        if (userToRetrieve.getPassword() != null) {
            userToRetrieve.setPassword(null);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        userToRetrieve.setToken(TokenHelper.getJWTString(calendar.getTime(), userToRetrieve.getEmail()));

        return Response.ok(userToRetrieve).build();
    }

    @OPTIONS
    @PermitAll
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response options2() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*").build();
    }

    /**
     * Edit user's profile
     *
     * @param user - (User) the model of the user
     * @return Response - the response with the edited user embedded
     */
    @PUT
    @PermitAll
    @Path("/editProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(User user) {
        try{
            UserRepository repo = new UserRepository();
            User dbUser = repo.getUserByUniqueID(user.getUniqueId());

            user.setID(dbUser.getId());
            user.setRole(dbUser.getRole());

            if(dbUser.getToken() != null){
                user.setToken(dbUser.getToken());
            }
            if(dbUser.getImageUrl() != null){
                user.setImageUrl(dbUser.getImageUrl());
            }

            if(user.getEmail() == null){
                user.setEmail(dbUser.getEmail());
            }
            if(user.getName() == null){
                user.setName(dbUser.getName());
            }
            if(user.getBirthDate() == null){
                user.setBirthDate(dbUser.getBirthDate());
            }
            if(user.getPassword() == null){
                user.setPassword(dbUser.getPassword());
            }
            if(user.getProfession() == null){
                user.setProfession(dbUser.getProfession());
            }

            //TODO: Verify email using token

            repo.updateUser(user);
            User newUser = repo.getUserByUniqueID(user.getUniqueId());
            return Response.ok(newUser).build();
        } catch(NullPointerException e){
            return Response.serverError().entity("Internal error.").build();
        }
    }

    @OPTIONS
    @PermitAll
    @Path("/editProfile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response options4() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*").build();
    }

}

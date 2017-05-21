package org.feup.lgp2d.helpwin.endpoints;

import org.eclipse.jetty.server.handler.ResourceHandler;
import org.feup.lgp2d.helpwin.authentication.util.TokenHelper;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.models.Image;
import org.feup.lgp2d.helpwin.models.RootImage;
import org.feup.lgp2d.helpwin.models.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Base64;
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
        if (user.getImageUrl() == null || user.getImageUrl().isEmpty()) {
            user.setImageUrl("http://i.imgur.com/Qo3dP6Z.png");
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

        if (userToRetrieve.getUserActions() != null) { userToRetrieve.setUserActions(null); }

        return Response.ok(userToRetrieve).build();
    }

    @POST
    @PermitAll
    @Path("/loginToken")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUserWithToken(@HeaderParam("Authorization")String token) {
        if (!TokenHelper.isValid(token)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Token").build();
        }

        String email = TokenHelper.getEmail(token);

        if (email == null || email.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity("Email not found in token").build();
        }

        UserRepository userRepository = new UserRepository();
        User userToRetrieve = userRepository.getOne(p -> p.getEmail().contentEquals(email));

        if (userToRetrieve == null) {
            return Response.status(Response.Status.NO_CONTENT).entity("User with token not found").build();
        }

        if (userToRetrieve.getPassword() != null || !userToRetrieve.getPassword().isEmpty()) {
            userToRetrieve.setPassword(null);
        }

        return Response.ok(userToRetrieve).build();
    }

    @OPTIONS
    @PermitAll
    @Path("/loginToken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response options3() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Authorization")
                .build();
    }

    @OPTIONS
    @PermitAll
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response options2() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @OPTIONS
    @PermitAll
    @Path("/image")
    @Produces(MediaType.APPLICATION_JSON)
    public Response options4() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @PermitAll
    @Path("/image")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(RootImage file) {

        String base64 = file.file.data_uri.split(",")[1];
        byte[] decodedImage = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        java.nio.file.Path path = Paths.get("./images/", file.file.filename);
        try {
            String pathToReturn = Files.write(path, decodedImage).toString();
            return Response.ok(pathToReturn.substring(1)).build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
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
    public Response option5() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*").build();
    }

}

package org.feup.lgp2d.helpwin.endpoints;

import com.sun.org.apache.regexp.internal.RE;
import org.feup.lgp2d.helpwin.authentication.util.TokenHelper;
import org.feup.lgp2d.helpwin.dao.repositories.Repository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.ActionRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.EvaluationStatusRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserActionRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.models.*;

import javax.annotation.security.PermitAll;
import javax.persistence.PostRemove;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Path("user")
public class UserController {

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        UserRepository userRepository = new UserRepository();
        List<User> users = userRepository.getAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("/institutions")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInstitutions() {
        UserRepository userRepository = new UserRepository();
        List<User> institutions = userRepository.getAll();
        institutions = institutions.stream().filter(p -> p.getRole().getId() == 2).collect(Collectors.toList());

        if (institutions == null) { return Response.status(Response.Status.NO_CONTENT).entity("No institutions to show").build(); }
        return Response.ok(institutions).build();
    }

    @GET
    @Path("/companies")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompanies() {
        UserRepository userRepository = new UserRepository();
        List<User> companies = userRepository.getAll();
        companies = companies.stream().filter(p -> p.getRole().getId() == 4).collect(Collectors.toList());

        if (companies == null) { return Response.status(Response.Status.NO_CONTENT).entity("No companies to show").build(); }
        return Response.ok(companies).build();
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
            user.setImageUrl("/images/HELPWIN.png");
        }

        if (user.getRole().getDescription().contentEquals("VOLUNTEER")){
            user.setActive(true);
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

        if (userToRetrieve == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("User does not exist").build();
        }
        
        if (!userToRetrieve.isActive()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Account deactivated").build();
        }

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

        if (userToRetrieve.getUserActions() != null) { userToRetrieve.setUserActions(null); }

        return Response.ok(userToRetrieve).build();
    }

    @POST
    @PermitAll
    @Path("/image")
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(RootImage file) {
        if (file == null) { return Response.status(Response.Status.BAD_REQUEST).entity("Image null").build(); }
        if (file.file == null) { return Response.status(Response.Status.BAD_REQUEST).entity("Image null").build(); }
        if (file.file.data_uri == null || file.file.filename == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Image data incorrect").build();
        }

        String base64 = file.file.data_uri.split(",")[1];
        byte[] decodedImage = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        java.nio.file.Path path = Paths.get("./images/", file.file.filename.length() > 10 ?
                file.file.filename.substring(0, 9) + ".png" : file.file.filename);
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

            user.setActive(true);

            repo.updateUser(user);
            User newUser = repo.getUserByUniqueID(user.getUniqueId());
            return Response.ok(newUser).build();
        } catch(NullPointerException e){
            return Response.serverError().entity("Internal error.").build();
        }
    }

    @PUT
    @PermitAll
    @Path("/add-coins")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
    public Response addCoins(Coins coins, @HeaderParam(value = "Authorization") final String token) {
        String email = TokenHelper.getEmail(token);
        if (email == null) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build(); }

        UserRepository userRepository = new UserRepository();
        User user = userRepository.getOne(p -> p.getEmail().contentEquals(email));
        if (user == null) { return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build(); }

        int coinsToAdd = Math.abs(coins.getCoinsToAdd());

        int credits = user.getCredits() + coinsToAdd;
        user.setCredits(credits);

        userRepository.updateUser(user);

        return Response.ok("Coins successfully added").build();
    }

    @PUT
    @PermitAll
    @Path("/remove-coins")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
    public Response removeCoins(Coins coins, @HeaderParam(value = "Authorization") final String token) {
        String email = TokenHelper.getEmail(token);
        if (email == null) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build(); }

        UserRepository userRepository = new UserRepository();
        User user = userRepository.getOne(p -> p.getEmail().contentEquals(email));
        if (user == null) { return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build(); }

        if (coins.getCoinsToRemove() > user.getCredits()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Negative balance").build();
        }

        int coinsToAdd = Math.abs(coins.getCoinsToAdd());

        int credits = user.getCredits() - coinsToAdd;
        user.setCredits(credits);

        userRepository.updateUser(user);

        return Response.ok("Coins successfully removed").build();
    }

    @PUT
    @PermitAll
    @Path("/deactivate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateUser(@HeaderParam(value = "Authorization") final String token, User user) {
        if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Token").build(); }

        UserRepository userRepository = new UserRepository();
        User userToDeactivate = userRepository.getUserByUniqueID(user.getUniqueId());
        if (userToDeactivate == null) { return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build(); }

        userToDeactivate.setActive(false);

        userRepository.updateUser(userToDeactivate);

        return Response.ok("User successfully deactivated").build();
    }

    @PUT
    @PermitAll
    @Path("/activate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response activate(@HeaderParam(value = "Authorization") final String token, User user) {
        if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Token").build(); }

        UserRepository userRepository = new UserRepository();
        User userToActivate = userRepository.getUserByUniqueID(user.getUniqueId());
        if (userToActivate == null) { return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build(); }

        userToActivate.setActive(true);

        userRepository.updateUser(userToActivate);

        return Response.ok("User successfully activated").build();
    }

    @GET
    @PermitAll
    @Path("/volunteerActions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVolunteerActions(@HeaderParam(value = "Authorization") final String token) {
        if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build(); }
        String email = TokenHelper.getEmail(token);

        UserRepository userRepository = new UserRepository();
        User user = userRepository.getUserByEmail(email);

        if (user == null) { return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build(); }

        List<UserAction> userActions = user.getUserActions();
        userActions.forEach(p -> p.setUser(null));
        //userActions.forEach(p -> p.getAction().setUser(null));
        userActions.forEach(p -> p.getAction().setUserActions(null));

        List<UserAction> actions = new ArrayList<>();
        actions.addAll(userActions);

        return Response.ok(actions).build();
    }

    @POST
    @PermitAll
    @Path("/evaluate/{actionId}/{status}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response evaluateVolunteer(@HeaderParam(value = "Authorization")final String token, User volunteer,
                                      @PathParam("actionId") final int actionId,
                                      @PathParam("status") final String status) {

        try {
            if (!TokenHelper.isValid(token)) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Token").build();
            }

            String email = TokenHelper.getEmail(token);

            UserRepository userRepository = new UserRepository();
            User institution = userRepository.getUserByEmail(email);

            if (institution == null) {
                return Response.status(Response.Status.NO_CONTENT).entity("Institution not found").build();
            }

            List<UserAction> actions = institution.getUserActions();
            UserAction actionToEvaluate = actions.stream().filter(p -> p.getPk().getUser().getUniqueId().contentEquals(volunteer.getUniqueId()) && p.getAction().getId().equals(actionId)).findFirst().orElse(null);

            if (actionToEvaluate == null) {
                return Response.status(Response.Status.NO_CONTENT).entity("No action to eval").build();
            }

            EvaluationStatusRepository evaluationStatusRepository = new EvaluationStatusRepository();
            EvaluationStatus eval;

            if (status == null || status.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("status empty").build();
            }

            if (status.equalsIgnoreCase("success")) {
                eval = evaluationStatusRepository.getOne(p -> p.getDescription().equalsIgnoreCase("success"));
            } else {
                eval = evaluationStatusRepository.getOne(p -> p.getDescription().equalsIgnoreCase("failed"));
            }

            if (eval == null) {
                return Response.status(Response.Status.NO_CONTENT).entity("no eval found").build();
            }

            actionToEvaluate.setEvaluationStatus(eval);

            UserActionRepository userActionRepository = new UserActionRepository();
            userActionRepository.update(actionToEvaluate);

            User volunteerR = userRepository.getOne( p-> p.getUniqueId().equals(volunteer.getUniqueId()) );

            volunteerR.setCredits(volunteerR.getCredits() + actionToEvaluate.getAction().getCredits());

            userRepository.update(volunteerR);
            return Response.ok().build();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}

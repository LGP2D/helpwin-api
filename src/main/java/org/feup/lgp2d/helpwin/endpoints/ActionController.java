package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.authentication.util.TokenHelper;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.ActionRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.EvaluationStatusRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserActionRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.models.Action;
import org.feup.lgp2d.helpwin.models.EvaluationStatus;
import org.feup.lgp2d.helpwin.models.User;
import org.feup.lgp2d.helpwin.models.UserAction;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("actions")
public class ActionController {

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActions() {
        ActionRepository actionRepository = new ActionRepository();
        List<Action> actions = actionRepository.getAll();
        if (!actions.isEmpty()) {
            return Response.ok().entity(actions).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found").build();
        }
    }

    @PermitAll
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getActionById(@PathParam("id")int id) {
        ActionRepository actionRepository = new ActionRepository();
        Action action = actionRepository.getOne(id);
        if (action != null) {
            return Response.ok().entity(action).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found!").build();
        }
    }

    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAction(@HeaderParam(value = "Authorization") final String token, Action action) {
        if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Token").build(); }
        String email = TokenHelper.getEmail(token);

        ActionRepository actionRepository = new ActionRepository();
        action.generateUniqueId();
        UserRepository userRepository = new UserRepository();

        User user = userRepository.getUserByEmail(email);
        if (user == null) { return Response.status(Response.Status.BAD_REQUEST).entity("User not found").build(); }

        action.setUser(user);
        Action actionToRetrieve = actionRepository.create(action);
        if (actionToRetrieve != null) {
            return Response.ok().entity(actionToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to save your record on database").build();
        }
    }

    @PermitAll
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAction(Action action) {
        ActionRepository actionRepository = new ActionRepository();
        Action actionToRetrieve = actionRepository.create(action);
        if (actionToRetrieve != null) {
            return Response.ok().entity(actionToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to update your record on database").build();
        }
    }

   /*@PermitAll
    @POST
    @Path("actions/volunteerSubmit/{idAction}/{idUser}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response VolunteerSubmit(Action action) {
        ActionRepository actionRepository = new ActionRepository();

    }*/

    @PermitAll
    @POST
    @Path("/submit/{actionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitAction(@PathParam("actionId") int actionId, @HeaderParam("Authorization") String token) {
        UserRepository userRepository = new UserRepository();
        String email = TokenHelper.getEmail(token);
        User user = userRepository.getOne(u -> u.getEmail().equals(email));
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Could not retrieve user").build();
        }

        ActionRepository actionRepository = new ActionRepository();
        Action action = actionRepository.getOne(actionId);
        if (action == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Action not found").build();
        }
        if (action.getAvailablePosition() == 0) {

            return Response.status(Response.Status.NO_CONTENT).entity("Action not available").build();
        }

        UserAction userAction = new UserAction();
        userAction.setUser(user);
        userAction.setAction(action);

        EvaluationStatusRepository evaluationStatusRepository = new EvaluationStatusRepository();
        EvaluationStatus eval = evaluationStatusRepository.getOne(p -> p.getDescription()
                .equalsIgnoreCase(userAction.getEvaluationStatus().getDescription()));

        userAction.setEvaluationStatus(eval);
        user.getUserActions().add(userAction);
        userRepository.updateUser(user);
        return Response.ok("Action added to User action.").build();
    }


    @PermitAll
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteActionById(@PathParam("id")int id) {
        ActionRepository actionRepository = new ActionRepository();
        actionRepository.delete(id);
        return Response.ok("Action deleted.").build();
    }

    @PermitAll
    @POST
    @Path("/userProfiles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@PathParam("id") int id) {
        try{
        List<User> usersInformation = new ArrayList<>();
        ActionRepository actionRepository = new ActionRepository();

        List<Action> actions = actionRepository.getAll();
        Action actionR = null;
        for (Action a : actions) {
            if(a.getId().equals(id)){
                actionR = a;
                break;
            }
        }

        List<UserAction> userActionList;
        userActionList = actionR != null ? actionR.getUserActions() : null;

        assert userActionList != null;
        for (UserAction userAction : userActionList) {
            if (!userAction.isElected() && userAction.getEvaluationStatus().getId() != 4)
                usersInformation.add(userAction.getUser());
        }

        cleanFields(usersInformation);
        if (!usersInformation.isEmpty()) {
            return Response.ok().entity(usersInformation).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Action has no users.").build();
        }
        } catch (NullPointerException ex){
            return Response.status(Response.Status.NOT_FOUND).entity("Action has no users.").build();
        }
    }

    private void cleanFields(List<User> users){
        for (User u : users) {
            u.setToken("");
            u.setPassword("");
            u.setRole(null);
        }
    }

    @PermitAll
    @POST
    @Path("/institutionActions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInstitutionActions(@HeaderParam("Authorization")String token) {
        List<Action> actions = new ArrayList<>();
        ActionRepository actionRepository = new ActionRepository();

        List<Action> actionsR = actionRepository.getAll();
        for (Action a : actionsR) {
            if (a.getUser().getEmail().equals(TokenHelper.getEmail(token))) {
                actions.add(a);
            }
        }

        if (actions.size() > 0) {
            return Response.ok().entity(actions).build();
        } else {
            return Response.serverError().entity("Institution has no actions.").build();
        }
    }

    @PermitAll
    @GET
    @Path("/verifiedValid")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVerifiedValidActions() {
        ActionRepository actionRepository = new ActionRepository();
        List<Action> actions = actionRepository.getAll();
        actions=actions.stream().filter(p-> p.isActive() && p.isVerified()).collect(Collectors.toList());
        if (!actions.isEmpty()) {
            return Response.ok().entity(actions).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found").build();
        }
    }

    @PUT
    @Path("/validate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validateAction(@HeaderParam(value = "Authorization")final String token, Action action) {
        if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build(); }

        ActionRepository actionRepository = new ActionRepository();
        Action actionToValidate = actionRepository.getOne(p -> p.getUniqueId().contentEquals(action.getUniqueId()));
        if (actionToValidate == null) { return Response.status(Response.Status.NO_CONTENT).entity("Action not found").build(); }

        actionToValidate.setVerified(true);

        actionRepository.update(actionToValidate);

        return Response.ok("Action successfully validated").build();
    }

    @PermitAll
    @PUT
    @Path("/invalidate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response invalidateAction(@HeaderParam(value = "Authorization")final String token, Action action) {
        if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build(); }

        ActionRepository actionRepository = new ActionRepository();
        Action actionToInvalidate = actionRepository.getOne(p -> p.getUniqueId().contentEquals(action.getUniqueId()));
        if (actionToInvalidate == null) { return Response.status(Response.Status.NO_CONTENT).entity("Action not found").build(); }

        actionToInvalidate.setVerified(false);

        actionRepository.update(actionToInvalidate);

        return Response.ok("Action successfully invalidated").build();
    }

    @PermitAll
    @POST
    @Path("/acceptUser/{actionId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response acceptUser(@HeaderParam(value = "Authorization")final String token, @PathParam("actionId")int actionId, @PathParam("userId")String id) {
        try{
        if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build(); }

        UserRepository userRepository = new UserRepository();
        User institution = userRepository.getOne(p -> p.getEmail().equals(TokenHelper.getEmail(token)));
        if (institution == null) { return Response.status(Response.Status.NO_CONTENT).entity("Institution not found").build(); }

        ActionRepository actionRepository = new ActionRepository();
        Action action1 = actionRepository.getOne(p -> p.getId().equals(actionId));
        if (action1 == null) { return Response.status(Response.Status.NO_CONTENT).entity("Action not found").build(); }

        UserAction userAction = null;

        User user = userRepository.getOne(p -> p.getUniqueId().equals(id));
        for (UserAction ua : action1.getUserActions()) {
            if(ua.getUser().getUniqueId().equals(user.getUniqueId())){
                userAction = ua;
                break;
            }
        }

        if (userAction == null) { return Response.status(Response.Status.NO_CONTENT).entity("User action not found").build(); }

        UserActionRepository userActionRepository = new UserActionRepository();
        userAction.setElected(true);
        userActionRepository.update(userAction);

        action1.setAvailablePosition(action1.getAvailablePosition()-1);
        actionRepository.update(action1);
        return Response.ok("User accepted").build();

        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error accepting user.").build();
        }
    }

    @PermitAll
    @POST
    @Path("/declineUser/{actionId}/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response declineUser(@HeaderParam(value = "Authorization")final String token, @PathParam("actionId")int actionId, @PathParam("userId")String id) {
        try{
            if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build(); }

            UserRepository userRepository = new UserRepository();
            User institution = userRepository.getOne(p -> p.getEmail().equals(TokenHelper.getEmail(token)));
            if (institution == null) { return Response.status(Response.Status.NO_CONTENT).entity("Institution not found").build(); }

            ActionRepository actionRepository = new ActionRepository();
            Action action1 = actionRepository.getOne(p -> p.getId().equals(actionId));
            if (action1 == null) { return Response.status(Response.Status.NO_CONTENT).entity("Action not found").build(); }

            UserAction userAction = null;

            User user = userRepository.getOne(p -> p.getUniqueId().equals(id));
            for (UserAction ua : action1.getUserActions()) {
                if(ua.getUser().getUniqueId().equals(user.getUniqueId())){
                    userAction = ua;
                    break;
                }
            }

            if (userAction == null) { return Response.status(Response.Status.NO_CONTENT).entity("User action not found").build(); }

            UserActionRepository userActionRepository = new UserActionRepository();
            userAction.setElected(false);

            EvaluationStatusRepository evaluationStatusRepository = new EvaluationStatusRepository();
            EvaluationStatus eval = evaluationStatusRepository.getOne(p -> p.getDescription().equalsIgnoreCase("rejected"));

            if (eval == null) { return Response.status(Response.Status.NO_CONTENT).entity("eval not found").build(); }

            userAction.setEvaluationStatus(eval);
            userActionRepository.update(userAction);

            return Response.ok("User rejected").build();

        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error rejecting user.").build();
        }

    }

    @PermitAll
    @POST
    @Path("/acceptedUsers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAcceptedUsers(@HeaderParam(value = "Authorization")final String token, @PathParam("id") int actionId) {
        try{
            if (!TokenHelper.isValid(token)) { return Response.status(Response.Status.BAD_REQUEST).entity("Invalid token").build(); }
            ActionRepository actionRepository = new ActionRepository();

            List<UserAction> usersActions = actionRepository.getOne(p-> p.getId().equals(actionId)).getUserActions();

            List<User> users = new ArrayList<>(0);
            for (UserAction userAction : usersActions) {
                if(userAction.isElected()){
                    users.add(userAction.getUser());
                }
            }

            if(users.size() > 0){
                return Response.ok("User rejected").build();
            }else{
                return Response.noContent().build();
            }
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal error.").build();
        }
    }
}

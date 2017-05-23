package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.ActionRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.models.Action;
import org.feup.lgp2d.helpwin.models.User;
import org.feup.lgp2d.helpwin.models.UserAction;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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
    @Path("actions/{id}")
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
    public Response createAction(Action action) {
        ActionRepository actionRepository = new ActionRepository();
        action.generateUniqueId();
        UserRepository userRepository = new UserRepository();
        User user = userRepository.getUserByUniqueID(action.getUser().getUniqueId());
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

    @PermitAll
    @DELETE
    @Path("actions/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteActionById(@PathParam("id")int id) {
        ActionRepository actionRepository = new ActionRepository();
        actionRepository.delete(id);
        return Response.ok("Action deleted.").build();
    }

    @PermitAll
    @POST
    @Path("/userProfiles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(Action action) {
        List<User> usersInformation = new ArrayList<>();
        ActionRepository actionRepository = new ActionRepository();

        List<Action> actions = actionRepository.getAll();
        Action actionR = null;
        for (Action a : actions) {
            if(a.getUniqueId().equals(action.getUniqueId())){
                actionR = a;
                break;
            }
        }

        List<UserAction> userActionList = actionR.getUserActions();

        for (UserAction userAction : userActionList) {
            usersInformation.add(userAction.getUser());
        }

        cleanFileds(usersInformation);
        if (!usersInformation.isEmpty()) {
            return Response.ok().entity(usersInformation).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Action has no users.").build();
        }
    }

    private void cleanFileds(List<User> users){
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
    public Response getInstitutionActions(User user) {
        List<Action> actions = new ArrayList<>();
        ActionRepository actionRepository = new ActionRepository();

        List<Action> actionsR = actionRepository.getAll();
        for (Action a : actionsR) {
            if (a.getUser().getUniqueId().equals(user.getUniqueId())) {
                actions.add(a);
            }
        }

        if (actions.size() > 0) {
            return Response.ok().entity(actions).build();
        } else {
            return Response.serverError().entity("Institution has no actions.").build();
        }
    }

}

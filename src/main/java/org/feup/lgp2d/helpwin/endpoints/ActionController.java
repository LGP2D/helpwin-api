package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.authentication.util.TokenHelper;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.ActionRepository;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.UserRepository;
import org.feup.lgp2d.helpwin.models.Action;
import org.feup.lgp2d.helpwin.models.User;
import org.feup.lgp2d.helpwin.models.UserAction;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
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
    public Response getActionById(@PathParam("id") int id) {
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
    @Path("actions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAction(Action action) {
        ActionRepository actionRepository = new ActionRepository();
        Action actionToRetrieve = actionRepository.create(action);
        if (actionToRetrieve != null) {
            return Response.ok().entity(actionToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to save your record on database").build();
        }
    }

    @PermitAll
    @PUT
    @Path("actions")
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
    @Path("actions/submit/{actionId}")
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

            user.getUserActions().add(userAction);
            userRepository.updateUser(user);
            return Response.ok("Action added to User action.").build();
        }


    @PermitAll
    @DELETE
    @Path("actions/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteActionById(@PathParam("id") int id) {
        ActionRepository actionRepository = new ActionRepository();
        actionRepository.delete(id);
        return Response.ok("Action deleted.").build();
    }


}

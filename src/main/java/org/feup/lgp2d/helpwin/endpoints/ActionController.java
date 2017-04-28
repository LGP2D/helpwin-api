package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.ActionRepository;
import org.feup.lgp2d.helpwin.models.Action;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @PermitAll
    @DELETE
    @Path("actions/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteActionById(@PathParam("id")int id) {
        ActionRepository actionRepository = new ActionRepository();
        actionRepository.delete(id);
        return Response.ok("Action deleted.").build();
    }



}

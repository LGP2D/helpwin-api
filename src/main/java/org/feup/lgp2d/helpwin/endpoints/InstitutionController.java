package org.feup.lgp2d.helpwin.endpoints;


import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.InstitutionRepository;
import org.feup.lgp2d.helpwin.models.Action;
import org.feup.lgp2d.helpwin.models.Institution;
import org.feup.lgp2d.helpwin.models.User;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("institutions")
public class InstitutionController {

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInstitutions() {
        InstitutionRepository institutionRepository = new InstitutionRepository();
        List<Institution> institutions = institutionRepository.getAll();
        if (!institutions.isEmpty()) {
            return Response.ok().entity(institutions).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found").build();
        }
    }

    @PermitAll
    @GET
    @Path("institutions/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getInstitutionById(@PathParam("id")int id) {
        InstitutionRepository institutionRepository = new InstitutionRepository();
        Institution institution = institutionRepository.getOne(id);
        if (institution != null) {
            return Response.ok().entity(institution).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found!").build();
        }
    }

    @PermitAll
    @POST
    @Path("institutions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createInstitution(Institution institution) {
        InstitutionRepository institutionRepository = new InstitutionRepository();
        Institution institutionToRetrieve = institutionRepository.create(institution);
        if (institutionToRetrieve != null) {
            return Response.ok().entity(institutionToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to save your record on database").build();
        }
    }

    @PermitAll
    @PUT
    @Path("institutions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateInstitution(Institution institution) {
        InstitutionRepository institutionRepository = new InstitutionRepository();
        Institution institutionToRetrieve = institutionRepository.create(institution);
        if (institutionToRetrieve != null) {
            return Response.ok().entity(institutionToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to update your record on database").build();
        }
    }

    @PermitAll
    @DELETE
    @Path("institutions/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteInstitutionById(@PathParam("id")int id) {
        InstitutionRepository institutionRepository = new InstitutionRepository();
        institutionRepository.delete(id);
        return Response.ok("Institution deleted.").build();
    }

    @PermitAll
    @POST
    @Path("/userProfiles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInstitutionUserProfiles(Action action) {
        InstitutionRepository institutionRepository = new InstitutionRepository();
        List<User> users = institutionRepository.getUserProfiles(action);
        if (!users.isEmpty()) {
            return Response.ok().entity(users).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Action has no users.").build();
        }
    }

}

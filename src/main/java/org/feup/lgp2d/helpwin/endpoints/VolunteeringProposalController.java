package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.VolunteeringRepository;
import org.feup.lgp2d.helpwin.models.VolunteeringProposal;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("proposals")
public class VolunteeringProposalController {

    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProposals() {
        VolunteeringRepository volunteeringRepository = new VolunteeringRepository();
        List<VolunteeringProposal> volunteeringProposals = volunteeringRepository.getAll();
        if (!volunteeringProposals.isEmpty()){
            return Response.ok(volunteeringProposals).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("No records found").build();
        }
    }
}

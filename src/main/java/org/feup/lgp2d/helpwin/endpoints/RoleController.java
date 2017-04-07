package org.feup.lgp2d.helpwin.endpoints;

import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.RoleRepository;
import org.feup.lgp2d.helpwin.models.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("roles")
public class RoleController {

    /**
     * Get all roles
     * @return Response - returns a response with the roles embedded in the response package
     *                    or an error if something went wrong.
     */
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoles() {
        RoleRepository roleRepository = new RoleRepository();
        List<Role> roles = roleRepository.getAll();
        if (!roles.isEmpty()) {
            return Response.ok().entity(roles).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found!").build();
        }
    }

    /**
     * Get the role given the id
     * @param id the id of the role to be returned
     * @return Response - returns a response with the role embedded in the response package
     *                    or an error if something went wrong.
     */
    @PermitAll
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getRoleById(@PathParam("id")int id) {
        RoleRepository roleRepository = new RoleRepository();
        Role role = roleRepository.getOne(id);
        if (role != null) {
            return Response.ok().entity(role).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No records were found!").build();
        }
    }

    /**
     * Creates a role
     * @param role - the model of the role to be created
     * @return Response - returns the model of the created role or an error if something went wrong
     */
    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRole(Role role) {
        RoleRepository roleRepository = new RoleRepository();
        Role roleToRetrieve = roleRepository.create(role);
        if (roleToRetrieve != null) {
            return Response.ok().entity(roleToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to save your record on database").build();
        }
    }

    /**
     * Updates a role
     * @param role - the model of the role to be updated
     * @return Response - returns the role updated or an error if something went wrong
     */
    @PermitAll
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRole(Role role) {
        RoleRepository roleRepository = new RoleRepository();
        Role roleToRetrieve = roleRepository.create(role);
        if (roleToRetrieve != null) {
            return Response.ok().entity(roleToRetrieve).build();
        } else {
            return Response.serverError().entity("Something happened trying to update your record on database").build();
        }
    }

    /**
     * Deletes a role
     * @param id the id of the role to be deleted
     * @return Response - an Ok response if everything went successful, or an error if something went wrong
     */
    @PermitAll
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteRoleById(@PathParam("id")int id) {
        RoleRepository roleRepository = new RoleRepository();
        roleRepository.delete(id);
        return Response.ok("Role deleted.").build();
    }
}

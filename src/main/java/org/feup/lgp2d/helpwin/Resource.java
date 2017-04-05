package org.feup.lgp2d.helpwin;

import com.sun.org.apache.regexp.internal.RESyntaxException;
import org.feup.lgp2d.helpwin.dao.repositories.repositoryImplementations.RoleRepository;
import org.feup.lgp2d.helpwin.models.Role;

import javax.annotation.PreDestroy;
import javax.annotation.security.PermitAll;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("roles")
public class Resource {

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

    @PermitAll
    @GET
    @Path("roles/{id}")
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

    @PermitAll
    @POST
    @Path("roles")
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

    @PermitAll
    @PUT
    @Path("roles")
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

    @PermitAll
    @DELETE
    @Path("roles/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response deleteRoleById(@PathParam("id")int id) {
        RoleRepository roleRepository = new RoleRepository();
        roleRepository.delete(id);
        return Response.ok("Role deleted.").build();
    }
}

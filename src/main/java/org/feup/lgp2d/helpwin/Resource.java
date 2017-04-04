package org.feup.lgp2d.helpwin;

import org.feup.lgp2d.helpwin.customExceptions.HibernateExceptionMapper;
import org.feup.lgp2d.helpwin.dao.RoleDAO;
import org.feup.lgp2d.helpwin.dao.UserDAO;
import org.feup.lgp2d.helpwin.models.Role;
import org.feup.lgp2d.helpwin.models.User;
import org.hibernate.exception.ConstraintViolationException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("hello")
public class Resource {

    @PermitAll
    @GET
    @Path("world")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return "HelloWorld";
    }

    @PermitAll
    @GET
    @Path("roles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Role> getRoles() {
        RoleDAO dao = new RoleDAO();
        List roles = dao.getRoles();
        return roles;
    }

    @PermitAll
    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        UserDAO dao = new UserDAO();
        List users = dao.getAllUsers();
        return users;
    }

    @PermitAll
    @POST
    @Path("create-user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        UserDAO dao = new UserDAO();
        dao.addUser(user);

        return Response.ok().build();
    }
}

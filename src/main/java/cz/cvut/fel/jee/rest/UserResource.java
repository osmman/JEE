package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Created by Tomáš on 25.1.14.
 */
@Path("user")
@Stateless
public class UserResource extends AbstractResource<User> {

    @Inject
    protected UserService facade;

    @Override
    protected AbstractFacade<User> getFacade() {
        return facade;
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    @Override
    public Response edit(@PathParam("id") Long id, User item) {
        User edit = facade.find(id);
        if (edit == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        edit.setEmail(item.getEmail());
//        edit.setPassword(item.getPassword());
        return super.edit(id, edit);
    }

    @POST
    @Path("/")
    @RolesAllowed("admin")
    @Override
    public Response create(User item) {
        if (item.getEmail() == null || item.getPassword() == null) {
            return Response.status(Status.BAD_REQUEST).entity("Validation error").build();
        }
        return super.create(item); //To change body of generated methods, choose Tools | Templates.
    }

}

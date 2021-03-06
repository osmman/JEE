package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.model.EntityObject;
import cz.cvut.fel.jee.model.Role;
import cz.cvut.fel.jee.model.User;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Tomáš on 25.1.14.
 */
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public abstract class AbstractResource<T extends EntityObject>
{

    protected abstract AbstractFacade<T> getFacade();
    
    @Inject
    @CurrentLoggedUser
    protected User loggedUser;

    @PermitAll
    @GET
    @Path("/")
    public List<T> getAll(@HeaderParam("X-Base") Integer base,
                           @HeaderParam("X-Offset") Integer offset)
    {

        List<T> collection = getFacade().findAll(base, offset);
        return collection;
    }

    @PermitAll
    @GET
    @Path("/{id}")
    public T find(@PathParam("id") Long id)
    {
        T item = getFacade().find(id);
        return item;
    }

    @PermitAll
    @POST
    @Path("/")
    public Response create(T item)
    {
        try{
            getFacade().create(item);
            return Response.status(Response.Status.CREATED).build();
        }catch(EJBTransactionRolledbackException ex){
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @PermitAll
    @PUT
    @Path("/{id}")
    public Response edit(@PathParam("id") Long id, T item)
    {
        try{
            getFacade().edit(item);
            return Response.status(Response.Status.NO_CONTENT).build();
        }catch(EJBTransactionRolledbackException ex){
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }

    @RolesAllowed(Role.ADMIN)
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id)
    {
        T item = getFacade().find(id);
        if(item != null){
            getFacade().remove(item);
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

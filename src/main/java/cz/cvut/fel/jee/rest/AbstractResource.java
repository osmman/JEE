package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.model.EntityObject;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by Tomáš on 25.1.14.
 */
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public abstract class AbstractResource<T extends EntityObject>
{

    protected abstract AbstractFacade<T> getFacade();

    @GET
    @Path("/")
    public Response getAll(@HeaderParam("X-Base") Integer base,
                           @HeaderParam("X-Offset") Integer offset)
    {
        Collection<T> collection = getFacade().findAll(base, offset);

        GenericEntity<Collection<T>> entity = new GenericEntity<Collection<T>>(collection)
        {
        };
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("/{id}")
    public T find(@PathParam("id") Long id)
    {
        T item = getFacade().find(id);
        return item;
    }

    @POST
    @Path("/")
    public Response registration(T item)
    {
        getFacade().create(item);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response edit(@PathParam("id") Long id, T item)
    {
        getFacade().edit(item);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id)
    {
        T item = getFacade().find(id);
        getFacade().remove(item);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.User;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created by Tomáš on 25.1.14.
 */
@Path("user")
@Stateless
public class UserResource extends AbstractResource<User>
{

    @Inject
    protected UserService facade;


    @Override
    protected AbstractFacade<User> getFacade()
    {
        return facade;
    }

    @Override
    public User find(Long id) {
        facade.getAllComments(id);
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }
    
    @GET
    @Path("/{id}/"+RestApplication.COMMENTS)
    public Collection<Comment> getUserComments(@PathParam("id") Long id){
        Set<Comment> allComments = facade.getAllComments(id);
        return allComments;
    }
    
    
    @GET
    @Path("/{id}/"+RestApplication.VIDEOS)
    public Collection<Comment> getUserVideos(@PathParam("id") Long id){
        Set<Comment> allVideos = facade.getAllComments(id);
        return allVideos;
    }
    
    
}

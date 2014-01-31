/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.ejb.CommentService;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author saljack
 */
@Path("comment")
@Stateless
public class CommentResource extends AbstractResource<Comment> {

    @Inject
    protected CommentService facade;
    
    @Inject
    protected VideoService videoService;
    
    @Inject
    protected UserService userService;

    @Override
    protected AbstractFacade<Comment> getFacade() {
        return facade;
    }

    @Override
    public Response create(Comment item) {
        if(item.getAuthor() == null || item.getVideo() == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Validation error").build();
        }
        User u = userService.find(item.getAuthor().getId());
        Video v = videoService.find(item.getVideo().getId());
        Comment comment = new Comment();
        comment.setAuthor(u);
        comment.setVideo(v);
        comment.setText(item.getText());
        return super.create(comment); 
    }

    @Override
    public Response edit(Long id, Comment item) {
        if(item.getText() == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Validation error").build();
        }
        
        Comment comment = facade.find(id);
        if(comment == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        comment.setText(item.getText());
        return super.edit(id, comment); //To change body of generated methods, choose Tools | Templates.
    }
}

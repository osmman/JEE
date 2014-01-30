/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.ejb.CommentService;
import cz.cvut.fel.jee.model.Comment;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 *
 * @author saljack
 */
@Path("comment")
@Stateless
public class CommentResource extends AbstractResource<Comment>{

    @Inject
    protected CommentService facade;
    
    @Override
    protected AbstractFacade<Comment> getFacade() {
        return facade;
    }
    
}

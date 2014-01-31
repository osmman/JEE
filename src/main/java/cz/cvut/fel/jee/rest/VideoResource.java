/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.Video;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author saljack
 */
@Path("video")
@Stateless
public class VideoResource extends AbstractResource<Video> {

    @Inject
    protected VideoService facade;

    @Override
    protected AbstractFacade<Video> getFacade() {
        return facade;
    }

    @GET
    @Path("/{id}/" + RestApplication.COMMENTS)
    public Collection<Comment> getVideosComments(@PathParam("id") Long id) {
        List<Comment> allComments = facade.getAllComments(id);
        return allComments;
    }

    @Override
    public Response create(Video item) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @Override
    public Response edit(Long id, Video item) {
        Video edit = facade.find(id);
        edit.setName(item.getName());
        return super.edit(id, edit);
    }
}

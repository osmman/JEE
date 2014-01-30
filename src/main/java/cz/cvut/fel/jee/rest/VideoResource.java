/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Video;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Path;

/**
 *
 * @author saljack
 */
@Path("video")
@Stateless
public class VideoResource extends AbstractResource<Video>{

    @Inject
    protected VideoService facade;
    
    @Override
    protected AbstractFacade<Video> getFacade() {
        return facade;
    }    
    
}

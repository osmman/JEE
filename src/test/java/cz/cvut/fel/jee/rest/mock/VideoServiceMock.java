/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.mock;

import cz.cvut.fel.jee.ejb.AbstractFacadeImpl;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;

/**
 *
 * @author saljack
 */
@Stateless
public class VideoServiceMock extends AbstractFacadeImpl<Video> implements VideoService{
    
    @Inject
    private EntityManager em;

    public VideoServiceMock() {
        super(Video.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Video entity, Part video) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Video entity, InputStream is, String mimetype) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Video> findByAuthor(User author) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Comment> getAllComments(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public File getVideoFile(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int numberOfVideos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

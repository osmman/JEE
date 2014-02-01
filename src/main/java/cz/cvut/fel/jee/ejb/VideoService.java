/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.Part;

/**
 *
 * @author saljack
 */
public interface VideoService extends AbstractFacade<Video>{

    /**
     *
     * @param entity Entity (will be set path, mimetype(from Part video))
     * @param video Part for uploaded file
     */
    void create(Video entity, Part video);

    void create(Video entity, InputStream is, String mimetype);

    List<Video> findByAuthor(User author);

    List<Comment> getAllComments(Long id);

    File getVideoFile(Long id);

    List<Video> findAllPublished();

    int numberOfVideos();
    
}

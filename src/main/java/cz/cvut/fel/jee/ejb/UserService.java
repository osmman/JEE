/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import java.util.Set;

/**
 *
 * @author saljack
 */
public interface UserService extends AbstractFacade<User>{

    User findByEmail(String email);

    Set<Comment> getAllComments(Long id);

    Set<Video> getAllVideos(Long id);
    
}

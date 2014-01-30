/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.rest.entity.UserXml;
import cz.cvut.fel.jee.rest.entity.links.CommentLinkXml;
import cz.cvut.fel.jee.rest.entity.links.VideoLinkXml;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author saljack
 */
public class UserAdapter extends LinkAdapter<UserXml, User>{

    @Inject
    private UserService userService;

    public UserAdapter() {
        super(UserXml.class);
    }
    
    
    
    @Override
    public User unmarshal(UserXml v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public UserXml marshal(User v) throws Exception {
        UserXml user = super.marshal(v);
        user.setEmail(v.getEmail());
        user.setId(v.getId());
        user.setPassword(v.getPassword());
        
        List<CommentLinkXml> comments = new LinkedList<>();
        //TODO set comment;
        user.setComments(comments);
        
        List<VideoLinkXml> videos = new LinkedList<>();
        //TODO set videos
        user.setVideos(videos);
        
        return user;
    }
    
}

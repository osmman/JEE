/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.entity;

import cz.cvut.fel.jee.rest.entity.links.CommentLinkXml;
import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;
import cz.cvut.fel.jee.rest.entity.links.VideoLinkXml;
import java.util.Set;

/**
 *
 * @author saljack
 */
public class UserXml extends UserLinkXml{
    private String email;

    private String password;

    private Set<VideoLinkXml> videos;
    
    private Set<CommentLinkXml> comments;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<VideoLinkXml> getVideos() {
        return videos;
    }

    public void setVideos(Set<VideoLinkXml> videos) {
        this.videos = videos;
    }

    public Set<CommentLinkXml> getComments() {
        return comments;
    }

    public void setComments(Set<CommentLinkXml> comments) {
        this.comments = comments;
    }
    
    
}

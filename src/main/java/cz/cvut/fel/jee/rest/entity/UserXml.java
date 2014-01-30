/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.entity;

import cz.cvut.fel.jee.rest.entity.links.CommentLinkXml;
import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;
import cz.cvut.fel.jee.rest.entity.links.VideoLinkXml;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author saljack
 */
public class UserXml extends UserLinkXml {

    private String email;

    private String password;

    @XmlElement
    private List<VideoLinkXml> videos;

    @XmlElement
    private List<CommentLinkXml> comments;

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

    public List<VideoLinkXml> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoLinkXml> videos) {
        this.videos = videos;
    }

    public List<CommentLinkXml> getComments() {
        return comments;
    }

    public void setComments(List<CommentLinkXml> comments) {
        this.comments = comments;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.entity;

import cz.cvut.fel.jee.rest.entity.links.CommentLinkXml;
import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;
import cz.cvut.fel.jee.rest.entity.links.VideoLinkXml;
import java.util.Date;
import java.util.List;

/**
 *
 * @author saljack
 */
public class VideoXml extends VideoLinkXml{
    private String name;

    private String path;
    
    private String mimetype;

    private Boolean published;

    private UserLinkXml author;

    private List<CommentLinkXml> comments;

    private Date createdAt;

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public Boolean isPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public UserLinkXml getAuthor() {
        return author;
    }

    public void setAuthor(UserLinkXml author) {
        this.author = author;
    }

    public List<CommentLinkXml> getComments() {
        return comments;
    }

    public void setComments(List<CommentLinkXml> comments) {
        this.comments = comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.entity;

import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;
import cz.cvut.fel.jee.rest.entity.links.VideoLinkXml;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author saljack
 */
public class VideoXml extends VideoLinkXml{
    @XmlElement(required = true)
    private String name;

    @XmlElement(required = true)
    private String path;
    
    @XmlElement(required = true)
    private String mimetype;

    private Boolean published;

    @XmlElement(required = true)
    private UserLinkXml author;

    private Date createdAt;
    
    private static final String comments = "comments";

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

    public String getComments() {
        return getPath()+"/"+comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
}

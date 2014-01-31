/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.entity;

import cz.cvut.fel.jee.rest.entity.links.CommentLinkXml;
import cz.cvut.fel.jee.rest.entity.links.VideoLinkXml;
import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author saljack
 */

public class CommentXml extends CommentLinkXml{

    @XmlElement(required = true)
    private String text;

    @XmlElement(required = true)
    private UserLinkXml author;
    
    @XmlElement(required = true)
    private VideoLinkXml video;

    private Date datetime;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserLinkXml getAuthor() {
        return author;
    }

    public void setAuthor(UserLinkXml author) {
        this.author = author;
    }

    public VideoLinkXml getVideo() {
        return video;
    }

    public void setVideo(VideoLinkXml video) {
        this.video = video;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    
}

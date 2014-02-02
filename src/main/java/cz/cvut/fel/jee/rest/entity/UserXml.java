/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.entity;

import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author saljack
 */
public class UserXml extends UserLinkXml {

    @XmlElement(required = true)
    private String email;

    private String password;
    
    private static final String videos = "videos";
    private static final String comments = "comments";

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

    public String getVideos() {
        return getPath()+"/"+videos;
    }

    public String getComments() {
        return getPath()+"/"+comments;
    }
}

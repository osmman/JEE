/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.entity;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author saljack
 */
public abstract class LinkXml {
        
    protected Long id;

    @XmlElement
    public String getPath() {
        return getName()+"/"+getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    protected abstract String getName();
}

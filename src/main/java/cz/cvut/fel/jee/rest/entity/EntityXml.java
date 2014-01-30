/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.entity;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author saljack
 */
@XmlRootElement
public abstract class EntityXml {
    
    private static final String address = "/semestralka/api/";
    
    protected Long id;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPath(){
        return address + getName()+"/"+getId();
    }
    
    protected abstract String getName();
}

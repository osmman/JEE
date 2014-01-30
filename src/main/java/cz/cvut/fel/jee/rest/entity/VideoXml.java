/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.entity;

import cz.cvut.fel.jee.rest.entity.LinkXml;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author saljack
 */
@XmlRootElement
public class VideoXml extends LinkXml{
    
    private static final String name = "video";
    
    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

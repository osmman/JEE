/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.entity.links;

import cz.cvut.fel.jee.rest.entity.EntityXml;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author saljack
 */
@XmlRootElement
public class VideoLinkXml extends EntityXml{
    
    private static final String name = "video";
    
    @Override
    protected String getName() {
        return name;
    }
    
}

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

/**
 *
 * @author saljack
 */
public class VideoLinkXml extends EntityXml{
    
    private static final String name = "video";

    public VideoLinkXml() {
    }

    public VideoLinkXml(Long id) {
        super(id);
    }
    
    @Override
    protected String getName() {
        return name;
    }
    
    public String getDownload(){
        return "/semestralka/video?videoid="+id;
    }
    
}

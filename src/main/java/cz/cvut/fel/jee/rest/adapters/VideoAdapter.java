/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.rest.entity.VideoXml;
import cz.cvut.fel.jee.model.Video;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author saljack
 */
public class VideoAdapter extends XmlAdapter<VideoXml, Video>{

    @Override
    public Video unmarshal(VideoXml v) throws Exception {
        return new Video();
    }

    @Override
    public VideoXml marshal(Video v) throws Exception {
        VideoXml xml = new VideoXml();
        xml.setId(v.getId());
        return xml;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.model.Video;
import cz.cvut.fel.jee.rest.entity.links.VideoLinkXml;

/**
 *
 * @author saljack
 */
public class VideoLinkAdapter extends LinkAdapter<VideoLinkXml, Video>{

    public VideoLinkAdapter() {
        super(VideoLinkXml.class, Video.class);
    }
    
}

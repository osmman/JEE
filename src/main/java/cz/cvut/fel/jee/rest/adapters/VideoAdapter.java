/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.model.Video;
import cz.cvut.fel.jee.rest.entity.VideoXml;
import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;

/**
 *
 * @author saljack
 */
public class VideoAdapter extends LinkAdapter<VideoXml, Video> {

    public VideoAdapter() {
        super(VideoXml.class, Video.class);
    }

    @Override
    public Video unmarshal(VideoXml v) throws Exception {
        Video video = super.unmarshal(v);
        video.setMimetype(v.getMimetype());
        video.setCreatedAt(v.getCreatedAt());
        video.setPublished(v.isPublished());
        return video;
    }

    @Override
    public VideoXml marshal(Video v) throws Exception {
        VideoXml video = super.marshal(v);
        video.setMimetype(v.getMimetype());
        video.setCreatedAt(v.getCreatedAt());
        video.setPublished(v.getPublished());
        video.setAuthor(new UserLinkXml(v.getAuthor().getId()));
        return video;

    }

}

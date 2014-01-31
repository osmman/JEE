/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.ejb.CommentService;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.exceptions.NotFoundException;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.Video;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.infinispan.io.GridFilesystem;

/**
 *
 * @author saljack
 */
@Named(value = "videoBean")
@RequestScoped
public class VideoBean {

    @Inject
    private Logger log;

    @Inject
    @VideoFilesystem
    private GridFilesystem fs;

    @Inject
    private FacesContext facesContext;

    @Inject
    private VideoService videoService;

    @Inject
    private CommentService commentService;

    private Long videoid;

    private String videoName;

    private String autohorName;
    
    private Video entity;

    @PostConstruct
    protected void init() {
        try {
            Map<String, String> parameterMap = (Map<String, String>) facesContext.getExternalContext().getRequestParameterMap();
            if (parameterMap.containsKey("videoid")) {
                videoid = Long.valueOf(parameterMap.get("videoid"));
            }

            if (videoid != null) {
                entity = videoService.find(videoid);
                if (entity != null) {
                    videoName = entity.getName();
                    autohorName = entity.getAuthor().getEmail();
                } else {
                    throw new NotFoundException();
                }
            }else{
                videoName = "";
                autohorName = "";
            }
        }catch(NumberFormatException ex){
            log.warning(ex.toString());
        }
    }

    /**
     * Creates a new instance of Video
     */
    public VideoBean() {
    }

    public boolean isExistsVideo() {
        if (entity != null) {
            File file = fs.getFile(entity.getPath());
            return file.exists();
        }
        return false;
    }

    public Long getVideoid() {
        return videoid;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }

    @RolesAllowed("admin")
    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getAutohorName() {
        return autohorName;
    }

    public void setAutohorName(String autohorName) {
        this.autohorName = autohorName;
    }

    public List<Comment> getComments(){
        return commentService.findByVideo(entity);
    }

}

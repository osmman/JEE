/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Video;
import java.io.File;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
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

    private Long videoid;

    private String videoName;

    private String autohorName;

    @PostConstruct
    protected void init() {
        try {
            Map<String, String> parameterMap = (Map<String, String>) facesContext.getExternalContext().getRequestParameterMap();
            if (parameterMap.containsKey("videoid")) {
                videoid = Long.valueOf(parameterMap.get("videoid"));
            }

            if (videoid != null) {
                Video entity = videoService.find(videoid);
                if (entity != null) {
                    videoName = entity.getName();
                    autohorName = entity.getAuthor().getEmail();
                } else {
                    videoName = videoid + "";
                    autohorName = "";
                }
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
        log.warning(videoid + " id");
        if (videoid != null) {
            File file = fs.getFile("/video/uploaded/" + videoid + ".mp4");
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

}

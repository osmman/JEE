/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.ejb.CommentService;
import cz.cvut.fel.jee.ejb.NewsService;
import cz.cvut.fel.jee.exceptions.NotFoundException;
import cz.cvut.fel.jee.model.News;
import cz.cvut.fel.jee.model.Video;
import org.infinispan.io.GridFilesystem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author saljack
 */
@Named(value = "newsBean")
@RequestScoped
public class NewsBean {

    @Inject
    private Logger log;

    @Inject
    @VideoFilesystem
    private GridFilesystem fs;

    @Inject
    private FacesContext facesContext;

    @Inject
    private NewsService newsService;

    @Inject
    private CommentService commentService;

    private Long newsid;

    private Date createdAt;

    private List<Video> videoList;

    private News entity;

    @PostConstruct
    protected void init() {
        try {
            Map<String, String> parameterMap = (Map<String, String>) facesContext.getExternalContext().getRequestParameterMap();
            if (parameterMap.containsKey("newsid")) {
                newsid = Long.valueOf(parameterMap.get("newsid"));
            }

            if (newsid != null) {
                entity = newsService.find(newsid);
                if (entity != null) {
                    log.info("Count of loaded videos: " + entity.getVideos().size());
                    createdAt = entity.getCreatedAt();
                    videoList = entity.getVideos();
                } else {
                    throw new NotFoundException();
                }
            }else{
                createdAt = new Date();
                videoList = Collections.EMPTY_LIST;
            }
        }catch(NumberFormatException ex){
            log.warning(ex.toString());
        }
    }

    /**
     * Creates a new instance of News
     */
    public NewsBean() {
    }

    public Long getNewsid() {
        return newsid;
    }

    public void setNewsid(Long newsid) {
        this.newsid = newsid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Video> getVideoList() {
        log.info("Videos count: " + videoList.size());
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}

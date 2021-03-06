/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.client.maps.MapsClient;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author saljack
 */
@Named(value = "videoUploadBean")
@RequestScoped
public class VideoUpload implements Serializable {

    private static final String videoMimeTypes[] = {"video/avi", "video/msvideo", "video/x-msvideo", "video/mp4", "video/mpeg", "video/x-matroska", "video/webm", "video/ogg", "video/x-ms-wmv"};

    Part video;

    Long videoid;

    @Inject
    private transient Logger log;

    @Inject
    private transient VideoService videoService;

    @Inject
    @CurrentLoggedUser
    User logedUser;

    /**
     * Creates a new instance of VideoUpload
     */
    public VideoUpload() {
    }

    public Part getVideo() {
        return video;
    }

    public void setVideo(Part video) {
        this.video = video;
    }

    public Long getVideoid() {
        return videoid;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }

    public String upload() {

        if (video != null) {
            Video entity = new Video();
            entity.setAuthor(logedUser);
            entity.setName(getFilename(video));
            videoService.create(entity,video);
            setVideoid(entity.getId());
        }

        return "";
    }

    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        List<FacesMessage> msgs = new ArrayList<FacesMessage>();
        Part file = (Part) value;
        if (file.getSize() > 1024 * 1024 * 1024) {
            msgs.add(new FacesMessage("File too big"));
        }

        boolean isVideo = false;
        for (String mimetype : videoMimeTypes) {
            if (mimetype.equals(file.getContentType())) {
                isVideo = true;
                break;
            }
        }

        if (!isVideo) {
            msgs.add(new FacesMessage("File is not video " + file.getContentType()));
        }

        if (!msgs.isEmpty()) {
            throw new ValidatorException(msgs);
        }
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

}

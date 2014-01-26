/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.http.Part;
import org.infinispan.io.GridFilesystem;

/**
 *
 * @author saljack
 */
@Named(value = "videoUploadBean")
@RequestScoped
public class VideoUpload implements Serializable {

    private static final String videoMimeTypes[] = {"video/avi", "video/msvideo", "video/x-msvideo", "video/mp4", "video/mpeg", "video/x-matroska", "video/webm"};

    Part video;

    Long videoid;

    @Inject
    GridFilesystem fileSystem;

    @Inject
    private transient Logger log;

    @Inject
    private VideoService videoService;

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
            //TODO create new video entity
            log.warning(logedUser.toString());
            Video entity = new Video();
            entity.setAuthor(logedUser);
            entity.setName(getFilename(video));
            videoService.create(entity);
            try {

                File dir = fileSystem.getFile("/video/uploaded");
                if (!dir.exists()) {
                    dir.mkdirs();
                    log.info("Creating dirs");
                }
                InputStream is = video.getInputStream();
                log.warning("/video/uploaded/" + entity.getId() + ".mp4");
                OutputStream os = fileSystem.getOutput("/video/uploaded/" + entity.getId() + ".mp4");
                byte[] buffer = new byte[20000];
                int len;
                while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                    os.write(buffer, 0, len);
                }
                is.close();
                os.close();
                setVideoid(entity.getId());
                log.info("File id:" + entity.getId() + " name: " + entity.getName() + " is writed!");
            } catch (IOException e) {
                log.warning(e.toString());
                videoService.remove(entity);
            }
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

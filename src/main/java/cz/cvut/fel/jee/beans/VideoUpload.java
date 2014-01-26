/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import org.infinispan.io.GridFilesystem;

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

    private static final String videoMimeTypes[] = {"video/avi", "video/msvideo", "video/x-msvideo", "video/mp4", "video/mpeg", "video/x-matroska", "video/webm", "video/ogg"};

    Part video;

    Long videoid;

    @Inject
    @VideoFilesystem
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
                log.warning("Video submited name: " + video.getSubmittedFileName());
                entity.setPath("/video/uploaded/" + entity.getId() + getExtension(entity.getName()));
                entity.setMimetype(video.getContentType());
                log.warning(entity.getMimetype());

                createDirectory();
                InputStream is = video.getInputStream();
                OutputStream os = fileSystem.getOutput(entity.getPath());
                byte[] buffer = new byte[20480];
                int len;
                while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                    os.write(buffer, 0, len);
                }
                is.close();
                os.close();
                setVideoid(entity.getId());
                videoService.edit(entity);
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

    private static String getExtension(String filename) {
        int dot = filename.lastIndexOf(".");
        if (dot == -1) {
            return "";
        }
        return filename.substring(dot + 1);
    }

    private void createDirectory() {
        File dir = fileSystem.getFile("/video/uploaded");
        if (!dir.exists()) {
            dir.mkdirs();
            log.info("Creating dirs");
        }
    }

}

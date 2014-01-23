/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import javax.servlet.http.Part;

/**
 *
 * @author saljack
 */
@Named(value = "videoUploadBean")
@RequestScoped
public class VideoUpload implements Serializable {

    private static final String videoMimeTypes[] = {"video/avi", "video/msvideo", "video/x-msvideo", "video/mp4", "video/mpeg", "video/x-matroska", "video/webm"};
    Part video;

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

    public String upload() {
        try {
            if (video != null) {

                video.write(video.getSubmittedFileName());
                System.out.println("FILE SAVED");
            }
        } catch (IOException e) {
            // Error handling
            System.err.println("Error");
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

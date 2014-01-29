/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.utils;

import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;

/**
 *
 * @author saljack
 */
@Singleton
@Transactional
@Startup
public class StartUp {

    @Inject
    private UserService userService;

    @Inject
    private VideoService videoService;

    @Inject
    private ServletContext ctx;

    @PostConstruct
    public void init() {
        try {
            populate();
            uploadVideo();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(StartUp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void populate() throws NoSuchAlgorithmException {
        User user = new User();
        user.setEmail("trnkamich@gmail.com");
        user.setPassword("heslo1");
        userService.create(user);
        user = new User();
        user.setEmail("turek@gmail.com");
        user.setPassword("heslo2");
        userService.create(user);
        user = new User();
        user.setEmail("vlasta@gmail.com");
        user.setPassword("heslo3");
        userService.create(user);
        user = new User();
        user.setEmail("tompol@gmail.com");
        user.setPassword("heslo4");
        userService.create(user);
    }

    public void uploadVideo() {
        if (videoService.numberOfVideos() < 1) {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("video/video.mp4");
            Video video = new Video();
            video.setName("video.mp4");
            User user = userService.find(4L);
            video.setAuthor(user);
            videoService.create(video, resourceAsStream, "video/mp4");
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.trash;

import cz.cvut.fel.jee.beans.NewsGeneratorTimer;
import cz.cvut.fel.jee.ejb.NewsService;
import cz.cvut.fel.jee.ejb.RoleService;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Role;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private NewsService newsService;
    
    @Inject
    private RoleService roleService;

    @Inject
    private ServletContext ctx;

    @Inject
    private NewsGeneratorTimer newsGeneratorTimer;

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
        Role adminRole = new Role();
        adminRole.setName("admin");
        roleService.create(adminRole);

        Role userRole = new Role();
        userRole.setName("user");
        roleService.create(userRole);

        User user = new User();
        user.setEmail("trnkamich@gmail.com");
        user.setPassword("heslo1");
        HashSet<Role> roles = new HashSet<Role>();
        roles.add(userRole);
        roles.add(adminRole);
        user.setRoles(roles);
        userService.create(user);

        user = new User();
        user.setEmail("turek@gmail.com");
        user.setPassword("heslo2");
        roles = new HashSet<Role>();
        roles.add(userRole);
        user.setRoles(roles);
        userService.create(user);

        user = new User();
        user.setEmail("vlasta@gmail.com");
        user.setPassword("heslo3");
        roles = new HashSet<Role>();
        roles.add(userRole);
        user.setRoles(roles);
        userService.create(user);

        user = new User();
        user.setEmail("tompol@gmail.com");
        user.setPassword("heslo4");
        roles = new HashSet<Role>();
        roles.add(userRole);
        user.setRoles(roles);
        userService.create(user);
    }

    public void uploadVideo() {
        if (videoService.numberOfVideos() < 1) {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("video/video.mp4");
            Video video = new Video();
            video.setName("video.mp4");
            video.setPublished(true);
            User user = userService.find(4L);
            video.setAuthor(user);
            videoService.create(video, resourceAsStream, "video/mp4");
            newsGeneratorTimer.add(video);
            newsGeneratorTimer.run();
        }
    }
}

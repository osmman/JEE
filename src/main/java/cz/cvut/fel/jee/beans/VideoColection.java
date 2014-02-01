package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Tomáš on 28.1.14.
 */
@Named("videos")
@RequestScoped
public class VideoColection {

    @Inject
    @CurrentLoggedUser
    private User user;

    @Inject
    private VideoService videoService;

    @Named("myVideoList")
    public List<Video> getMy(){
        return videoService.findByAuthor(user);
    }

    @Named("allVideo")
    public List<Video> getAll(){
        return videoService.findAll();
    }

    public List<Video> getAllPublished(){
        return videoService.findAllPublished();
    }

    public void delete(Video video){
        videoService.remove(video);
    }

}

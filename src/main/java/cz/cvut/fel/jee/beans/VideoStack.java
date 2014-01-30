package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.model.Video;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Stack;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 30.1.14
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
@Singleton
public class VideoStack {

    @Inject
    private Logger log;

    private Stack<Video> videoStack = new Stack<Video>();

    public void push(Video video) {
        log.info("Push item to video stack");
        videoStack.push(video);
    }

    public Video pop() {
        log.info("Pop item from video stack.");
        return videoStack.pop();
    }

}

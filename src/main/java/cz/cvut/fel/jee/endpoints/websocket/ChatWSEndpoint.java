/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.endpoints.websocket;

import cz.cvut.fel.jee.ejb.CommentService;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Comment;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author saljack
 */
@ServerEndpoint(value = "/chat/{videoid}", encoders = CommentEncoder.class, decoders = CommentDecoder.class)
@Singleton
public class ChatWSEndpoint {

    @Inject
    private Logger log;

    @Inject
    private CommentService commentService;

    @Inject
    private VideoService videoService;

    @OnOpen
    public void open(@PathParam("videoid") Long videoid, Session client) {
        client.getUserProperties().put("videoid", videoid);
    }

    @OnMessage
    public void onMessage(@PathParam("videoid") Long videoid, CommentMessage message, Session peer) {
        message.setVideoId(videoid);
        Comment comment = commentService.addCommentMessage(message);
        if (comment != null) {
            for (Session client : peer.getOpenSessions()) {
                try {
                    if (client.isOpen() && videoid.equals(client.getUserProperties().get("videoid"))) {
                        client.getBasicRemote().sendObject(comment);
                    }
                } catch (IOException | EncodeException ex) {
                    log.log(Level.WARNING, ex.toString(), ex);
                }
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable thr) {
        log.log(Level.WARNING, thr.toString(), thr);
    }

}

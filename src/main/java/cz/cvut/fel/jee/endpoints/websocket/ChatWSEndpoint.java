/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.endpoints.websocket;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.ejb.CommentService;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import cz.cvut.fel.jee.security.Authenticator;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author saljack
 */
@ServerEndpoint(value = "/chat/{videoid}", encoders = CommentEncoder.class, decoders = CommentDecoder.class)
public class ChatWSEndpoint {

    @Inject
    private transient Logger log;

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
                    log.warning(ex.toString());
                }
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable thr) {
        log.warning(thr.getMessage());
    }

}

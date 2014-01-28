/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.endpoints;

import java.io.IOException;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author saljack
 */
@ServerEndpoint("/chat/{videoid}")
public class ChatWSEndpoint {

    @Inject
    private transient Logger logger;
    
    @OnOpen
    public void open(@PathParam("videoid") Long videoid, Session peer){
        peer.getUserProperties().put("videoid", videoid);
    }

    @OnMessage
    public void onMessage(@PathParam("videoid") Long videoid, String message, Session peer) {
        logger.info(message);
        for (Session client : peer.getOpenSessions()) {
            try {
                if (client.isOpen() && videoid.equals(client.getUserProperties().get("videoid"))) {
                    client.getBasicRemote().sendText(peer.getId() + ": " + message);
                }

            } catch (IOException ex) {
                logger.severe(ex.toString());
            }
        }
    }

}

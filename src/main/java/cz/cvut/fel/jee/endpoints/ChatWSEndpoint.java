/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.endpoints;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author saljack
 */
@ServerEndpoint("/chat")
public class ChatWSEndpoint {

    @Inject
    private Logger logger;

    @OnMessage
    public void onMessage(String message, Session peer) {
        logger.info(message);
        for (Session client : peer.getOpenSessions()) {
            try {
                client.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                logger.severe(ex.toString());
            }
        }
    }

}

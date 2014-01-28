/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.endpoints.websocket;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author saljack
 */
public class CommentDecoder implements Decoder.Text<CommentMessage> {

    @Override
    public CommentMessage decode(String arg0) throws DecodeException {
        CommentMessage comment = new CommentMessage();
        JsonObject json = Json.createReader(new StringReader(arg0)).readObject();
        comment.setText(json.getString("text"));
        return comment;
    }

    @Override
    public boolean willDecode(String arg0) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

}

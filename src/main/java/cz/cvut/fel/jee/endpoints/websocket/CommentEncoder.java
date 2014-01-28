/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.endpoints.websocket;

import cz.cvut.fel.jee.model.Comment;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author saljack
 */
public class CommentEncoder implements Encoder.Text<Comment> {

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(Comment object) throws EncodeException {
        String json = Json.createObjectBuilder()
                .add("text", object.getText())
                .add("date", object.getDatetime().toString())
                .add("author", object.getAuthor().getEmail())
                .add("authorID", object.getAuthor().getId())
                .build().toString();

        return json;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.rest.entity.CommentXml;
import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;
import cz.cvut.fel.jee.rest.entity.links.VideoLinkXml;

/**
 *
 * @author saljack
 */
public class CommentAdapter extends LinkAdapter<CommentXml, Comment> {

    public CommentAdapter() {
        super(CommentXml.class, Comment.class);
    }

    @Override
    public Comment unmarshal(CommentXml v) throws Exception {
        Comment comment = super.unmarshal(v);
        comment.setDatetime(v.getDatetime());
        comment.setText(v.getText());
        if(v.getAuthor() != null){
            comment.setAuthor(new UserLinkAdapter().unmarshal(v.getAuthor()));
        }
        if(v.getVideo() != null){
            comment.setVideo(new VideoLinkAdapter().unmarshal(v.getVideo()));
        }
        return comment;
    }

    @Override
    public CommentXml marshal(Comment v) throws Exception {
        CommentXml comment = super.marshal(v);
        comment.setDatetime(v.getDatetime());
        comment.setText(v.getText());
        comment.setVideo(new VideoLinkXml(v.getVideo().getId()));
        comment.setAuthor(new UserLinkXml(v.getAuthor().getId()));
        return comment;
    }

}

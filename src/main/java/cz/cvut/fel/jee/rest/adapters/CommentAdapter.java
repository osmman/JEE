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

/**
 *
 * @author saljack
 */
public class CommentAdapter extends LinkAdapter<CommentXml, Comment> {

    public CommentAdapter() {
        super(CommentXml.class);
    }

    @Override
    public Comment unmarshal(CommentXml v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CommentXml marshal(Comment v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}

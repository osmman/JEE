/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.rest.entity.links.CommentLinkXml;

/**
 *
 * @author saljack
 */
public class CommentLinkAdapter extends LinkAdapter<CommentLinkXml, Comment>{

    public CommentLinkAdapter() {
        super(CommentLinkXml.class);
    }
    
}

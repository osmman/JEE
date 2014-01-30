/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.rest.entity.CommentXml;
import cz.cvut.fel.jee.model.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author saljack
 */
public class CommentAdapter extends XmlAdapter<List<CommentXml>, List<Comment>> {

    
    @Override
    public List<Comment> unmarshal(List<CommentXml> v){
        return null;
    }

    @Override
    public List<CommentXml> marshal(List<Comment> v){
        System.out.println("MARSHAL");
        System.out.println(v);
        System.out.println(v.size());
        List<CommentXml> ret = new ArrayList<>(v.size());
        for (Comment comment : v) {
            CommentXml c = new CommentXml();
            c.setId(comment.getId());
            System.out.println(c.toString());
            ret.add(c);
        }
        return ret;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.entity;

import cz.cvut.fel.jee.rest.entity.LinkXml;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author saljack
 */
@XmlRootElement
public class CommentXml extends LinkXml {

    private static final String name = "comment";

    @Override
    protected String getName() {
        return name;
    }

}

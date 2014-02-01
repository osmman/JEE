/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.rest.entity.links.UserLinkXml;

/**
 *
 * @author saljack
 */
public class UserLinkAdapter extends LinkAdapter<UserLinkXml, User>{

    public UserLinkAdapter() {
        super(UserLinkXml.class, User.class);
    }
    
}

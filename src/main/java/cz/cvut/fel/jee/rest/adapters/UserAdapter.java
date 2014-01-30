/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.rest.entity.UserXml;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author saljack
 */
public class UserAdapter extends XmlAdapter<UserXml, User>{

    @Override
    public User unmarshal(UserXml v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserXml marshal(User v) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.rest.adapters;

import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.rest.entity.UserXml;
import javax.transaction.Transactional;

/**
 *
 * @author saljack
 */
public class UserAdapter extends LinkAdapter<UserXml, User> {

    public UserAdapter() {
        super(UserXml.class, User.class);
    }

    @Override
    public User unmarshal(UserXml v) throws Exception {
        User user = super.unmarshal(v);
        user.setEmail(v.getEmail());
        user.setPassword(v.getPassword());
        return user;
    }

    @Transactional
    @Override
    public UserXml marshal(User v) throws Exception {
        UserXml user = super.marshal(v);
        user.setEmail(v.getEmail());
        user.setPassword(v.getPassword());
        return user;
    }

}

package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.ejb.RoleService;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.Role;
import cz.cvut.fel.jee.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by Tomáš on 29.1.14.
 */
@Named
@RequestScoped
public class Registration {

    private User newUser;

    @Inject
    private UserService userService;

    @Inject
    private RoleService roleService;

    @PostConstruct
    private void init(){
        Role role = roleService.findByName(Role.USER);
        newUser = new User();
        newUser.setRoles(new HashSet<Role>());
        newUser.getRoles().add(role);
    }

    public String registrate(){
        userService.create(newUser);
        init();
        return "login.xhtml&faces-redirect=true";
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}

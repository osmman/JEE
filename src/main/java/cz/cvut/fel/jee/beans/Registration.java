package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Tomáš on 29.1.14.
 */
@Named
@RequestScoped
public class Registration {

    private User newUser;

    @Inject
    private UserService userService;

    @PostConstruct
    private void init(){
        newUser = new User();
    }

    public String registrate(){
        userService.create(newUser);
        init();
        return "index";
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}

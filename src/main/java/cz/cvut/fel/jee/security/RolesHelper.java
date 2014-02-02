package cz.cvut.fel.jee.security;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.model.Role;
import cz.cvut.fel.jee.model.User;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Tomáš on 2.2.14.
 */
@Named("rolesHelper")
public class RolesHelper {

    private Role admin;

    @Inject
    @CurrentLoggedUser
    private User user;

    @PostConstruct
    private void init(){
        admin = new Role();
        admin.setName(Role.ADMIN);
    }

    public boolean isAdmin(User user){
        return hasRole(user, Role.ADMIN);
    }

    public boolean isAdmin(){
        return hasRole(user, Role.ADMIN);
    }

    public boolean hasRole(User user,String role){
        if(user == null) return false;
        return user.getRoles().contains(admin);
    }
}

package cz.cvut.fel.jee.beans.administration;

import cz.cvut.fel.jee.ejb.RoleService;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.Role;
import cz.cvut.fel.jee.model.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by Tomáš on 2.2.14.
 */
@Named("usersBean")
@RequestScoped
public class UsersBean {

    @Inject
    private UserService userService;

    @Inject
    private RoleService roleService;

    public List<User> getAll(){
        return userService.findAll();
    }

    public void addAdminRole(User user) {
        Role admin = roleService.findByName(Role.ADMIN);

        user.getRoles().add(admin);
        userService.edit(user);
    }


    public void removeAdminRole(User user) {
        Role admin = roleService.findByName(Role.ADMIN);

        user.getRoles().remove(admin);
        userService.edit(user);
    }

    public void deactivate(User user){
        userService.deactivate(user);
    }

    public void activate(User user){
        userService.activate(user);
    }

}

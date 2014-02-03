package cz.cvut.fel.jee.security;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Named(value = "authenticator")
@Stateful
@SessionScoped
public class Authenticator implements Serializable {

    private static final long serialVersionUID = 2072170216118113636L;

    @SuppressWarnings("CdiUnproxyableBeanTypesInspection")
    @Inject
    private transient UserService userService;

    private String password;

    private String email;

    private User currentUser;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }
    
    public boolean login(HttpServletRequest request){
        try {
            request.login(email, password);
            User user = userService.findByEmail(email);
            currentUser = user;
            password = null;
            email = null;
            request.getSession().setAttribute("userID", currentUser.getId());
        } catch (ServletException e) {
            return false;
        }
        return true;
    }
    
    public boolean logout(HttpServletRequest request){
        try {
            request.logout();
            currentUser = null;
            request.getSession().removeAttribute("userID");
        } catch (ServletException e) {
        }
        return true;
    }

    @Produces
    @Named("currentUser")
    @CurrentLoggedUser
    public User getCurrentUser() {
        return currentUser;
    }

}
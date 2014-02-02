package cz.cvut.fel.jee.security;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

    @Inject
    private FacesContext facesContext;

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

    public String login() {
        HttpServletRequest request = (HttpServletRequest) facesContext
                .getExternalContext().getRequest();
        try {
            request.login(email, password);
            User user = userService.findByEmail(email);
            currentUser = user;
            password = null;
            email = null;
        } catch (ServletException e) {
            facesContext.addMessage(null,new FacesMessage("Bad login."));
            return "";
        }

        return "index";
    }

    public String logout(){
        HttpServletRequest request = (HttpServletRequest) facesContext
                .getExternalContext().getRequest();
        try {
            request.logout();
            currentUser = null;
        } catch (ServletException e) {
        }
        return "index";
    }

    @Produces
    @Named("currentUser")
    @CurrentLoggedUser
    public User getCurrentUser() {
        return currentUser;
    }

}
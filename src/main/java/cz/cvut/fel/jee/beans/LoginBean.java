/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.security.Authenticator;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author saljack
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {
    
    @Inject
    private FacesContext facesContext;
    
    @Inject
    private Authenticator authenticator;
    
    private String password;

    private String email;
    
    public String login(){
        authenticator.setEmail(email);
        authenticator.setPassword(password);
        HttpServletRequest request = (HttpServletRequest) facesContext
                .getExternalContext().getRequest();
        if(!authenticator.login(request)){
            facesContext.addMessage(null,new FacesMessage("Bad login."));
            return "";
        }
        return "index";
    }
    
    public String logout(){
        HttpServletRequest request = (HttpServletRequest) facesContext
                .getExternalContext().getRequest();
        authenticator.logout(request);
        return "index";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    
}

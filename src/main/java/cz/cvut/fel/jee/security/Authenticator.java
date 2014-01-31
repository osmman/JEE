package cz.cvut.fel.jee.security;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;

import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.resource.spi.AuthenticationMechanism;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named(value = "authenticator")
@Stateful
@SessionScoped
public class Authenticator implements Serializable {

	@Inject
	private transient Logger logger;

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

	public void login() {
		User user = userService.findByEmail(email);
		if (user.getPassword().equals(password)) {
			currentUser = user;
			password = null;
			email = null;
		} else {
			// @todo vyjimka, messages
		}

	}

	@Produces
	@Named("currentUser")
	@CurrentLoggedUser
	public User getCurrentUser() {
		return currentUser;
	}

}

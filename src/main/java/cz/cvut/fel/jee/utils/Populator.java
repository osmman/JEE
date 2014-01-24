package cz.cvut.fel.jee.utils;

import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;

@Stateless
@Transactional
@Named(value = "populator")
public class Populator {
	
    @Inject
    private UserService userService;
	
	public void populate() throws NoSuchAlgorithmException{
		User user = new User();
		user.setEmail("trnkamich@gmail.com");
		user.setPassword("heslo1");
        userService.create(user);
		user = new User();
		user.setEmail("turek@gmail.com");
		user.setPassword("heslo2");
        userService.create(user);
        user = new User();
		user.setEmail("vlasta@gmail.com");
		user.setPassword("heslo3");
        userService.create(user);
        user = new User();
		user.setEmail("tompol@gmail.com");
		user.setPassword("heslo4");
        userService.create(user);
	}
}

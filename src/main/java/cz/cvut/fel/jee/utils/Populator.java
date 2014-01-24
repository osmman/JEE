package cz.cvut.fel.jee.utils;

import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import cz.cvut.fel.jee.model.User;

@Stateless
@Transactional
@Named(value = "populator")
public class Populator {
	
	@Inject
	EntityManager em;
	
	public void populate() throws NoSuchAlgorithmException{
		User user = new User();
		user.setEmail("trnkamich@gmail.com");
		user.setPassword("heslo1");
		em.persist(user);
		user = new User();
		user.setEmail("turek@gmail.com");
		user.setPassword("heslo2");
		em.persist(user);
		user.setEmail("vlasta@gmail.com");
		user.setPassword("heslo3");
		em.persist(user);
		user.setEmail("tompol@gmail.com");
		user.setPassword("heslo4");
		em.persist(user);
	}
}

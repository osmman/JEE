package cz.cvut.fel.jee.trash;

import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;

@Singleton
@Transactional
@Startup
public class Populator {

    @Inject
    private UserService userService;

    @PostConstruct
    public void populate() throws NoSuchAlgorithmException {
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

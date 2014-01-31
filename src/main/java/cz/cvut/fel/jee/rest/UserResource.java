package cz.cvut.fel.jee.rest;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 * Created by Tomáš on 25.1.14.
 */
@Path("user")
@Stateless
public class UserResource extends AbstractResource<User>
{

    @Inject
    protected UserService facade;


    @Override
    protected AbstractFacade<User> getFacade()
    {
        return facade;
    }
}

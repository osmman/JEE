package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by Tomáš on 24.1.14.
 */
@Stateless
public class UserEjb extends AbstractFacade<User>
{

    @Inject
    private EntityManager em;

    public UserEjb()
    {
        super(User.class);
    }


    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }
}

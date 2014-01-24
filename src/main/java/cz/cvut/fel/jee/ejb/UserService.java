package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by Tomáš on 24.1.14.
 */
@Stateless
public class UserService extends AbstractFacade<User>
{

    @Inject
    private EntityManager em;

    public UserService()
    {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public User findByEmail(String email){
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email);
        User user = query.getSingleResult();
        return user;
    }
}

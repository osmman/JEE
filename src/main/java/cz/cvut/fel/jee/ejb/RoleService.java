package cz.cvut.fel.jee.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cz.cvut.fel.jee.model.Role;


@Stateless
public class RoleService  extends AbstractFacade<Role>{

	@Inject
    private EntityManager em;

    public RoleService()
    {
        super(Role.class);
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

	
}

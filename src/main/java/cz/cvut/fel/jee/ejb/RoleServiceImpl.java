package cz.cvut.fel.jee.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cz.cvut.fel.jee.model.Role;


@Stateless
public class RoleServiceImpl  extends AbstractFacadeImpl<Role> implements RoleService{

	@Inject
    private EntityManager em;

    public RoleServiceImpl()
    {
        super(Role.class);
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }


    @Override
    public Role findByName(String name) {
        TypedQuery<Role> query = em.createNamedQuery("Role.findByName", Role.class).setParameter("name", name);
        return query.getSingleResult();
    }
}
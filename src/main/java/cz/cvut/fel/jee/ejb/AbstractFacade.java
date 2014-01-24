package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.utils.QueryBuilder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.List;

/**
 * Created by Tomáš on 24.1.14.
 */
public abstract class AbstractFacade<T>
{

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity)
    {
        getEntityManager().persist(entity);
    }

    public void edit(T entity)
    {
        getEntityManager().merge(entity);
    }

    public void remove(T entity)
    {
        getEntityManager().remove(getEntityManager().contains(entity) ? entity : getEntityManager().merge(entity));
    }

    public T find(Object id)
    {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll(Integer base, Integer offset)
    {
        QueryBuilder<T> builder = new QueryBuilder<T>(entityClass,
                getEntityManager());
        builder.setBase(base);
        builder.setOffset(offset);
        Query q = builder.build();
        return q.getResultList();
    }

    public boolean contains(T entity)
    {
        return this.getEntityManager().contains(entity);
    }
}

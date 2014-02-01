package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.EntityObject;
import cz.cvut.fel.jee.utils.QueryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Tomáš on 24.1.14.
 */
public abstract class AbstractFacadeImpl<T extends EntityObject> implements AbstractFacade<T>
{

    private Class<T> entityClass;

    public AbstractFacadeImpl(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Override
    public void create(T entity)
    {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(T entity)
    {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(T entity)
    {
        getEntityManager().remove(getEntityManager().contains(entity) ? entity : getEntityManager().merge(entity));
    }

    @Override
    public T find(Object id)
    {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public List<T> findAll(){
        QueryBuilder<T> builder = new QueryBuilder<T>(entityClass,
                getEntityManager());
        Query q = builder.build();
        return q.getResultList();
    }

    @Override
    public List<T> findAll(Integer base, Integer offset)
    {
        QueryBuilder<T> builder = new QueryBuilder<T>(entityClass,
                getEntityManager());
        builder.setBase(base);
        builder.setOffset(offset);
        Query q = builder.build();
        return q.getResultList();
    }

    @Override
    public boolean contains(T entity)
    {
        return this.getEntityManager().contains(entity);
    }
}

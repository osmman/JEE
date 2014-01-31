package cz.cvut.fel.jee.utils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by Tomáš on 24.1.14.
 */
public class QueryBuilder<T>
{
    private Class<T> entityClass;

    private EntityManager em;

    private String orderBy;

    private String orderMethod;

    private Integer base;

    private Integer offset;

    private CriteriaQuery<T> cq;

    private CriteriaBuilder cb;

    private Root<T> root;

    public QueryBuilder(Class<T> entityClass, EntityManager em)
    {
        this.entityClass = entityClass;
        this.em = em;
    }

    public void setOrder(String orderBy, String method)
    {
        this.orderBy = orderBy;
        this.orderMethod = method;
    }

    public void setBase(Integer base)
    {
        this.base = base;
    }

    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }

    public Query build()
    {
        cb = em.getCriteriaBuilder();
        cq = cb.createQuery(entityClass);
        root = cq.from(entityClass);
        cq.select(root);

        order();
        Query query = em.createQuery(cq);

        return limit(query);
    }

    private void order()
    {

        if (orderBy != null) {
            if (orderMethod.endsWith("desc")) {
                cq.orderBy(cb.desc(root.get(orderBy)));
            } else {
                cq.orderBy(cb.asc(root.get(orderBy)));
            }
        }

    }

    private Query limit(Query q)
    {
        if (base != null) {
            q.setMaxResults(base);
        }
        if (offset != null) {
            q.setFirstResult(offset);
        }
        return q;
    }

}

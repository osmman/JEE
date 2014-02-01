package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.EntityObject;
import java.util.List;

/**
 *
 * @author saljack
 */
public interface AbstractFacade<T extends EntityObject> {

    boolean contains(T entity);

    void create(T entity);

    void edit(T entity);

    T find(Object id);

    List<T> findAll();

    List<T> findAll(Integer base, Integer offset);

    void remove(T entity);
    
}

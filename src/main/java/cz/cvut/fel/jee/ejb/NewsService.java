package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.News;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by Tomáš on 29.1.14.
 */
public class NewsService extends AbstractFacade<News> {

    @Inject
    private EntityManager em;

    public NewsService() {
        super(News.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}

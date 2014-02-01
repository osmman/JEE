package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.News;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by Tomáš on 29.1.14.
 */
public class NewsServiceImpl extends AbstractFacadeImpl<News>  implements NewsService{

    @Inject
    private EntityManager em;

    public NewsServiceImpl() {
        super(News.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}


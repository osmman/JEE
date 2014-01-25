package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by Tomáš on 25.1.14.
 */
@Stateless
public class VideoService extends AbstractFacade<Video>
{
    @Inject
    private EntityManager em;

    public VideoService()
    {
        super(Video.class);
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }
}

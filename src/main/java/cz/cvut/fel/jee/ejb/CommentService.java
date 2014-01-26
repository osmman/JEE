package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.Comment;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by Tomáš on 26.1.14.
 */
@Stateless
public class CommentService extends AbstractFacade<Comment>
{
    @Inject
    private EntityManager em;

    public CommentService()
    {
        super(Comment.class);
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }
}

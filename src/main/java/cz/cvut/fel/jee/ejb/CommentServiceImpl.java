package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.endpoints.websocket.CommentMessage;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.Role;
import cz.cvut.fel.jee.model.Video;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

/**
 * Created by Tomáš on 26.1.14.
 */
@Stateless
public class CommentServiceImpl extends AbstractFacadeImpl<Comment> implements CommentService {

    @Inject
    private EntityManager em;

    @Inject
    private UserService userService;

    @Inject
    private VideoService videoService;

    public CommentServiceImpl() {
        super(Comment.class);
    }

    @Override
    @PermitAll
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @PermitAll
    public List<Comment> findByVideo(Video video) {
        return em.createNamedQuery("Comment.findByVideo").setParameter("video", video).getResultList();
    }

    @Override
    public Comment addCommentMessage(CommentMessage comment) {
        Comment entity = new Comment();
        entity.setAuthor(userService.find(comment.getAuthorId()));
        entity.setText(comment.getText());
        entity.setDatetime(comment.getDate());
        entity.setVideo(videoService.find(comment.getVideoId()));
        super.create(entity);
        return entity;
    }
}

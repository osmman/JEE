package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.endpoints.websocket.CommentMessage;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.Video;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Tomáš on 26.1.14.
 */
@Stateless
public class CommentService extends AbstractFacade<Comment> {

    @Inject
    private EntityManager em;

    @Inject
    private UserService userService;

    @Inject
    private VideoService videoService;

    public CommentService() {
        super(Comment.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Comment> findByVideo(Video video) {
        return em.createNamedQuery("Comment.findByVideo").setParameter("video", video).getResultList();
    }

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

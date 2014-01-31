package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import java.util.Set;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by Tomáš on 24.1.14.
 */
@Stateless
public class UserService extends AbstractFacade<User> {

    @Inject
    private EntityManager em;

    public UserService() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public User findByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email);
        User user = query.getSingleResult();
        return user;
    }

    public Set<Comment> getAllComments(Long id) {
        User user = find(id);
        Set<Comment> comments = user.getComments();
        //hack to load collection
        comments.size();
        return comments;
    }

    public Set<Video> getAllVideos(Long id) {
        User user = find(id);
        Set<Video> videos = user.getVideos();
        //hack to load collection
        videos.size();
        return videos;
    }
}

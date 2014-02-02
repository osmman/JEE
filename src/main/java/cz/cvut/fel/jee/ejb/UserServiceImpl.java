package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;

import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by Tomáš on 24.1.14.
 */
@Stateless
public class UserServiceImpl extends AbstractFacadeImpl<User> implements UserService {

    @Inject
    private EntityManager em;

    public UserServiceImpl() {
        super(User.class);
    }

    @Override
    @PermitAll
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @PermitAll
    public User findByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email);
        User user = query.getSingleResult();
        return user;
    }

    @Override
    @PermitAll
    public Set<Comment> getAllComments(Long id) {
        User user = find(id);
        Set<Comment> comments = user.getComments();
        //hack to load collection
        comments.size();
        return comments;
    }

    @Override
    @PermitAll
    public Set<Video> getAllVideos(Long id) {
        User user = find(id);
        Set<Video> videos = user.getVideos();
        //hack to load collection
        videos.size();
        return videos;
    }

    @Override
    @RolesAllowed({"admin"})
    public void activate(User user) {
        user.setActivated(true);
        edit(user);
    }

    @Override
    @RolesAllowed({"admin"})
    public void deactivate(User user) {
        user.setActivated(false);
        edit(user);
    }
}

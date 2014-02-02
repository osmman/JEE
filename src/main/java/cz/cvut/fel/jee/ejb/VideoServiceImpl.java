package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.Role;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;

import org.infinispan.io.GridFilesystem;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Tomáš on 25.1.14.
 */
@Stateless
public class VideoServiceImpl extends AbstractFacadeImpl<Video> implements VideoService {

    @Inject
    @VideoFilesystem
    private GridFilesystem fileSystem;

    private static final String UPLOADED_PATH = "/video/uploaded";

    public static final String PUBLISHED_PATH = "/video/published";

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public VideoServiceImpl() {
        super(Video.class);
    }

    @PostConstruct
    @PermitAll
    private void init() {
        createDirectory();
    }

    @Inject
    private VideoConversionProducerService vcs;

    /**
     *
     * @param entity Entity (will be set path, mimetype(from Part video))
     * @param video Part for uploaded file
     */
    @Override
    @RolesAllowed(Role.USER)
    public void create(Video entity, Part video) {
        super.create(entity);
        try {
            log.warning("Video submited name: " + video.getSubmittedFileName());
            entity.setThumbs(new LinkedList<String>());

            String uploaded = UPLOADED_PATH + "/" + entity.getId() + "_" + video.getSubmittedFileName();
            String output = PUBLISHED_PATH + "/"+ entity.getId() +".ogv ";


            InputStream is = video.getInputStream();
            OutputStream os = fileSystem.getOutput(uploaded);
            byte[] buffer = new byte[20480];
            int len;
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, len);
            }
            is.close();
            os.close();
            log.info("File id:" + entity.getId() + " name: " + entity.getName() + " is writed!");

            vcs.sendMessage(entity, uploaded, output);
            entity.setPath(output);

            super.edit(entity);


        } catch (IOException e) {
            log.warning(e.toString());
            throw new EJBException(e);
        }
    }

    @RolesAllowed(Role.USER)
    public void create(Video entity, InputStream is, String mimetype) {
        super.create(entity);
        try {
            entity.setPath(UPLOADED_PATH + entity.getId() + getExtension(entity.getName()));
            entity.setMimetype(mimetype);
            OutputStream os = fileSystem.getOutput(entity.getPath());
            byte[] buffer = new byte[20480];
            int len;
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, len);
            }
            is.close();
            os.close();
            super.edit(entity);
        } catch (IOException e) {
            log.warning(e.toString());
            throw new EJBException(e);
        }
    }

    @Override
    @PermitAll
    public File getVideoFile(Long id) {
        Video video = find(id);
        if (video == null) {
            return null;
        }
        return fileSystem.getFile(video.getPath());
    }

    @PermitAll
    private void createDirectory() {
        File dir = fileSystem.getFile(UPLOADED_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dir = fileSystem.getFile(PUBLISHED_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @PermitAll
    private static String getExtension(String filename) {
        int dot = filename.lastIndexOf(".");
        if (dot == -1) {
            return "";
        }
        return filename.substring(dot + 1);
    }

    @Override
    @PermitAll
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @PermitAll
    public List<Video> findByAuthor(User author) {
        return em.createNamedQuery("Video.findByAuthor").setParameter("author", author).getResultList();
    }

    @PermitAll
    public List<Video> findAllPublished(){
        return em.createNamedQuery("Video.findAllPublished").getResultList();
    }

    @Override
    @PermitAll
    public int numberOfVideos() {
        return ((Number) em.createNamedQuery("Video.count").getSingleResult()).intValue();
    }

    @Override
    @PermitAll
    public List<Comment> getAllComments(Long id) {
        Video video = find(id);
        List<Comment> comments = video.getComments();
        //Hack to load comments
        comments.size();
        return comments;
    }
}

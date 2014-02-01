package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.model.Comment;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;
import org.infinispan.io.GridFilesystem;

/**
 * Created by Tomáš on 25.1.14.
 */
@Stateless
public class VideoServiceImpl extends AbstractFacadeImpl<Video> implements VideoService {

    @Inject
    @VideoFilesystem
    private GridFilesystem fileSystem;

    private static final String BASE_PATH = "/video/uploaded";

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public VideoServiceImpl() {
        super(Video.class);
    }

    @PostConstruct
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
    public void create(Video entity, Part video) {
        super.create(entity);
        try {
            log.warning("Video submited name: " + video.getSubmittedFileName());
            entity.setThumbs(new LinkedList<String>());
            entity.setPath(BASE_PATH + "/" + entity.getId() + "_" + video.getSubmittedFileName());
            entity.setMimetype("video/ogv");
            log.warning(entity.getMimetype());

            InputStream is = video.getInputStream();
            OutputStream os = fileSystem.getOutput(entity.getPath());
            byte[] buffer = new byte[20480];
            int len;
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, len);
            }
            is.close();
            os.close();
            log.info("File id:" + entity.getId() + " name: " + entity.getName() + " is writed!");

            String output = BASE_PATH + "/" + entity.getId() + entity.getName() + ".ogv ";

            vcs.sendMessage(entity, entity.getPath(), output);
            entity.setPath(output);

            super.edit(entity);

        } catch (IOException e) {
            log.warning(e.toString());
            throw new EJBException(e);
        }
    }

    @Override
    public void create(Video entity, InputStream is, String mimetype) {
        super.create(entity);
        try {
            entity.setPath(BASE_PATH + "/" + entity.getId() + "." + getExtension(entity.getName()));
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
    public File getVideoFile(Long id) {
        Video video = find(id);
        if (video == null) {
            return null;
        }
        return fileSystem.getFile(video.getPath());
    }

    private void createDirectory() {
        File dir = fileSystem.getFile(BASE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private static String getExtension(String filename) {
        int dot = filename.lastIndexOf(".");
        if (dot == -1) {
            return "";
        }
        return filename.substring(dot + 1);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Video> findByAuthor(User author) {
        return em.createNamedQuery("Video.findByAuthor").setParameter("author", author).getResultList();
    }

    @Override
    public int numberOfVideos() {
        return ((Number) em.createNamedQuery("Video.count").getSingleResult()).intValue();
    }

    @Override
    public List<Comment> getAllComments(Long id) {
        Video video = find(id);
        List<Comment> comments = video.getComments();
        //Hack to load comments
        comments.size();
        return comments;
    }
}

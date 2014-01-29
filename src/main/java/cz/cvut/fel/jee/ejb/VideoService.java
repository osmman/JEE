package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.model.Video;
import org.infinispan.io.GridFilesystem;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Tomáš on 25.1.14.
 */
@Stateless
public class VideoService extends AbstractFacade<Video> {

    @Inject
    @VideoFilesystem
    private GridFilesystem fileSystem;

    private static final String BASE_PATH = "/video/uploaded";

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    public VideoService() {
        super(Video.class);
    }

    @PostConstruct
    private void init() {
        createDirectory();
    }

    @Inject
    private VideoConversionProducerService vcs;

    public void create(Video entity, Part video) {
        super.create(entity);
        try {
            log.warning("Video submited name: " + video.getSubmittedFileName());
            entity.setPath(BASE_PATH + "/" + entity.getId() + "_" + video.getSubmittedFileName());
            entity.setMimetype(video.getContentType());
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
            super.edit(entity);
            log.info("File id:" + entity.getId() + " name: " + entity.getName() + " is writed!");

            String output = BASE_PATH + "/"+ entity.getId() + entity.getName()+".mp4";

            vcs.sendMessage(entity, entity.getPath(), output);


        } catch (IOException e) {
            log.warning(e.toString());
            throw new EJBException(e);
        }
    }

    public File getVideoFile(Long id){
        Video video = find(id);
        if(video == null) return null;
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

    public List<Video> findByAuthor(User author) {
        return em.createNamedQuery("Video.findByAuthor").setParameter("author",author).getResultList();
    }
}

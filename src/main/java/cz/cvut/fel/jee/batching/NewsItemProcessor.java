package cz.cvut.fel.jee.batching;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.model.Video;
import cz.cvut.fel.jee.utils.VideoImageGrabber;
import org.infinispan.io.GridFilesystem;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 29.1.14
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
@Dependent
@Named
public class NewsItemProcessor implements ItemProcessor {

    @Inject
    private Logger log;

    @Inject
    @VideoFilesystem
    private GridFilesystem fileSystem;

    private static final String BASE_PATH = "/video/thumbs/";

    private static final int IMAGE_COUNT = 5;

    @Override
    public Object processItem(Object o) throws Exception {
        Video video = (Video) o;

        File working_video = null;

        log.info(String.format("Processing video: %s", video.getName()));
        try {
            log.info("Creating temp file.");
            working_video = File.createTempFile("input", ".tmp");
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Copy data from grid do temp.");
        Files.copy(fileSystem.getInput(video.getPath()), Paths.get(working_video.getPath()), REPLACE_EXISTING);

        String outputImagePrefix = video.getName();

        log.info(String.format("Start grabing images from video: %s.", video.getName()));
        VideoImageGrabber vg = new VideoImageGrabber(working_video.getPath(), outputImagePrefix, IMAGE_COUNT);
        List<File> images = vg.gramImages();
        log.info("Grabing images done!");

        log.info("Saving images.");
        createFolder();

        File working_output;
        OutputStream outputStream;
        String image_path;
        int i = 0;
        video.setThumbs(new LinkedList<String>());
        for (File file : images) {
            String imagePath = BASE_PATH + video.getId() + "_"  + video.getName() + "_" + i + ".png";
            log.info(String.format("Processing saving image: %s", video.getId() + "_"  + video.getName() + "_" + i + ".png"));
            outputStream = fileSystem.getOutput(imagePath);
            Files.copy(Paths.get(file.getPath()), outputStream);
            video.getThumbs().add(imagePath);
            file.deleteOnExit();
        }
        log.info("Saving images done!");

        return video;
    }

    private void createFolder() {
        File dir = fileSystem.getFile(BASE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }


}

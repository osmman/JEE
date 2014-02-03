package cz.cvut.fel.jee.message;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.beans.NewsGeneratorTimer;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Video;
import cz.cvut.fel.jee.utils.VideoConverter;
import it.sauronsoftware.jave.EncoderException;
import org.infinispan.io.GridFilesystem;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 27.1.14
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
@MessageDriven(name = "VideoConvertesionQueue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/jms/queue/VideoConvertesionQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class VideoConverterConsumer implements MessageListener{

    @Inject
    private Logger log;

    @Inject
    @VideoFilesystem
    private GridFilesystem fileSystem;

    @Inject
    private VideoService videoService;

    @Inject
    private NewsGeneratorTimer newsGeneratorTimer;

    @Override
    public void onMessage(Message message) {
        log.info("Message received.");
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage msg = (ObjectMessage) message;
                VideoMessageWraper object = (VideoMessageWraper) msg.getObject();

                String inputPath = object.getInput();
                String outputPath = object.getOutput();
                Video entity = videoService.find(object.getVideoId());


                File working_input = null;
                File working_output = null;

                try {
                    log.info("Creating temp files.");
                    working_input = File.createTempFile("input", ".tmp");
                    working_output = File.createTempFile("output", ".tmp");
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                log.info("Copy data from gridu do temp.");
                Files.copy(fileSystem.getInput(inputPath), Paths.get(working_input.getPath()), REPLACE_EXISTING);

                log.info("Conversion start.");
                VideoConverter converter = new VideoConverter();
                converter.convertVideo(working_input, working_output);
                log.info("Conversion Done.");


                log.info("Copy from temp to grid.");
                Files.copy(Paths.get(working_output.getPath()), fileSystem.getOutput(outputPath));

                log.info("Deleting temp files.");
                working_input.deleteOnExit();
                working_output.deleteOnExit();

                log.info("Persist video entity.");
                entity.setMimetype("video/ogv");
                entity.setPublished(true);
                videoService.edit(entity);

                log.info("Deleting input files.");
                File input = fileSystem.getFile(inputPath);
                input.delete();

                log.info("Message done!");

                log.info("Creating batching");

                newsGeneratorTimer.add(entity);

            }
        } catch (JMSException e) {
            log.warning("Cant convert message!");
        } catch (EncoderException e) {
            log.warning("Encoder problems!");
        } catch (IOException e) {
            log.warning("Cant manipulate with files!");
        }
    }
}


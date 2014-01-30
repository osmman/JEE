package cz.cvut.fel.jee.ejb;

import cz.cvut.fel.jee.beans.VideoStack;
import cz.cvut.fel.jee.message.VideoMessageWraper;
import cz.cvut.fel.jee.model.Video;

import javax.annotation.Resource;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 29.1.14
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
@RequestScoped
public class VideoConversionProducerService {

    @Inject
    private Logger log;

    @Inject
    private VideoStack videoStack;

    @Inject
    private EntityManager em;

    @Resource(mappedName = "java:jboss/jms/queue/VideoConvertesionQueue")
    private Queue queue;

    @Inject
    private JMSContext context;

    public void sendMessage(Video video, String input, String output){
        log.info(String.format("Creating message for input: %s, a output %s", input, output));
        VideoMessageWraper message_wrapper = new VideoMessageWraper();

        message_wrapper.setInput(input);
        message_wrapper.setOutput(output);

        context.createProducer().send(queue, message_wrapper);

        videoStack.push(video);

        log.info("Message created");
    }
}

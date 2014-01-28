package cz.cvut.fel.jee.message;

import cz.cvut.fel.jee.utils.VideoConverter;
import it.sauronsoftware.jave.EncoderException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 27.1.14
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
@MessageDriven(name = "VideoConvertesionQueue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/VideoConvertesionQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class VideoConverterConsumer implements MessageListener{

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage msg = (ObjectMessage) message;
                VideoMessageWraper object = (VideoMessageWraper) msg.getObject();

                File input = object.getInput();
                File output = object.getOutput();

                VideoConverter converter = new VideoConverter();
                converter.convertVideo(input, output);

                if (object.getCallback() != null) {
                    object.getCallback().call();
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }
}


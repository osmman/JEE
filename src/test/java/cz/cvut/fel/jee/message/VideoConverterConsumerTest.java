package cz.cvut.fel.jee.message;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import java.io.File;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 27.1.14
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class VideoConverterConsumerTest {

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:jboss/queue/VideoConvertesionQueue")
    private Queue queue;

    public static final String RESOURCES = "src/test/resources";

    @Before
    public void beforeTest(){
        File output1 = new File(RESOURCES + "/video/output1.mp4");
        output1.delete();
        File output2 = new File(RESOURCES + "/video/output2.mp4");
        output2.delete();
        File output3 = new File(RESOURCES + "/video/output3.mp4");
        output3.delete();

    }

    @Test
    public void consumeTest() {
        VideoMessageWraper vm1 = new VideoMessageWraper();
        VideoMessageWraper vm2 = new VideoMessageWraper();
        VideoMessageWraper vm3 = new VideoMessageWraper();

        File input = new File(RESOURCES + "/video/animace.wmv");
        File output1 = new File(RESOURCES + "/video/output1.mp4");
        File output2 = new File(RESOURCES + "/video/output2.mp4");
        File output3 = new File(RESOURCES + "/video/output3.mp4");

        vm1.setInput(input);
        vm1.setOutput(output1);

        vm2.setInput(input);
        vm2.setOutput(output2);

        vm3.setInput(input);
        vm3.setOutput(output3);

        context.createProducer().send((Destination) queue, vm1);
        context.createProducer().send((Destination) queue, vm2);
        context.createProducer().send((Destination) queue, vm3);

        try {
            wait(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Assert.assertTrue(output1.length() > 0);
        Assert.assertTrue(output2.length() > 0);
        Assert.assertTrue(output3.length() > 0);

    }
}

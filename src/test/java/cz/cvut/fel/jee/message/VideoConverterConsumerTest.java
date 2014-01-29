package cz.cvut.fel.jee.message;

import cz.cvut.fel.jee.ejb.AbstractFacade;
import cz.cvut.fel.jee.ejb.UserService;
import cz.cvut.fel.jee.model.User;
import cz.cvut.fel.jee.utils.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 27.1.14
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Arquillian.class)
public class VideoConverterConsumerTest {

    public static final String WEBAPP_SRC = "src/main/webapp";

    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(Resources.class.getPackage())
                .addPackage(User.class.getPackage())
                .addClasses(VideoMessageWraper.class,IMessageCallback.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("test-ds.xml")
                .addClasses(UserService.class, AbstractFacade.class);
    }

    @Inject
    private JMSContext context;

    @Resource(mappedName = "/queue/VideoConvertesionQueue")
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

        Assert.assertNotNull(context);

        context.createProducer().send((Destination) queue, vm1);
        context.createProducer().send((Destination) queue, vm2);
        context.createProducer().send((Destination) queue, vm3);

//        Assert.assertTrue(output1.length() > 0);
//        Assert.assertTrue(output2.length() > 0);
//        Assert.assertTrue(output3.length() > 0);

    }
}

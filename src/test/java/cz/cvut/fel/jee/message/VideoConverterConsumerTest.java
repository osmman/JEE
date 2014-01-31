package cz.cvut.fel.jee.message;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import org.infinispan.io.GridFilesystem;
import org.junit.Assert;

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
//@RunWith(Arquillian.class)
public class VideoConverterConsumerTest {

    public static final String WEBAPP_SRC = "src/main/webapp";

    @Inject
    @VideoFilesystem
    private GridFilesystem fileSystem;

    public VideoConverterConsumerTest() {
    }

//    @Deployment
//    public static WebArchive deploy() {
//        return ShrinkWrap.create(WebArchive.class)
//                .addPackage(Resources.class.getPackage())
//                .addPackage(User.class.getPackage())
//                .addClasses(VideoMessageWraper.class, GridFilesystem.class, VideoFilesystem.class)
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//                .addAsResource("video/animace.wmv", "video/animace.wmv")
//                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
//                .addAsWebInfResource("test-ds.xml")
//                .addClasses(UserService.class, AbstractFacade.class);
//    }

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:jboss/jms/queue/VideoConvertesionQueue")
    private Queue queue;

    public static String RESOURCES = "";


//    @Before
    public void beforeTest(){
        System.out.println(RESOURCES);
        File output1 = new File(RESOURCES + "video/output1.mp4");
        output1.delete();
        File output2 = new File(RESOURCES + "video/output2.mp4");
        output2.delete();
        File output3 = new File(RESOURCES + "video/output3.mp4");
        output3.delete();

    }

    //@Test
    public void consumeTest() {

        VideoMessageWraper vm1 = new VideoMessageWraper();
        VideoMessageWraper vm2 = new VideoMessageWraper();
        VideoMessageWraper vm3 = new VideoMessageWraper();

        String input =RESOURCES + "video/animace.wmv";
        String output1 = RESOURCES + "video/output1.mp4";
        String output2 = RESOURCES + "video/output2.mp4";
        String output3 = RESOURCES + "video/output3.mp4";




        Assert.assertTrue(new File(output1).length() == 0);
        Assert.assertTrue(new File(output2).length() == 0);
        Assert.assertTrue(new File(output3).length() == 0);

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

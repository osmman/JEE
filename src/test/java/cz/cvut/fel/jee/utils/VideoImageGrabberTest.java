package cz.cvut.fel.jee.utils;

import it.sauronsoftware.jave.EncoderException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 29.1.14
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
public class VideoImageGrabberTest {

    public static final String RESOURCES = "src/test/resources";

    @Test
    public void testConversion() throws FileNotFoundException, EncoderException {

        String inputPath = RESOURCES + "/video/animace.wmv";
        String outputPrefixPath = RESOURCES + "/video/animace";

        VideoImageGrabber vg = new VideoImageGrabber(inputPath, outputPrefixPath, 3);
        vg.gramImages();

        File f1 = new File(outputPrefixPath+"_0.png");
        File f2 = new File(outputPrefixPath+"_1.png");
        File f3 = new File(outputPrefixPath+"_2.png");

        Assert.assertTrue(f1.length() > 0);
        Assert.assertTrue(f2.length() > 0);
        Assert.assertTrue(f3.length() > 0);

        f1.deleteOnExit();
        f2.deleteOnExit();
        f3.deleteOnExit();
    }
}

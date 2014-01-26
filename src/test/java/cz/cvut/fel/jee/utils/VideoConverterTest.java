package cz.cvut.fel.jee.utils;

import it.sauronsoftware.jave.EncoderException;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Tomáš on 25.1.14.
 */
@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VideoConverterTest
{
    public static final String RESOURCES = "src/test/resources";

    @Test
    public void testConversion() throws FileNotFoundException, EncoderException {
        File input = new File(RESOURCES + "/video/animace.wmv");
        File output = new File(RESOURCES + "/video/output.avi");
        VideoConverter converter = new VideoConverter();
        converter.convertVideo(input, output);
    }
}

package cz.cvut.fel.jee.utils;

import it.sauronsoftware.jave.*;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 26.1.14
 * Time: 20:37
 * To change this template use File | Settings | File Templates.
 */
public class VideoConverter {

    /**
     * Method converting video to mpeg4 / DIVX.
     * @param input input file
     * @param output output file
     * @throws EncoderException
     */
     public void convertVideo(File input, File output) throws EncoderException {
         AudioAttributes audio = new AudioAttributes();
         audio.setCodec("vorbis");
         audio.setBitRate(new Integer(128000));
         audio.setSamplingRate(new Integer(44100));
         audio.setChannels(new Integer(2));
         VideoAttributes video = new VideoAttributes();
         video.setCodec("libtheora");
         video.setTag("theora");
         video.setBitRate(new Integer(160000));
         video.setFrameRate(new Integer(15));
         EncodingAttributes attrs = new EncodingAttributes();
         attrs.setFormat("ogg");
         attrs.setAudioAttributes(audio);
         attrs.setVideoAttributes(video);
         Encoder encoder = new Encoder();
         encoder.encode(input, output, attrs);
    }
}

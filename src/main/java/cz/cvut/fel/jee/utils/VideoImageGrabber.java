package cz.cvut.fel.jee.utils;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.Global;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 29.1.14
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */
public class VideoImageGrabber {

    private IMediaReader mediaReader;

    private ImageSnapListener imageSnapListener;

    public VideoImageGrabber(String videoPath, String imagesPrefix, int imageCount) {

        Encoder encoder = new Encoder();
        MultimediaInfo mi = null;
        try {
            mi = encoder.getInfo(new File(videoPath));
        } catch (EncoderException e) {
            e.printStackTrace();
        }


        IMediaReader mediaReader = ToolFactory.makeReader(videoPath);

        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);

        imageSnapListener = new ImageSnapListener();
        imageSnapListener.setImagesPrefix(imagesPrefix);
        imageSnapListener.setSECONDS_BETWEEN_FRAME(mi.getDuration() / imageCount);
        imageSnapListener.setMICRO_SECONDS_BETWEEN_FRAMES((long) (1000 * imageSnapListener.getSECONDS_BETWEEN_FRAME()));
        imageSnapListener.setOutputFiles(new LinkedList<File>());

        mediaReader.addListener(imageSnapListener);

        this.mediaReader = mediaReader;
    }

    public List<File> gramImages() {
        while (mediaReader.readPacket() == null) ;
        return imageSnapListener.getOutputFiles();
    }


    private static class ImageSnapListener extends MediaListenerAdapter {

        private String imagesPrefix;

        private Integer number = 0;

        private double SECONDS_BETWEEN_FRAME;

        private long mLastPtsWrite = -1;

        private int mVideoStreamIndex = -1;

        private long MICRO_SECONDS_BETWEEN_FRAMES;

        List<File> outputFiles;


        public void onVideoPicture(IVideoPictureEvent event) {
            if (event.getStreamIndex() != mVideoStreamIndex) {
                // if the selected video stream id is not yet set, go ahead an
                // select this lucky video stream
                if (mVideoStreamIndex == -1)
                mVideoStreamIndex = event.getStreamIndex();
                // no need to show frames from this video stream
                else
                return;
            }

            // if uninitialized, back date mLastPtsWrite to get the very first frame
            if (mLastPtsWrite == Global.NO_PTS)
            mLastPtsWrite = event.getTimeStamp() - MICRO_SECONDS_BETWEEN_FRAMES;
            // if it's time to write the next frame
            if (event.getTimeStamp() - mLastPtsWrite >=
            MICRO_SECONDS_BETWEEN_FRAMES-1) {
                String outputFilename = dumpImageToFile(event.getImage());
                // indicate file written
                double seconds = ((double) event.getTimeStamp()) /
                Global.DEFAULT_PTS_PER_SECOND;
                System.out.printf(
                        "at elapsed time of %6.3f seconds wrote: %s\n",
                        seconds, outputFilename);

                // update last write time
                mLastPtsWrite += MICRO_SECONDS_BETWEEN_FRAMES;
            }
        }

        private String dumpImageToFile(BufferedImage image) {
            try {
                String outputFilename = imagesPrefix +
                "_" + number++ + ".png";
                File reference = new File(outputFilename);
                ImageIO.write(image, "png", reference);
                outputFiles.add(reference);
                return outputFilename;
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private String getImagesPrefix() {
            return imagesPrefix;
        }

        private void setImagesPrefix(String imagesPrefix) {
            this.imagesPrefix = imagesPrefix;
        }

        private Integer getNumber() {
            return number;
        }

        private void setNumber(Integer number) {
            this.number = number;
        }

        private double getSECONDS_BETWEEN_FRAME() {
            return SECONDS_BETWEEN_FRAME;
        }

        private void setSECONDS_BETWEEN_FRAME(double SECONDS_BETWEEN_FRAME) {
            this.SECONDS_BETWEEN_FRAME = SECONDS_BETWEEN_FRAME;
        }

        private int getmVideoStreamIndex() {
            return mVideoStreamIndex;
        }

        private void setmVideoStreamIndex(int mVideoStreamIndex) {
            this.mVideoStreamIndex = mVideoStreamIndex;
        }

        private long getMICRO_SECONDS_BETWEEN_FRAMES() {
            return MICRO_SECONDS_BETWEEN_FRAMES;
        }

        private void setMICRO_SECONDS_BETWEEN_FRAMES(long MICRO_SECONDS_BETWEEN_FRAMES) {
            this.MICRO_SECONDS_BETWEEN_FRAMES = MICRO_SECONDS_BETWEEN_FRAMES;
        }

        private List<File> getOutputFiles() {
            return outputFiles;
        }

        private void setOutputFiles(List<File> outputFiles) {
            this.outputFiles = outputFiles;
        }
    }


}

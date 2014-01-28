package cz.cvut.fel.jee.message;

import cz.cvut.fel.jee.model.Video;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 28.1.14
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
public class VideoMessageCallback implements IMessageCallback {

    private File input;

    private File output;

    private Video video;

    @Override
    public void call() {
        input.delete();
        video.setPath(output.getPath());
        video.setPublished(true);

        // TODO: Add to batch
    }

    public VideoMessageCallback(File input, File output, Video video){
        this.input = input;
        this.output = output;
        this.video = video;
    }

    public File getInput() {
        return input;
    }

    public void setInput(File input) {
        this.input = input;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}

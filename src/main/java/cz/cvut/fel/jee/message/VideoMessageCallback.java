package cz.cvut.fel.jee.message;

import cz.cvut.fel.jee.model.Video;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 28.1.14
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
public class VideoMessageCallback implements IMessageCallback {

    private String input;

    private String output;

    private Video video;

    @Override
    public void call() {
        video.setPath(output);
        video.setPublished(true);

    }

    public VideoMessageCallback(String input, String output, Video video){
        this.input = input;
        this.output = output;
        this.video = video;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}

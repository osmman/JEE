package cz.cvut.fel.jee.message;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 27.1.14
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class VideoMessageWraper implements Serializable {

    private Long videoId;

    private String input;

    private String output;

    public VideoMessageWraper() {
    }

    public VideoMessageWraper(Long videoId, String input, String output) {
        this.videoId = videoId;
        this.input = input;
        this.output = output;
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

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }
}

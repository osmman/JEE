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

    private String input;

    private String output;

    private IMessageCallback callback;

    public VideoMessageWraper() {
    }

    public VideoMessageWraper(String input, String output, IMessageCallback callback) {
        this.input = input;
        this.output = output;
        this.callback = callback;
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

    public IMessageCallback getCallback() {
        return callback;
    }

    public void setCallback(IMessageCallback callback) {
        this.callback = callback;
    }
}

package cz.cvut.fel.jee.message;

import java.io.File;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 27.1.14
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class VideoMessageWraper implements Serializable {

    private File input;

    private File output;

    public VideoMessageWraper() {
    }

    public VideoMessageWraper(File input, File output) {
        this.input = input;
        this.output = output;
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
}

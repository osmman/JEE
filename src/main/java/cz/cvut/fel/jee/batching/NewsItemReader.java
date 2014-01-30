package cz.cvut.fel.jee.batching;

import cz.cvut.fel.jee.beans.VideoStack;
import cz.cvut.fel.jee.model.Video;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 29.1.14
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
@Named
public class NewsItemReader extends AbstractItemReader {

    @Inject
    private Logger log;

    @Inject
    private VideoStack videoStack;

    private Video video;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        this.video = videoStack.pop();
    }

    @Override
    public Video readItem() throws Exception {
        if (this.video != null) {
            Video out = this.video;
            this.video = null;
            return out;
        }
        return null;
    }
}

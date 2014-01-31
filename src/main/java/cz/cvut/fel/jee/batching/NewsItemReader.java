package cz.cvut.fel.jee.batching;

import cz.cvut.fel.jee.model.Video;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 29.1.14
 * Time: 19:34
 * To change this template use File | Settings | File Templates.
 */
@Dependent
@Named
public class NewsItemReader extends AbstractItemReader {

    @Inject
    JobContext jobCtx;

    @Inject
    private Logger log;

    private List<Video> list;

    private Iterator<Video> iterator;

    @Override
    public void open(Serializable checkpoint) throws Exception {

        JobOperator jobOperator = BatchRuntime.getJobOperator();
        list = (List<Video>) jobOperator.getParameters(jobCtx.getExecutionId()).get("items");
        iterator = list.iterator();
    }

    @Override
    public Video readItem() throws Exception {

        if(iterator.hasNext()){
            return iterator.next();
        }
        return null;
    }
}

package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.model.Video;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 31.1.14
 * Time: 20:36
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Startup
public class NewsGeneratorTimer {

    @Inject
    Logger log;

    @Schedule(hour = "*", minute = "*/2")
    public void run() {
        log.info("News generator timer started.");

        JobOperator jo = BatchRuntime.getJobOperator();
        Properties properties = new Properties();
        long jid = jo.start("newsJob", properties);
    }

    private Queue<Video> queue = new LinkedList<Video>();

    public void add(Video video) {
        log.info("Push item to video stack");
        queue.add(video);
    }

    public Video peek() {
        log.info("Pop item from video stack.");
        return queue.peek();
    }

    public List<Video> getAll() {
        List<Video> list = new LinkedList();
        list.addAll(queue);

        queue.clear();
        return list;
    }
}

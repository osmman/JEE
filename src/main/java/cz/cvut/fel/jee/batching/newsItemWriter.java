package cz.cvut.fel.jee.batching;

import cz.cvut.fel.jee.ejb.NewsService;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.News;
import cz.cvut.fel.jee.model.Video;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 30.1.14
 * Time: 9:54
 * To change this template use File | Settings | File Templates.
 */
@Dependent
@Named
public class NewsItemWriter extends AbstractItemWriter {

    @Inject
    private VideoService videoService;

    @Inject
    private NewsService newsService;

    @Inject
    private Logger log;

    @Override
    public void writeItems(List<Object> objects) throws Exception {
        log.info("Saving batch!");
        News news = new News();
        news.setNews(new LinkedList<Video>());
        for (Object entity : objects) {
            log.info("Storing video change!");
            videoService.edit((Video) entity);
            news.getNews().add((Video) entity);
        }

        log.info("Batch saved!");
    }
}

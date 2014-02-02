package cz.cvut.fel.jee.beans;

import cz.cvut.fel.jee.annotations.CurrentLoggedUser;
import cz.cvut.fel.jee.ejb.NewsService;
import cz.cvut.fel.jee.model.News;
import cz.cvut.fel.jee.model.User;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 2.2.14
 * Time: 11:59
 * To change this template use File | Settings | File Templates.
 */
@Named("news")
public class NewsColection {

    @Inject
    @CurrentLoggedUser
    private User user;

    @Inject
    private NewsService newsService;

    public List<News> getAllNews() {
        return newsService.findAll();
    }

}

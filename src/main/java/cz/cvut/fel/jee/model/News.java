package cz.cvut.fel.jee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Tomáš on 29.1.14.
 */
@Entity
public class News extends EntityObject {

    @NotNull
    private Date createdAt;

    @OrderBy("createdAt")
    @ManyToMany
    private List<Video> news;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Video> getNews() {
        return news;
    }

    public void setNews(List<Video> news) {
        this.news = news;
    }

    @PrePersist
    private void prePersist(){
        createdAt = new Date();
    }
}

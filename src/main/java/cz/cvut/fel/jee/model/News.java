package cz.cvut.fel.jee.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Created by Tomáš on 29.1.14.
 */
@Table(name = "news")
@NamedQueries({
    @NamedQuery(name = "News.findByVideo", query = "SELECT n FROM News n WHERE :video MEMBER OF n.videos")
})
@Entity
public class News extends EntityObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4154257403095256492L;

	@NotNull
    private Date createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    private List<Video> videos;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @PrePersist
    private void prePersist(){
        createdAt = new Date();
        System.out.println(videos.size());
    }
}

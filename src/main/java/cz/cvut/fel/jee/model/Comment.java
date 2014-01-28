package cz.cvut.fel.jee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Tomáš on 21.1.14.
 */
@NamedQueries({
        @NamedQuery(name = "Comment.findByVideo", query = "SELECT c FROM Comment c WHERE c.video = :video")
})
@Entity
public class Comment extends EntityObject {
    @NotNull
    private String text;

    @NotNull
    @ManyToOne
    private User author;

    @NotNull
    private Date datetime;
    
    @NotNull
    @ManyToOne
    private Video video;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @PrePersist
    private void prePersist() {
        datetime = new Date();
    }
}

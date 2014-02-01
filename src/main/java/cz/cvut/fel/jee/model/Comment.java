package cz.cvut.fel.jee.model;

import cz.cvut.fel.jee.rest.adapters.CommentAdapter;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Tomáš on 21.1.14.
 */
@NamedQueries({
        @NamedQuery(name = "Comment.findByVideo", query = "SELECT c FROM Comment c WHERE c.video = :video")
})
@Entity
@XmlJavaTypeAdapter(CommentAdapter.class)
public class Comment extends EntityObject {
    @NotNull
    @NotEmpty
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

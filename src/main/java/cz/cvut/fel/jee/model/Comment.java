package cz.cvut.fel.jee.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Created by Tomáš on 21.1.14.
 */
@Entity
public class Comment extends EntityObject {
    @NotNull
    private String text;

    @NotNull
    @ManyToOne
    private User author;

    @NotNull
    @Past
    private Date datetime;

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

    @PrePersist
    private void prePersist() {
        datetime = new Date();
    }
}

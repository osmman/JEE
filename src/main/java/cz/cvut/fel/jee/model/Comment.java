package cz.cvut.fel.jee.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Created by Tomáš on 21.1.14.
 */
@Entity
public class Comment extends EntityObject
{
    @NotNull
    private String text;

    @NotNull
    @ManyToOne
    private User author;
    
    Timestamp datetime;

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }    
}

package cz.cvut.fel.jee.model;

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

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }
}

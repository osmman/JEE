package cz.cvut.fel.jee.model;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Tomáš on 21.1.14.
 */
@NamedQueries({
        @NamedQuery(name = "Video.findByAuthor", query = "SELECT v FROM Video v WHERE v.author = :author"),
        @NamedQuery(name="Video.count", query="SELECT COUNT(v) from Video v")  
})
@Entity
public class Video extends EntityObject
{

    @NotNull
    private String name;

    private String path;
    
    private String mimetype;

    private Boolean published;

    @NotNull
    @ManyToOne
    private User author;

    @OneToMany
    @OrderBy("datetime")
    private List<Comment> comments;

    @ManyToMany
    private Set<Tag> tags;

    @NotNull
    private Date createdAt;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }

    public List<Comment> getComments()
    {
        return comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }

    public Set<Tag> getTags()
    {
        return tags;
    }

    public void setTags(Set<Tag> tags)
    {
        this.tags = tags;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public void setCreatedAt(Date datetime) {
        this.createdAt = datetime;
    }

    @PrePersist
    private void prePersist(){
        this.createdAt = new Date();
    }
    
}

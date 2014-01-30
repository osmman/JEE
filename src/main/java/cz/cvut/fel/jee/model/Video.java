package cz.cvut.fel.jee.model;

import cz.cvut.fel.jee.rest.adapters.VideoAdapter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Tomáš on 21.1.14.
 */
@NamedQueries({
        @NamedQuery(name = "Video.findByAuthor", query = "SELECT v FROM Video v WHERE v.author = :author"),
        @NamedQuery(name="Video.count", query="SELECT COUNT(v) from Video v")  
})
@Entity
@XmlJavaTypeAdapter(VideoAdapter.class)
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

    @OneToMany(mappedBy = "video")
    @OrderBy("datetime")
    private List<Comment> comments;

    @ElementCollection
    private List<String> thumbs;

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

    public List<String> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<String> thumbs) {
        this.thumbs = thumbs;
    }

    @PrePersist
    private void prePersist(){
        this.createdAt = new Date();
    }
    
}

package cz.cvut.fel.jee.model;

import cz.cvut.fel.jee.rest.adapters.VideoAdapter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

/**
 * Created by Tomáš on 21.1.14.
 */
@NamedQueries({
        @NamedQuery(name = "Video.findByAuthor", query = "SELECT v FROM Video v WHERE v.author = :author"),
        @NamedQuery(name="Video.count", query="SELECT COUNT(v) from Video v"),
        @NamedQuery(name= "Video.findAllPublished",query = "SELECT v FROM Video v WHERE v.published = true")
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

    private String location;

    @NotNull
    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    @OrderBy("datetime")
    private List<Comment> comments;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> thumbs;

    @ManyToMany(mappedBy = "videos",cascade = CascadeType.ALL)
    private List<News> newsList;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @PrePersist
    private void prePersist(){
        this.createdAt = new Date();
    }
    
}

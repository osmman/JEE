package cz.cvut.fel.jee.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Tomáš on 21.1.14.
 */
@Entity
public class Tag extends EntityObject
{
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Video> video;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Set<Video> getVideo()
    {
        return video;
    }

    public void setVideo(Set<Video> video)
    {
        this.video = video;
    }
}

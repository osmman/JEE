package cz.cvut.fel.jee.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Tomáš on 10.1.14.
 */
@Entity
public class User extends EntityObject
{

    private String email;

    private String password;

    @OneToMany(mappedBy = "author")
    private Set<Video> videos;

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments;

    @Email
    @NotNull
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 6, max = 30)
    @NotNull
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}

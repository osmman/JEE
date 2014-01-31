package cz.cvut.fel.jee.model;

import cz.cvut.fel.jee.rest.adapters.UserAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.validator.constraints.Email;

/**
 * Created by Tomáš on 10.1.14.
 */
@NamedQueries({
    @NamedQuery(name = "User.findByEmail", query = "SELECT s FROM User s WHERE s.email like :email")
})
@Table(name = "users")
@Entity
@XmlJavaTypeAdapter(UserAdapter.class)
public class User extends EntityObject {

    private static final long serialVersionUID = 7434469730098060563L;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "author")
    private Set<Video> videos;

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments;

    @ManyToMany
    private Set<Role> roles;

    @Email
    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}

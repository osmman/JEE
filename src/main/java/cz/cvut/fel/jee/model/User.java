package cz.cvut.fel.jee.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.persistence.UniqueConstraint;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import javax.persistence.FetchType;

/**
 * Created by Tomáš on 10.1.14.
 */
@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "SELECT s FROM User s WHERE s.email like :email")
})
@Table(name = "users")
@Entity
public class User extends EntityObject
{

    private static final long serialVersionUID = 7434469730098060563L;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private Set<Video> videos;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private Set<Comment> comments;

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

    public void setPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String newPassword = password + email;
        md.update(newPassword.getBytes());
        byte[] pwd = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < pwd.length; i++) {
            sb.append(Integer.toString((pwd[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        newPassword = sb.toString();
        this.password = newPassword;
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
    
    
}

package cz.cvut.fel.jee.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.NamedQuery;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.UniqueConstraint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * Created by Tomáš on 10.1.14.
 */
@NamedQueries({
    @NamedQuery(name = "User.findByEmail", query = "SELECT s FROM User s WHERE s.email like :email")
     })
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email") })
@Entity
public class User extends EntityObject
{

	private static final long serialVersionUID = 7434469730098060563L;

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

    @NotNull
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException
    {
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
}

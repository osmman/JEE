package cz.cvut.fel.jee.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
@XmlRootElement
public class User extends EntityObject
{

	private static final long serialVersionUID = 7434469730098060563L;

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

    public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
    public void setPassword(String password)
    {
        this.password = password;
    }
}

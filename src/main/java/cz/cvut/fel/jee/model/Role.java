package cz.cvut.fel.jee.model;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE r.name like :name")
})
@Entity
@Table(name = "roles")
public class Role extends EntityObject{

	private static final long serialVersionUID = 6165139649037084589L;

        public static final String ADMIN = "admin";
        public static final String USER = "user";
        
        
	@NotNull
    private String name;
	
	@ManyToMany(mappedBy="roles")
    private Set<User> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (name != null ? !name.equals(role.name) : role.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

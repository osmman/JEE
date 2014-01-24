package cz.cvut.fel.jee.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tomáš on 10.1.14.
 */
@MappedSuperclass
public class EntityObject implements Serializable
{

	private static final long serialVersionUID = 5980313330840959274L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", updatable = false, nullable = false)
    protected Long id;

    public EntityObject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s(%d)[%s]", getClass().getSimpleName(), getId(), super.toString());
    }
}

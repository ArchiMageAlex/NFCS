package nfcs.model;

import java.io.Serializable;
import javax.persistence.*;

import nfcs.model.core.BaseEntity;

import org.metawidget.inspector.annotation.UiLabel;
import java.util.List;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name="roles")
@NamedQueries({
	@NamedQuery(name = "getRoleByName", query = "select r from Role r where r.name=:name") })
@Access(javax.persistence.AccessType.PROPERTY)
public class Role extends BaseEntity implements Serializable {
	@Override
	public String toString() {
		return this.getName();
	}

	private static final long serialVersionUID = 1L;

	private String name;

	private List<User> users;

    public Role() {
    }

    @UiLabel("Название")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@UiLabel("Пользователи")
	@ManyToMany
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
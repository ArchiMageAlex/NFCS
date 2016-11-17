package nfcs.model;

import java.io.Serializable;
import javax.persistence.*;

import nfcs.model.core.BaseEntity;

import org.metawidget.inspector.annotation.UiHidden;
import org.metawidget.inspector.annotation.UiLabel;
import java.util.List;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
@NamedQueries({
		@NamedQuery(name = "getUserByName", query = "select u from User u where u.name=:name"),
		@NamedQuery(name = "getAllUsers", query = "select u from User u") })
public class User extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	private String password;

	// bi-directional many-to-many association to Role
	private List<Role> roles;

	public User() {
	}

	@UiLabel("Name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@UiHidden
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@UiLabel("Roles")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "users")
	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
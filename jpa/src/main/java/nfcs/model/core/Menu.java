package nfcs.model.core;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

import org.metawidget.inspector.annotation.UiLabel;
import org.metawidget.inspector.faces.UiFacesLookup;

import nfcs.model.core.BaseEntity;

/**
 * Entity implementation class for Entity: Menu
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "getMenuChildren", query = "select m from Menu m where m.parent = :parent"),
		@NamedQuery(name = "getRootMenu", query = "select m from Menu m where m.parent is null") })
public class Menu extends BaseEntity implements Serializable {
	private String name;
	private String entityName;
	private Menu parent;
	private static final long serialVersionUID = 1L;

	public Menu() {
		super();
	}

	@Column(nullable = false)
	@UiLabel("Menu label")
	public String getName() {
		return this.name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	@UiLabel("Entity name")
	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String EntityName) {
		this.entityName = EntityName;
	}

	@ManyToOne()
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.core.Menu')}", itemLabel = "#{menu.name}", itemValue = "#{menu.id}", var = "menu")
	@UiLabel("Parent item")
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Menu)) {
			return ((Menu) obj).getId().equals(this.getId());
		} else {
			if (obj instanceof Long)
				// TODO: Potential comparation error...
				return ((Long) obj).equals(this.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getId() == null ? super.hashCode() : this.getId().intValue();
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}

package nfcs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.metawidget.inspector.annotation.UiLabel;
import org.metawidget.inspector.faces.UiFacesLookup;

/**
 * Entity implementation class for Entity: Organization
 * 
 */
@Entity
@NamedQuery(name = "findAllOrganizations", query = "select o from Organization o")
public class Organization extends Subject implements Serializable {
	private Colleague chief;
	private List<Department> departments;
	private List<Colleague> colleagues;

	private static final long serialVersionUID = 1L;

	public Organization() {
		super();
	}

	@OneToOne(cascade = { CascadeType.DETACH })
//	@JoinColumn(name = "chief")
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Colleague')}", itemLabel = "#{chief.name}", itemValue = "#{chief.id}", var = "chief")
	@UiLabel("Chief")
	public Colleague getChief() {
		return this.chief;
	}

	public void setChief(Colleague chief) {
		this.chief = chief;
	}

	@OneToMany(mappedBy="organization")
	@UiLabel("Departments")
	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	@OneToMany(mappedBy="organization")
	@UiLabel("Staff")
	public List<Colleague> getColleagues() {
		return colleagues;
	}

	public void setColleagues(List<Colleague> colleagues) {
		this.colleagues = colleagues;
	}
}
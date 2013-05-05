package nfcs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.metawidget.inspector.annotation.UiLabel;
import org.metawidget.inspector.faces.UiFacesLookup;

/**
 * Entity implementation class for Entity: Department
 * 
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "findAllDepartments", query = "Select d from Department d") })
public class Department extends Subject implements Serializable {
	private Department parentDepartment;
	private List<Colleague> colleagues;
	private Colleague chief;
	private List<Department> children;
	private Organization organization;

	@ManyToOne(cascade = { CascadeType.DETACH }, targetEntity = Organization.class)
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Organization')}", itemLabel = "#{org.name}", itemValue = "#{org.id}", var = "org")
	@UiLabel("Организация")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	private static final long serialVersionUID = 1L;

	public Department() {
		super();
	}

	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Department')}", itemLabel = "#{dept.name}", itemValue = "#{dept.id}", var = "dept")
	@UiLabel("Вышестоящее подразделение")
	@ManyToOne(cascade = { CascadeType.DETACH }) 
	public Department getParentDepartment() {
		return this.parentDepartment;
	}

	public void setParentDepartment(Department ParentDepartment) {
		this.parentDepartment = ParentDepartment;
	}

	@OneToOne(cascade = { CascadeType.DETACH })
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Colleague')}", itemLabel = "#{chief.name}", itemValue = "#{chief.id}", var = "chief")
	@UiLabel("Руководитель")
	public Colleague getChief() {
		return this.chief;
	}

	public void setChief(Colleague Chief) {
		this.chief = Chief;
	}

	@UiLabel("Сотрудники")
	@OneToMany(cascade={CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH }, targetEntity=Colleague.class, mappedBy="department")
	public List<Colleague> getColleagues() {
		return colleagues;
	}

	public void setColleagues(List<Colleague> colleagues) {
		this.colleagues = colleagues;
	}

	@OneToMany(cascade={CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH }, targetEntity=Department.class, mappedBy="parentDepartment")
	@UiLabel("Нижестоящие подразделения")
	public List<Department> getChildren() {
		return children;
	}

	public void setChildren(List<Department> children) {
		this.children = children;
	}
}
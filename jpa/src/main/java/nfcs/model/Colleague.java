package nfcs.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.metawidget.inspector.annotation.UiLabel;
import org.metawidget.inspector.faces.UiFacesDateTimeConverter;
import org.metawidget.inspector.faces.UiFacesLookup;

/**
 * Entity implementation class for Entity: Colleague
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findAllColleagues", query = "select c from Colleague c") })
public class Colleague extends Subject implements Serializable {
	private Organization organization;
	private Date birthDate;
	private Department department;

	private static final long serialVersionUID = 1L;

	public Colleague() {
		super();
	}

	@UiLabel("Birthdate")
	@Temporal(TemporalType.DATE)
	@UiFacesDateTimeConverter(locale = "ru_RU", timeZone = "Asia/Vladivostok", pattern = "dd.MM.yyyy")
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Colleague)) {
			return ((Colleague) obj).getId().equals(this.getId());
		} else {
			if (obj instanceof Long)
				// TODO: Potential comparing error...
				return ((Long) obj).equals(this.getId());
		}
		return false;
	}

	@UiLabel("Department")
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Department')}", var = "department", itemLabel = "#{department.name}", itemValue = "#{department.id}")
	@ManyToOne
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@UiLabel("Organization")
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Organization')}", var = "org", itemLabel = "#{org.name}", itemValue = "#{org.id}")
	@ManyToOne
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}

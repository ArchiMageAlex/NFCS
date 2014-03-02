package nfcs.model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;
import javax.persistence.*;

import org.metawidget.inspector.annotation.UiLabel;

import nfcs.model.core.BaseEntity;

/**
 * Entity implementation class for Entity: Subject
 * 
 */
@Entity
@NamedQueries({	@NamedQuery(name = "findSubjectByName", query = "select c from Subject c where c.name = :name")})
@Inheritance(strategy = InheritanceType.JOINED)
public class Subject extends BaseEntity implements Serializable {
	private String name;
	private Report schedules;
	private List<Transport> contacts;
	private static final long serialVersionUID = 1L;

	public Subject() {
		super();
	}

	@Column
	@UiLabel("��������/���")
	public String getName() {
		return this.name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	public void setSchedules(Report schedules) {
		this.schedules = schedules;
	}

	@ManyToOne(optional = true, cascade={CascadeType.ALL})
	@JoinColumn(name = "schedules", nullable = true)
	@UiLabel("������")
	public Report getSchedules() {
		return schedules;
	}

	@OneToMany(mappedBy="subject", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
	@UiLabel("��������")
	public List<Transport> getContacts() {
		return contacts;
	}

	public void setContacts(List<Transport> contacts) {
		this.contacts = contacts;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}
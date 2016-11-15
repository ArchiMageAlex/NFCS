package nfcs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import nfcs.model.core.BaseEntity;

@Entity
public abstract class Transport extends BaseEntity implements Serializable, TransportInterface {
	private static final long serialVersionUID = 1L;
	private Subject subject;

	public Transport() { 
		super();
	} 

	@Override
	public void send() throws Exception {
		throw new Exception("Does not realized method send() for class "
				+ this.getClass().getName());
	}

	@ManyToOne
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}

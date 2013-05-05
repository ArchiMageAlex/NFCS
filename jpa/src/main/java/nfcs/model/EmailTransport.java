package nfcs.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EmailTransport
 * 
 */
@Entity
public class EmailTransport extends Transport implements Serializable { 
	private String eMail;

	private static final long serialVersionUID = 1L;

	public EmailTransport() {
		super();
	}

	@Column(nullable = false)
	public String getEMail() {
		return this.eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	@Override
	public String toString() {
		return this.getEMail();
	}
}

package nfcs.model.core;

import java.io.Serializable;
import java.lang.Long;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

import nfcs.model.Colleague;

import org.metawidget.inspector.annotation.UiAttribute;
import org.metawidget.inspector.annotation.UiAttributes;
import org.metawidget.inspector.annotation.UiHidden;
import org.metawidget.inspector.annotation.UiLabel;
import org.metawidget.inspector.faces.UiFacesDateTimeConverter;
import org.metawidget.inspector.faces.UiFacesLookup;

/**
 * Entity implementation class for Entity: BaseEntity
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.PROPERTY)
public class BaseEntity implements Serializable {
	private Long id;

	private Date createdOn;

	private Colleague createdBy;
 
	private Date validFrom;

	private Date validTo;

	private static final long serialVersionUID = 1L;

	public BaseEntity() {
		super();
		this.setCreatedOn(new Date(System.currentTimeMillis()));
		this.setValidFrom(this.getCreatedOn());

		try {
			this.setValidTo(new SimpleDateFormat("dd.MM.yyyy")
					.parse("31.12.4000"));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new Error(
					"Can't parse default end valid period for entity id="
							+ this.getId().toString() + " for class="
							+ this.getClass().getName());
		}
	}

	@UiLabel("Id")
	@UiHidden
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@UiFacesDateTimeConverter(locale = "ru_RU", timeZone = "Asia/Vladivostok", pattern = "dd.MM.yyyy")
	@UiLabel("Date of creation")
	@Temporal(value = TemporalType.TIMESTAMP)
	@UiAttributes({ @UiAttribute(name = "lang", value = "ru") })
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Colleague')}", itemLabel = "#{colleague.name}", itemValue = "#{colleague.id}", var = "colleague")
	@UiLabel("Creator")
	@OneToOne(cascade = { CascadeType.DETACH })
	public Colleague getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Colleague createdBy) {
		this.createdBy = createdBy;
	}

	@UiFacesDateTimeConverter(locale = "ru_RU", timeZone = "Asia/Vladivostok", pattern = "dd.MM.yyyy")
	@UiLabel("Valid from")
	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	@UiFacesDateTimeConverter(locale = "ru_RU", timeZone = "Asia/Vladivostok", pattern = "dd.MM.yyyy")
	@UiLabel("Valid to")
	@Temporal(value = TemporalType.TIMESTAMP)
	public Date getValidTo() {
		return this.validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof BaseEntity)) {
			return ((BaseEntity) obj).getId().equals(this.getId());
		} else {
			if (obj instanceof Long)
				// TODO: Potential comparation error...
				return ((Long) obj).equals(this.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getId() == null ? super.hashCode() : this.getId()
				.intValue();
	}
}

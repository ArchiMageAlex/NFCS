package nfcs.model.schedule;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import org.metawidget.inspector.annotation.UiHidden;
import org.metawidget.inspector.annotation.UiLabel;

import nfcs.model.Report;
import nfcs.model.core.BaseEntity;

@Entity
public class Schedule extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date begins = new Date(System.currentTimeMillis());
	private Date ends = java.sql.Date.valueOf("3001-01-01"); 
	private Integer repeat = 1;
	private Integer startFrom = 1;
	private Integer every = 1;
	private Calendar calendar = GregorianCalendar.getInstance();
	private Integer periodType = Calendar.DAY_OF_YEAR;
	private Report report;

	public boolean isOccuring(String event, Date date) {
		Integer repeatCounter = this.getRepeat();
		Calendar cal = (Calendar) this.getCalendar().clone();
		cal.setTime(this.getBegins());
		cal.add(this.getPeriodType(), this.getStartFrom() - 1);

		if (date.before(this.getEnds()) || date.equals(this.getEnds()))
			while (cal.getTime().before(date) && --repeatCounter != 0)
				cal.add(this.getPeriodType(), this.getEvery());

		try {
			return this.compareDates(cal.getTime(), date);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean compareDates(Date date1, Date date2) throws Exception {
		throw new Exception("dates intersects");
	}

	public boolean isOverdue(String event, Date date) {
		// return this.before(date)
		return true;
	}

	public Date nextOccurence(String event, Date date) {
		return date;
	}

	public List<Date> getOccurences(String event, DateRange during) {
		List<Date> l = new Vector<Date>();

		for (Date d : during.getDateList())
			if (this.isOccuring(event, d))
				l.add(d);
		return l;
	}

	public void setBegins(Date begins) {
		this.begins = begins;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@UiLabel("Date begins")
	public Date getBegins() {
		return begins;
	}

	public void setEnds(Date ends) {
		this.ends = ends;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@UiLabel("Date ends")
	public Date getEnds() {
		return ends;
	}

	public void setRepeat(Integer repeat) {
		this.repeat = repeat;
	}

	@Column(nullable = false)
	@UiLabel("Repeat")
	public Integer getRepeat() {
		return repeat;
	}

	public void setStartFrom(Integer startFrom) {
		this.startFrom = startFrom;
	}

	@Column(nullable = false)
	@UiLabel("Starts from")
	public Integer getStartFrom() {
		return startFrom;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@UiHidden
	public Calendar getCalendar() {
		return calendar;
	}

	public void setEvery(Integer every) {
		this.every = every;
	}

	@UiLabel("Every ..")
	public Integer getEvery() {
		return every;
	}

	public void setPeriodType(Integer periodType) {
		this.periodType = periodType;
	}

	@Column(nullable = false)
	@UiLabel("Period type")
	public Integer getPeriodType() {
		return periodType;
	}

	public void validate() throws Exception {
		if (this.getBegins().compareTo(this.getEnds()) > 0)
			throw new Exception(
					"Property 'From' cannot be greater than property 'To'");
	}

	@OneToOne
	@UiHidden
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}
}
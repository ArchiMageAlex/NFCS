package nfcs.web;

import java.io.Serializable;
import java.util.Date;

import nfcs.model.Colleague;

import org.primefaces.model.*;
 
public class NFCSScheduleEvent implements ScheduleEvent, Serializable {
	private static final long serialVersionUID = 1L;
	private Colleague executor;
	private Date endDate;
	private Date startDate; 
	private String title;
	private Long id;
	private Boolean allDay;
	private Object data;
	private String styleClass;

	public NFCSScheduleEvent(Long id, String title, Date start, Date end, Colleague executor) {
		this.setId(id.toString());
		this.setTitle(title);
		this.setStartDate(start);
		this.setEndDate(end);
		this.setExecutor(executor);
	}

	public NFCSScheduleEvent(String title, Date start, Date end, Colleague executor) {
		this.setTitle(title);
		this.setStartDate(start);
		this.setEndDate(end);
		this.setExecutor(executor);
	}

	public NFCSScheduleEvent() {
	}

	public String toString() {
		return "NFCSScheduleEvent{id=" + this.getId() + ",title="
			+ this.getTitle() + ",startDate=" + this.getStartDate().toString()
			+ ",endDate=" + this.getEndDate().toString() + ",executor="
			+ this.executor.getId().toString() + "}";

	}

	public void setExecutor(Colleague executor) {
		this.executor = executor;
	}

	public Colleague getExecutor() {
		return executor;
	}

	@Override
	public Object getData() {
		return this.data;
	}

	@Override
	public Date getEndDate() {
		return this.endDate;
	}

	@Override
	public String getId() {
		return this.id == null ? null : this.id.toString();
	}

	@Override
	public Date getStartDate() {
		return this.startDate;
	}

	@Override
	public String getStyleClass() {
		return this.styleClass;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean isAllDay() {
		return false;
	}

	@Override
	public void setId(String id) {
		this.id = Long.parseLong(id);
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public boolean getAllDay() {
		return allDay;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final NFCSScheduleEvent other = (NFCSScheduleEvent) obj;
		if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return (int) (this.id ^ (this.id >>> 32));
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}

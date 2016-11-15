package nfcs.web;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import nfcs.model.Colleague;
import nfcs.model.Subject;
import nfcs.model.schedule.Schedule;

import org.primefaces.event.*;
import org.primefaces.model.*;

@ManagedBean
@SessionScoped
public class ScheduleController implements Serializable {
	private static final long serialVersionUID = 1L;
 
	private NFCSScheduleModel eventModel = new NFCSScheduleModel();

	private NFCSScheduleEvent event = new NFCSScheduleEvent();

	@EJB(name = "EJB")
	private nfcs.ejb.EJB schedule;

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean login;

	private List<Colleague> subjects;

	public LoginBean getLogin() {
		return login;
	}

	public void setLogin(LoginBean login) {
		this.login = login;
	}

	public ScheduleController() {
	}

	public Date getRandomDate(Date base) {
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); // set random
																	// day of
																	// month

		return date.getTime();
	}

	@PostConstruct
	public void ejbPostConstruct() throws Exception {
/*		List<Report> lrs = reportEJB.find("findAllSchedules");

		for (Report rs : lrs) {
			for (Schedule sch : rs.getSchedules()) {
				NFCSScheduleEvent dse = new NFCSScheduleEvent(rs.getId(),
						rs.getName(), sch.getBegins(), sch.getEnds(), rs.getExecutor());
				dse.setExecutor(login.getUser());
				dse.setId(sch.getId().toString());
				getEventModel().addEvent(dse);
			}
		}
		subjects = colleagueEJB.find("findAllColleagues");*/
	}

	public void addEvent(ActionEvent actionEvent) {
		System.out.println("Add event");
		if (getEvent().getId() == null) {
			createEvent();
		} else {
			updateEvent(((NFCSScheduleEvent) getEvent()));
		}

		setEvent(new NFCSScheduleEvent());
	}

	public void deleteEvent(ActionEvent event) {
		Schedule entity;
		FacesMessage message;

		try {
			String eventId = getEvent().getId();
			getEventModel().deleteEvent(getEvent());
			entity = (Schedule) schedule
					.findById(Schedule.class, Long.parseLong(eventId));
			schedule.remove(entity);
			System.out.println("������� �������, id: " + getEvent().getId());
			setEvent(new NFCSScheduleEvent());
		} catch (NumberFormatException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"������ ��������������", e.getLocalizedMessage()
							+ ", �������� " + getEvent().getId());
			addMessage(message);
			e.printStackTrace();
		} catch (Exception ex) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "������",
					ex.getLocalizedMessage());
			addMessage(message);
			ex.printStackTrace();
		}
	}

	private void createEvent() {
/*		Schedule sch = new Schedule();
		sch.setBegins(getEvent().getStartDate());
		sch.setEnds(getEvent().getEndDate());
		sch = schedule.create(sch);

		Report entity = new Report();
		entity.setCode(getEvent().getTitle());
		entity.setName(getEvent().getTitle());

		if (((NFCSScheduleEvent) getEvent()).getExecutor() == null) {
			entity.setExecutor(this.getLogin().getUser());
			((NFCSScheduleEvent) getEvent()).setExecutor(this.getLogin()
					.getUser());
		} else
			entity.setExecutor(((NFCSScheduleEvent) getEvent()).getExecutor());

		entity = reportEJB.create(entity);
		entity.getSchedules().add(sch);
		entity = reportEJB.update(entity);
		sch.setReport(entity);
		sch = schedule.update(sch);
		getEvent().setId(sch.getId().toString());
		getEventModel().addEvent(getEvent());
		System.out.println("Create event: " + getEvent().toString());*/
	}

	private void updateEvent(NFCSScheduleEvent event) {
/*		Report entity;
		Schedule sch;
		FacesMessage message;

		try {
			sch = schedule.findById(Schedule.class,
					Long.parseLong(event.getId()));
			entity = reportEJB.findById(Report.class,
					sch.getReport().getId());
			sch.setBegins(event.getStartDate());
			sch.setEnds(event.getEndDate());
			schedule.update(sch);
			entity.setName(event.getTitle());
			entity.setExecutor(event.getExecutor());
			entity = reportEJB.update(entity);
			getEventModel().updateEvent(event);
			System.out.println("Event update successful: " + entity.getId());
		} catch (NumberFormatException e) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"������ ��������������", e.getLocalizedMessage()
							+ ", �������� " + event.getId());
			addMessage(message);
			e.printStackTrace();
		} catch (Exception ex) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "������",
					ex.getLocalizedMessage());
			addMessage(message);
			ex.printStackTrace();
		}*/
	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		this.event = (NFCSScheduleEvent) selectEvent.getScheduleEvent();
	}

	public void onDateSelect(DateSelectEvent selectEvent) {
		this.event = new NFCSScheduleEvent("New event onDateSelect",
				selectEvent.getDate(), selectEvent.getDate(), this.getLogin()
						.getUser());
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		Calendar c = GregorianCalendar.getInstance(TimeZone
				.getTimeZone("Asia/Vladivostok"));
		NFCSScheduleEvent nfcsEvent = (NFCSScheduleEvent) event
				.getScheduleEvent();

		System.out.println(nfcsEvent.getStartDate());
		c.setTimeInMillis(event.getScheduleEvent().getStartDate().getTime());
		c.add(Calendar.DAY_OF_YEAR, event.getDayDelta());
		c.add(Calendar.MINUTE, event.getMinuteDelta());
		nfcsEvent.setStartDate(c.getTime());
		System.out.println(nfcsEvent.getStartDate());

		System.out.println(nfcsEvent.getEndDate());
		c = GregorianCalendar.getInstance();
		c.setTimeInMillis(event.getScheduleEvent().getEndDate().getTime());
		c.add(Calendar.DAY_OF_YEAR, event.getDayDelta());
		c.add(Calendar.MINUTE, event.getMinuteDelta());
		nfcsEvent.setEndDate(c.getTime());
		System.out.println(nfcsEvent.getEndDate());

		updateEvent(nfcsEvent);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		Calendar c = GregorianCalendar.getInstance();
		NFCSScheduleEvent nfcsEvent = (NFCSScheduleEvent) event
				.getScheduleEvent();

		c.setTimeInMillis(event.getScheduleEvent().getEndDate().getTime());
		c.add(Calendar.DAY_OF_YEAR, event.getDayDelta());
		c.add(Calendar.MINUTE, event.getMinuteDelta());
		nfcsEvent.setEndDate(c.getTime());

		updateEvent(nfcsEvent);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void setEvent(ScheduleEvent event) {
		this.event = (NFCSScheduleEvent) event;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = (NFCSScheduleModel) eventModel;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setColleagues(List<Colleague> subjects) {
		this.subjects = subjects;
	}

	public List<Colleague> getColleagues() {
		return subjects;
	}

	public List<Subject> completeSubjects(String query) {
		List<Subject> suggestions = new ArrayList<Subject>();

/*		for (Subject p : subjects) {
			if (p.getEMail().startsWith(query))
				suggestions.add(p);
		}*/

		return suggestions;
	}
}

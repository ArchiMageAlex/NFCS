package nfcs.model;

import java.io.Serializable;
import java.lang.String;
import java.util.List;
import javax.persistence.*;

import org.metawidget.inspector.annotation.UiLabel;
import org.metawidget.inspector.faces.UiFacesLookup;

import nfcs.model.core.BaseEntity;
import nfcs.model.schedule.Schedule;

/**
 * Entity implementation class for Entity: ReportSchedule
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findAllSchedules", query = "select c from Report c") })
public class Report extends BaseEntity implements Serializable {
	private String code;
	private String name;
	private Colleague executor;
	private Colleague controller;
	private List<Transport> destinations;
	private Schedule schedule = new Schedule();
	private static final long serialVersionUID = 1L;
	private Integer priority;
	private Report previousReport;

	public Report() {
		super();
	}

	@Column(nullable = false)
	@UiLabel("Код отчета")
	public String getCode() {
		return this.code;
	}

	public void setCode(String Code) {
		this.code = Code;
	}

	@Column(nullable = false)
	@UiLabel("Название отчета")
	public String getName() {
		return this.name;
	}

	public void setName(String Name) {
		this.name = Name;
	}

	public void setExecutor(Colleague Executor) {
		executor = Executor;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(nullable = false)
	@UiLabel("Ответственный")
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Colleague')}", itemLabel = "#{executor.name}", itemValue = "#{executor.id}", var = "executor")
	public Colleague getExecutor() {
		return executor;
	}

	public void setController(Colleague Controller) {
		controller = Controller;
	}

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinColumn(nullable = false)
	@UiLabel("Контролер")
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Colleague')}", itemLabel = "#{controller.name}", itemValue = "#{controller.id}", var = "controller")
	public Colleague getController() {
		return controller;
	}

	@OneToOne(mappedBy="report", cascade={ CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@UiLabel("Расписание")
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	@ManyToMany(cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@UiLabel("Получатели")
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Transport')}", itemLabel = "#{transport.name}", itemValue = "#{transport.id}", var = "transport")
	public List<Transport> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Transport> destinations) {
		this.destinations = destinations;
	}

	@UiLabel("Приоритет (больше-выше)")
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	@UiLabel("Предыдущий отчет")
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@UiFacesLookup(value = "#{entityController.getForLookup('nfcs.model.Report')}", itemLabel = "#{report.name}", itemValue = "#{report.id}", var = "report")
	public Report getPrev() {
		return previousReport;
	}

	public void setPrev(Report prev) {
		this.previousReport = prev;
	}
}

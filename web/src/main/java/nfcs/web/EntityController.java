package nfcs.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import nfcs.ejb.EJB;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nfcs.model.core.BaseEntity;

@ManagedBean
@SessionScoped
public class EntityController implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(EntityController.class);

	protected Class<? extends BaseEntity> entityClass;
	protected List<ColumnModel> columns = new ArrayList<ColumnModel>();

	@javax.ejb.EJB(name = "EJB")
	protected EJB ejb;
	private BaseEntity currentEntity;
	private BaseEntity selection;

	@ManagedProperty("#{lazyEntityDataModel}")
	private LazyEntityDataModel lazyDataModel;

	private String entityClassName;

	public EntityController() {
		super();
	}

	@PostConstruct
	public void controllerSetup() {
		logger.debug("Start init of EntityController");
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		String entity = extContext.getRequestParameterMap().get("entityClassName");
		this.setEntityClassName(entity);
		ejb.setEntityClass(this.getEntityClassName());
		this.entityClass = ejb.getEntityClass();
		Properties props = this.ejb.getEntityProperties(this.getEntityClass());

		if (props != null) {
			columns = new ArrayList<ColumnModel>();
			Enumeration<?> i = props.keys();

			while (i.hasMoreElements()) {
				String key = (String) i.nextElement();
				columns.add(new ColumnModel(props.getProperty(key), key));
			}
		}

		this.getLazyDataModel().setEntityClass(entity);
		this.getLazyDataModel().init();
		logger.debug("Data filled for entity " + entity);
		this.setCurrentEntity(null);
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public Class<? extends BaseEntity> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<? extends BaseEntity> entityClass) {
		this.entityClass = entityClass;
	}

	public String getEntityClassName() {
		return this.entityClassName;
	}

	public void setEntityClassName(String className) {
		if (className != null)
			this.entityClassName = className;
	}

	protected void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public BaseEntity getCurrentEntity() {
		return currentEntity;
	}

	public void setCurrentEntity(BaseEntity currentEntity) {
		this.currentEntity = currentEntity;
	}

	public BaseEntity getSelection() {
		return selection;
	}

	public void setSelection(BaseEntity selection) {
		this.selection = selection;
	}

	public String create() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			this.setCurrentEntity(this.entityClass.newInstance());
			this.setSelection(this.getCurrentEntity());
			updateTable(this.getCurrentEntity());
		} catch (InstantiationException e) {
			context.addMessage("Cannot create entity",
					new FacesMessage(e.getLocalizedMessage()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			context.addMessage("Access denied for entity creation",
					new FacesMessage(e.getLocalizedMessage()
									+ this.entityClass.getName()));
			e.printStackTrace();
		}
		return "";
	}

	public String save() {
		ejb.update(this.getCurrentEntity());
		updateTable(this.getCurrentEntity());
		return "";
	}

	public String delete() {
		ejb.remove(this.getCurrentEntity());
		updateTable(null);
		return "";
	}

	private void updateTable(BaseEntity current) {
		FacesContext context = FacesContext.getCurrentInstance();
		DataTable dataTable = (DataTable) context.getViewRoot().findComponent("crud:table");
		dataTable.setSelection(current);
		dataTable.processUpdates(context);
		this.setCurrentEntity(current);
	}

	public List<BaseEntity> getForLookup(String className) {
		return this.ejb.getAll4Class(className);
	}

	public void tableRowSelectListener(SelectEvent event) {
		this.setCurrentEntity((BaseEntity) event.getObject());
	}

	public void tableRowUnSelectListener(SelectEvent event) {
		this.setCurrentEntity(null);
	}

	public LazyEntityDataModel getLazyDataModel() {
		if (null != lazyDataModel) {
			if (null != lazyDataModel.getEntityClass()) {
				if (!lazyDataModel.getEntityClass().equals(this.getEntityClassName())) {
					logger.info("Get refilled lazy data model");
					lazyDataModel.setEntityClass(this.getEntityClassName());
					lazyDataModel.init();
				}
			} else {
				logger.info("Get filled lazy data model");
				lazyDataModel.setEntityClass(this.getEntityClassName());
				lazyDataModel.init();
			}
		}

		return lazyDataModel;
	}

	public void setLazyDataModel(LazyEntityDataModel lazyDataModel) {
		this.lazyDataModel = lazyDataModel;
	}
}
package nfcs.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nfcs.ejb.EJB;
import nfcs.model.core.BaseEntity;

@ManagedBean(name = "lazyEntityDataModel")
@SessionScoped
public class LazyEntityDataModel extends LazyDataModel<BaseEntity> {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(LazyEntityDataModel.class);

	protected List<? extends BaseEntity> entities;
	@javax.ejb.EJB(name = "EJB")
	protected EJB ejb;
	private String entityClass;

	@PostConstruct
	public void init() {
		logger.info("Start init of LazyEntityDataModel");
		if (this.entityClass != null) {
			logger.info("Init for class " + this.entityClass);
			entities = this.ejb.getAll4Class(this.getEntityClass());
		}
	}

	@Override
	public BaseEntity getRowData(String rowKey) {
		logger.info("get row data");
		return ejb.findById(BaseEntity.class, Long.parseLong(rowKey));
	}

	@Override
	public Object getRowKey(BaseEntity entity) {
		logger.info("get row key");
		return entity.getId();
	}

	@Override
	public List<BaseEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		logger.info("Start loading data");
		List<BaseEntity> data = new ArrayList<BaseEntity>();
		data = ejb.getAll4Class(this.getEntityClass());
		logger.info("Array of " + data.size() + " entries of entity " + this.getEntityClass());
		return data;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
}

package nfcs.ejb;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;

import nfcs.model.*;
import nfcs.model.core.BaseEntity;
import javax.ejb.*;

@Stateless
public class EJB implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext//(type = PersistenceContextType.EXTENDED)
	protected EntityManager em;
	@Resource
	private SessionContext ctx;
	private Class<BaseEntity> entityClass;

    public BaseEntity create(BaseEntity entity) {
//		entity = em.merge(entity);
/*		if (entity instanceof BaseEntity)
			((BaseEntity) entity)
					.setCreatedBy(em
							.find(Colleague.class,
									((BaseEntity) em
											.createQuery(
													"select c from Colleague c where c.name = :name")
											.setParameter(
													"name",
													ctx.getCallerPrincipal()
															.getName())
											.getSingleResult()).getId()));*/
		return em.merge(entity);
	}

    @SuppressWarnings("unchecked")
	public List<BaseEntity> find(String queryName) {
		Query query = em.createNamedQuery(queryName);
		return query.getResultList();
	}

    public List<BaseEntity> getAll() {
		Query query = em.createQuery("select o from "
				+ this.getEntityClass().getSimpleName() + " o");
		return query.getResultList();
	}

    public List<BaseEntity> getAll4Class(String className) {
		Query query = em.createQuery("select o from " + className + " o");
		return query.getResultList();
	}

    public List<BaseEntity> getEntitiesOfClassByProperty(
            Class vClass, String propertyName, String propertyValue) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BaseEntity> cq = cb.createQuery(vClass);
		Root<BaseEntity> root = cq.from(vClass);
		ParameterExpression<String> par = cb.parameter(String.class);
		cq.select(root).where(
				cb.like(root.get(propertyName).as(String.class), par));
		TypedQuery<BaseEntity> tq = em.createQuery(cq);
		tq.setParameter(par, propertyValue);

		return tq.getResultList();
	}

    public void remove(BaseEntity entity) {
		em.remove(em.merge(entity));
	}

    public BaseEntity update(BaseEntity entity) {
		return em.merge(entity);
	}

    public BaseEntity findById(Class<? extends BaseEntity> objectClass, Long id) {
		return (BaseEntity) em.find(objectClass, id);
	}

    public Properties getEntityProperties(
            Class<? extends BaseEntity> entityClass) {
		EntityType<?> entity = em.getMetamodel().entity(entityClass);// i.next();
		Set<?> attrs = entity.getSingularAttributes();
		Properties props = new Properties();
		Iterator<?> i = attrs.iterator();

		while (i.hasNext()) {
			boolean hide = false;
			Attribute<?, ?> attr = (Attribute<?, ?>) i.next();
			String name = attr.getName();

			if (attr.getJavaMember() instanceof Method) {
				Method m = (Method) attr.getJavaMember();

				for (Annotation a : m.getAnnotations()) {
					if (a.annotationType()
							.getName()
							.equals("org.metawidget.inspector.annotation.UiHidden")) {
						hide = true;
					}
					if (a.annotationType()
							.getName()
							.equals("org.metawidget.inspector.annotation.UiLabel")) {
						try {
							Method call = a.annotationType().getMethod("value",
									new Class[0]);
							name = (String) call.invoke(a, null);
						} catch (SecurityException e) {
							hide = true;
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			if (!hide)
				props.put(attr.getName(), name);
		}

		return props;
	}

    public Class<BaseEntity> getEntityClass() {
		return entityClass;
	}

    @SuppressWarnings("unchecked")
	public void setEntityClass(String className) {
		try {
			this.entityClass = (Class<BaseEntity>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}

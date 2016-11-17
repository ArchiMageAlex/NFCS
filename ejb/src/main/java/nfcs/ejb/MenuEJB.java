package nfcs.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nfcs.model.core.Menu;

@Stateless
public class MenuEJB implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(MenuEJB.class);

	@PersistenceContext // (type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	public List<Menu> getChildren(Menu menu) {
		Query query = em.createNamedQuery("getMenuChildren");
		query.setParameter("parent", menu);
		return query.getResultList();
	}

	public List<Menu> getRootMenu() {
		Query query = em.createNamedQuery("getRootMenu");
		return query.getResultList();
	}

	public void initMenu() {
		Menu menu;

		for (EntityType<?> entity : em.getMetamodel().getEntities()) {
			Query findByName = em.createQuery("select m from Menu m where entityname = :entityname");
			findByName.setParameter("entityname", entity.getJavaType().getName());
			logger.info("Finding menu for entity with class " + entity.getJavaType().getName());

			try {
				menu = (Menu) findByName.getSingleResult();
				logger.info("Menu found for class " + entity.getJavaType().getName());
			} catch (NoResultException nre) {
				logger.info("Menu not found for class " + entity.getJavaType().getName() + ", adding");
				menu = new Menu();
				menu.setName(entity.getName());
				menu.setEntityName(entity.getJavaType().getName());

				Query findParentMenu = em.createQuery("select m from Menu m where entityname = ?");
				try {
					findParentMenu.setParameter(1, entity.getSupertype().getJavaType().getName());
					logger.info("Finding parent menu " + entity.getSupertype().getJavaType().getName()
							+ " for entity with class " + entity.getJavaType().getName());

					try {
						Menu parent = (Menu) findByName.getSingleResult();
						menu.setParent(parent);
						logger.info("Menu found for entity with class " + entity.getSupertype().getJavaType().getName());
					} catch (NoResultException nreParent) {
						logger.info("Parent menu " + entity.getSupertype().getJavaType().getName()
								+ " for entity with class " + entity.getJavaType().getName() + " not found");
					}
				} catch (NullPointerException npe) {
					logger.error("Parent class for entity with class " + entity.getJavaType().getName() + " not found",
							npe);
				}
				menu = em.merge(menu);
				em.flush();
			}
		}
	}
}

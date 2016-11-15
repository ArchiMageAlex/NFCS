package nfcs.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import nfcs.model.core.Menu;

@Stateless
public class MenuEJB implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext//(type = PersistenceContextType.EXTENDED)
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
}

package nfcs.ejb;

import javax.ejb.*;
import javax.persistence.Query;

import nfcs.model.core.BaseEntity;

/**
 * Session Bean implementation class ColleagueEJB
 */
@Stateless 
public class SubjectEJB extends EJB {
	private static final long serialVersionUID = 1L;

	public BaseEntity findByName(String name) {
		Query query = em.createNamedQuery("findSubjectByName").setParameter("name",
				name);
		return (BaseEntity) query.getSingleResult();
	}
}

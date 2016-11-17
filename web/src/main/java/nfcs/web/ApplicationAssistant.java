package nfcs.web;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nfcs.model.Role;
import nfcs.model.User;
import nfcs.model.core.BaseEntity;
import nfcs.model.core.Menu;

@ManagedBean(eager = true)
@ApplicationScoped
public class ApplicationAssistant {
	private static Logger logger = LoggerFactory.getLogger(ApplicationAssistant.class);
	@javax.ejb.EJB(lookup = "java:app/ejb-1.0.0/EJB!nfcs.ejb.EJB")
	nfcs.ejb.EJB ejb;

	@PostConstruct
	public void setup() {
		logger.info("Start setup");
		boolean isAdminRoleExists = false;
		Role role = null;

		if (0 == ejb.getEntitiesOfClassByProperty(User.class, "name", "admin").size()) {
			logger.info(
					"Default admin role and user not found. Creating User(name=admin,password=admin,roles={name=admin}).");
			User user = new User();
			user.setName("admin");
			user.setPassword("admin");
			user.setRoles(new ArrayList<Role>());

			user = (User) ejb.update(user);
			
			for (BaseEntity entity : ejb.getEntitiesOfClassByProperty(Role.class, "name", "admin")) {
				role = (Role) entity;
				isAdminRoleExists = true;
				break;
			}

			if (!isAdminRoleExists) {
				logger.info("Default admin role not found. Creating Role(name=admin)");
				role = new Role();
				role.setName("admin");
				role.setUsers(new ArrayList<User>());
				ejb.update(role);
			}

			user.getRoles().add(role);
			ejb.update(user);
			role.getUsers().add(user);
			ejb.update(role);
		}
	}
}

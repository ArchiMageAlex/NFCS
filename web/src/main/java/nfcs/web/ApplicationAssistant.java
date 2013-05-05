package nfcs.web;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import nfcs.model.Role;
import nfcs.model.User;
import nfcs.model.core.BaseEntity;

@ManagedBean(eager = true)
@ApplicationScoped
public class ApplicationAssistant {
	@EJB
	nfcs.ejb.EJB ejb;

	@PostConstruct
	public void setup() {
		boolean isAdminUserExists = false;
		boolean isAdminRoleExists = false;
		Role role = null;

		for (BaseEntity entity : ejb.getEntitiesOfClassByProperty(User.class,
				"name", "admin")) {
			isAdminUserExists = true;
			break;
		}

		if (!isAdminUserExists) {
			System.out
					.println("Не создана учетная запись администратора по-умолчанию. Создается User(name=admin,password=admin,roles={name=admin}");
			User user = new User();
			user.setName("admin");
			user.setPassword("admin");
			user.setRoles(new ArrayList<Role>());

			for (BaseEntity entity : ejb.getEntitiesOfClassByProperty(
					Role.class, "name", "admin")) {
				role = (Role) entity;
				isAdminRoleExists = true;
				break;
			}

			if (!isAdminRoleExists) {
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

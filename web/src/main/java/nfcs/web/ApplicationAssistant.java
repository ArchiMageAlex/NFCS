package nfcs.web;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import nfcs.model.Role;
import nfcs.model.User;
import nfcs.model.core.BaseEntity;
import nfcs.model.core.Menu;

//@ManagedBean(eager = true)
@ApplicationScoped
public class ApplicationAssistant {
	@javax.ejb.EJB(lookup = "java:app/ejb-1.0.0/EJB!nfcs.ejb.EJB")
    nfcs.ejb.EJB ejb;

	@PostConstruct
	public void setup() {
		boolean isAdminUserExists = false;
		boolean isAdminRoleExists = false;
        boolean isDefaultMenuExists = false;
		Role role = null;

		for (BaseEntity entity : ejb.getEntitiesOfClassByProperty(User.class,
				"name", "admin")) {
			isAdminUserExists = true;
			break;
		}

        for (BaseEntity entity : ejb.getEntitiesOfClassByProperty(Menu.class,
                "name", "admin")) {
            isDefaultMenuExists = true;
            break;
        }

		if (!isAdminUserExists) {
			System.out
					.println("Default admin role and user not found. Creating User(name=admin,password=admin,roles={name=admin}).");
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

        if (!isDefaultMenuExists) {
            System.out
                    .println("Default menu not found. Creating default menu).");
            Menu menu = new Menu();
            //ejb.update(menu);
        }
    }
}

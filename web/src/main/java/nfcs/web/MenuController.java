package nfcs.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ExpressionFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import nfcs.ejb.MenuEJB;
import nfcs.model.core.Menu;

import org.primefaces.component.menuitem.UIMenuItem;
import org.primefaces.component.submenu.UISubmenu;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SessionScoped
@ManagedBean
public class MenuController implements Serializable {
	private static Logger logger = LoggerFactory.getLogger(MenuController.class);
	private static final long serialVersionUID = 1L;
	private MenuModel model;
	private UIMenuItem currentComponent;

	@EJB
	private MenuEJB menuEJB;

	@PostConstruct
	public void init() {
		menuEJB.initMenu();
		model = new DefaultMenuModel();
		DefaultSubMenu system = new DefaultSubMenu();
		system.setLabel("Параметры");
		DefaultMenuItem reset = new DefaultMenuItem();
		reset.setValue("Обновить");
		reset.setCommand("#{menuController.resetAction}");
		system.addElement(reset);		system.setExpanded(false);
		model.addElement(system);

		DefaultSubMenu entities = new DefaultSubMenu();
		entities.setLabel("Сущности");

		model.addElement(entities);

		for (Menu menu : menuEJB.getRootMenu()) {
			List<Menu> children = menuEJB.getChildren(menu);
			
			if (children.size() > 0) {
				DefaultSubMenu item = new DefaultSubMenu();
				item.setLabel(menu.getName());

				for (Menu child : children) {
					getNewMenuItem(item, child);
				}

				entities.addElement(item);
			} else {
				getNewMenuItem(entities, menu);
			}
		}
	}

	private void getNewMenuItem(DefaultSubMenu uimenu, Menu menu) {
		DefaultMenuItem childmenu = new DefaultMenuItem();
		childmenu.setCommand("#{menuController.menuAction}");
		childmenu.setValue(menu.getName());
		childmenu.setUrl("/secure/admin/crud.xhtml?faces-redirect=true&entityClassName=" + menu.getEntityName());
		uimenu.addElement(childmenu);
	}

	public MenuModel getMenuModel() {
		return model;
	}

	public String menuAction() {
		return currentComponent.getUrl();
	}

	public String resetAction() {
		init();
		return "/welcome.xhtml";
	}
}

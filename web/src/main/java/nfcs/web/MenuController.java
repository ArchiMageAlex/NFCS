package nfcs.web;

import java.io.Serializable;

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

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

@SessionScoped
@ManagedBean
public class MenuController implements Serializable {

	private class MenuActionListener implements ActionListener, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public void processAction(ActionEvent arg0)
				throws AbortProcessingException {
			currentComponent = (MenuItem) arg0.getSource();
		}
	}

	private static final long serialVersionUID = 1L;
	private MenuModel model;
	private MenuItem currentComponent;

	@EJB
	private MenuEJB menuEJB;

	@PostConstruct
	public void init() {
		model = new DefaultMenuModel();
		MenuItem reset = new MenuItem();
		reset.setValue("Обновить");
		reset.setActionExpression(ExpressionFactory.newInstance()
				.createMethodExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{menuController.resetAction}", String.class,
						new Class[0]));
		model.addMenuItem(reset);

		for (Menu menu : menuEJB.getRootMenu()) {
			Submenu item = new Submenu();
			item.setLabel(menu.getName());

			for (Menu child : menuEJB.getChildren(menu)) {
				item.getChildren().add(fillChildren(child));
			}

			model.addSubmenu(item);
		}
	}

	private MenuItem fillChildren(Menu menuentity) {
		MenuItem item = newMenuItem(menuentity.getName(),
				"/secure/admin/crud.xhtml?faces-redirect=true&entityClassName="
						+ menuentity.getEntityName());

		for (Menu child : menuEJB.getChildren(menuentity)) {
			item.getChildren().add(fillChildren(child));
		}

		return item;
	}

	private MenuItem newMenuItem(String name, String url) {
		MenuItem item = new MenuItem();
		item.setId(name);
		item.setValue(name);
		item.setUrl(url);
		item.setActionExpression(ExpressionFactory.newInstance()
				.createMethodExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{menuController.menuAction}", String.class,
						new Class[0]));
		item.addActionListener(new MenuActionListener());
		return item;
	}

	public MenuModel getMenuModel() {
		return model;
	}

	public String menuAction() {
		return currentComponent.getUrl();
	}

	public String resetAction() {
		init();
		return "/";
	}
}

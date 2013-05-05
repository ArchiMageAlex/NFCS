package nfcs.web;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import nfcs.ejb.SubjectEJB;
import nfcs.model.Colleague;
import nfcs.model.User;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1l;
	private String login;
	private String password;
	private Colleague user;
	private boolean loggedIn = false;

	public Colleague getUser() {
		return user;
	}

	public void setUser(Colleague user) {
		this.user = user;
	}

	@EJB(name = "SubjectEJB")
	private SubjectEJB colleagueEJB;

	@EJB(name = "EJB")
	private nfcs.ejb.EJB userEJB;

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	protected String getEncryptedPassword(String password) {
		String encPassword;
		String alg = "SHA-256";
		String charsetName = "UTF-8";

		if (password != null) {
			try {
				MessageDigest md = MessageDigest.getInstance(alg);
				md.update(password.getBytes(charsetName));

				encPassword = new String(md.digest());
				return encPassword;
			} catch (NoSuchAlgorithmException e) {
				FacesContext.getCurrentInstance().addMessage(
						"Ошибка расшифровки",
						new FacesMessage("Не найдена реализация алгоритма "
								+ alg));
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				FacesContext.getCurrentInstance().addMessage(
						"Ошибка кодировки",
						new FacesMessage("Кодировка " + charsetName
								+ " не поддерживается"));
				e.printStackTrace();
				e.printStackTrace();
			}
		}
		return null;
	}

	public void setPassword(String password) {
		this.password = password;// getEncryptedPassword(password);
	}

	public String validate() throws ServletException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();

		try {
			if (request.getUserPrincipal() == null) {
				request.login(this.login, this.password);
			}

			Principal p = request.getUserPrincipal();

			if (this.getUser() == null) {
				try {
					this.user = (Colleague) colleagueEJB
							.findByName(p.getName());
				} catch (Exception ex) {
					ex.printStackTrace();
					Colleague colleague = new Colleague();
					colleague.setName(this.login);
					this.user = (Colleague) colleagueEJB.create(colleague);
				}
			}
			this.setLoggedIn(true);
			String redirect = request.getRequestURI();
			return redirect;// "/welcome";//
		} catch (ServletException e) {
			User useruser = (User) userEJB.findById(User.class, 1L);

			if (useruser != null) {
				useruser.setName(this.login);
				useruser.setPassword(this.getPassword());
				userEJB.create(useruser);
				this.setLoggedIn(true);
				String redirect = ((HttpServletRequest) context
						.getExternalContext().getRequest()).getRequestURI();
				return redirect;// "/welcome";//
			} else {
				this.setLoggedIn(false);
				return "error?faces-redirect=true";
			}
		}
	}

	public boolean validate(String username, String password)
			throws ServletException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();

		try {
			if (request.getUserPrincipal() == null) {
				request.login(this.login, this.password);
			}

			Principal p = request.getUserPrincipal();

			if (this.getUser() == null) {
				try {
					this.user = (Colleague) colleagueEJB
							.findByName(p.getName());
				} catch (Exception ex) {
					ex.printStackTrace();
					Colleague colleague = new Colleague();
					colleague.setName(this.login);
					this.user = (Colleague) colleagueEJB.create(colleague);
				}
			}
			this.setLoggedIn(true);
			return true;
		} catch (ServletException e) {
			User useruser = (User) userEJB.findById(User.class, 1L);

			if (useruser != null) {
				useruser.setName(this.login);
				useruser.setPassword(this.getPassword());
				userEJB.create(useruser);
				this.setLoggedIn(true);
				return true;
			} else {
				this.setLoggedIn(false);
				return false;
			}
		}
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void logout() {
		this.setLoggedIn(false);
		this.setUser(null);
		this.setPassword(null);
		this.setLogin(null);
	}
}

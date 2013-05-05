package nfcs.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import nfcs.ejb.EJB;
import nfcs.model.core.BaseEntity;

@FacesConverter(forClass = BaseEntity.class)
public class BaseConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value.trim().equals("")) {
			return null;
		} else {
			try {
				Long id = Long.parseLong(value);
				EJB ejb = (EJB) InitialContext
						.doLookup("java:app/ejb-1.0.0/EJB");
				BaseEntity c = (BaseEntity) ejb.findById(BaseEntity.class, id);
				return c;
			} catch (NumberFormatException exception) {
				System.out
						.println("Conversion error. Not a valid Colleague id:"
								+ value);
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Conversion Error",
						"Not a valid Colleague"));
			} catch (NamingException e) {
				System.out.println("JNDI lookup error. Colleague not found:"
						+ value);
				e.printStackTrace();
				throw new ConverterException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "JNDI lookup error",
						"Colleague not found:" + value));
			}
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null)
			return "";
		else {
			if (value instanceof BaseEntity) {
				if (((BaseEntity) value).getId() == null) {
					return "";
				} else {
					return String.valueOf(((BaseEntity) value).getId()
							.toString());
				}
			} else {
				if (value instanceof Long) {
					return value.toString();
				} else {
					return "";
				}
			}
		}
	}
}

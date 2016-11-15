package nfcs.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import nfcs.ejb.SubjectEJB;
import nfcs.model.Colleague;

@FacesConverter(forClass = Colleague.class)
public class ColleagueConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value.trim().equals("")) {
			return null;
		} else {
			try {
				Long id = Long.parseLong(value);
				SubjectEJB colleagueEJB = (SubjectEJB) InitialContext
						.doLookup("java:app/ejb-1.0.0/SubjectEJB");
				Colleague c = (Colleague) colleagueEJB.findById(
						Colleague.class, id);
				System.out.println("Colleague " + c.getName()
						+ " selected and found!");
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
			if (value instanceof Colleague) {
				if (((Colleague) value).getId() == null) {
					return "";
				} else {
					return String.valueOf(((Colleague) value).getId()
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

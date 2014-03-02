package nfcs.web.cc;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@FacesComponent(value="loginComponent") 
public class LoginComponent extends UINamingContainer { 
        
     private UIInput username; 
     private UIInput password; 
        
     private String action; 
        
     public LoginComponent() { 
          super(); 
     } 

     public void actionListener(ActionEvent ae) { 
             
          FacesContext context = FacesContext.getCurrentInstance(); 
          ELContext elContext = context.getELContext(); 
   
          Object params[] = new Object[2]; 
          params[0] = username.getValue(); 
          params[1] = password.getValue(); 
             
          MethodExpression me = (MethodExpression)this.getAttributes().get("login"); 
          boolean loginSuccess = (Boolean)me.invoke(elContext, params); 
             
          if(loginSuccess) { 
               action = (String)getAttributes().get("success"); 
          } else { 
               action = (String)getAttributes().get("failure"); 
          } 
     } 
        
     public String action() { 
          return action; 
     } 
        
     public UIInput getUsername() { 
          return username; 
     } 
   
     public void setUsername(UIInput username) { 
          this.username = username; 
     } 
   
     public UIInput getPassword() { 
          return password; 
     } 
   
     public void setPassword(UIInput password) { 
          this.password = password; 
     }           
}  
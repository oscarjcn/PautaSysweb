package br.mpeg.drosofila.visao.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
@FacesValidator(value="emailValidator")
public class EmailValidator implements Validator{

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		String enteredEmail = (String)object;
	    Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
	    Matcher m = p.matcher(enteredEmail);

	    boolean matchFound = m.matches();

	    if (!matchFound) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("E-mail incorreto!");
	        message.setSummary("E-mail incorreto!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }
		
	}

}

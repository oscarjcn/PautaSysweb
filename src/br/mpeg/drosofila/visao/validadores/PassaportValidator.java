package br.mpeg.drosofila.visao.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import br.mpeg.drosofila.visao.ParticipanteBeans;
@FacesValidator(value="br.museugoeldi.valida.passaport")
public class PassaportValidator implements Validator{

	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		ParticipanteBeans participanteBeans 
		= (ParticipanteBeans)((HttpSession)arg0.getExternalContext().getSession(true)).getAttribute("participanteBeans");
		
		String enteredEmail = (String)object;
	    Pattern p = Pattern.compile("[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]+");
	    Matcher m = p.matcher(enteredEmail);

	    boolean matchFound = m.matches();

	    if (!matchFound && (participanteBeans.getCpf()== null || participanteBeans.getCpf().trim().equals(""))) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("Passaport incorreto!");
	        message.setSummary("Passaport incorreto!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }
		
	}
}

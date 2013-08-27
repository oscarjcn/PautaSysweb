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
@FacesValidator(value="br.museugoeldi.valida.RG.CPF")
public class RgCpfValidator implements Validator{

	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		ParticipanteBeans participanteBeans 
		= (ParticipanteBeans)((HttpSession)arg0.getExternalContext().getSession(true)).getAttribute("participanteBeans");
		
		String enteredEmail = (String)object;
	    Pattern p = Pattern.compile("[0-9][0-9][0-9][0-9]+");
	    Matcher m = p.matcher(enteredEmail);

	    boolean matchFound = m.matches();

	    if (!matchFound && (participanteBeans.getPassaporte()== null || participanteBeans.getPassaporte().trim().equals(""))) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail(arg1.getClientId()+" incorreto!");
	        message.setSummary("Insira apenas os numeros, sem pontos ou traços");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }
		
	}
}

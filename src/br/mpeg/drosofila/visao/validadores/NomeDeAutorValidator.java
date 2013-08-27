package br.mpeg.drosofila.visao.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
@FacesValidator(value="br.museugoeldi.valida.nome.autor")
public class NomeDeAutorValidator implements Validator{

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		String nomeAutorResumo = (String)object;
		nomeAutorResumo = nomeAutorResumo.replaceAll(" ", "").replaceFirst(",", ", ").trim();
	    Pattern p = Pattern.compile("[a-zA-Z]+([\\-]?[a-zA-Z]+)+, [A-Z]*");
	    Matcher m = p.matcher(nomeAutorResumo);

	    boolean matchFound = m.matches();

	    if (!matchFound) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("Por favor, coloque o sobrenome seguido de vírgula e iniciais sem ponto ex: SILVA, DT");
	        message.setSummary("Formato nome do autor não permitido");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }
	}
	
	public static void main(String[] args) {
		System.out.println("oaijfad , asda ".replaceAll(" ", "").replaceFirst(",", ", "));
	}

}

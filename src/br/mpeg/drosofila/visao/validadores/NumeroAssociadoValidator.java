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

import org.jsoup.Jsoup;

import br.mpeg.drosofila.controle.AssociadoControle;
import br.mpeg.drosofila.controle.api.IAssociadoControle;
import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.visao.AdmAssociadoBean;

@FacesValidator("br.museugoeldi.valida.associado.numero")
public class NumeroAssociadoValidator implements Validator{


	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		int numeroAssociado = (Integer)object;
	    
		
		
		if (numeroAssociado==0) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("Número deve ser diferente de 0.");
	        message.setSummary("Número não pode ser 0 (zero)!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }	
		
		AdmAssociadoBean associadoBean = (AdmAssociadoBean) ((HttpSession)arg0.getExternalContext().getSession(true)).getAttribute("admAssociadoBean");
		IAssociadoControle associadoControle;
		associadoControle = new AssociadoControle();
		
		Associado associado = associadoControle.buscaPorNumero(numeroAssociado);
		Associado associadoAdministracao = associadoBean.getAssociado();
		
	    if (associado != null && !(associadoAdministracao!=null && associadoAdministracao.getCpf()!=null 
	    		&& associadoAdministracao.getNumero() ==numeroAssociado)) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("Número do associado já está cadastrado.");
	        message.setSummary("Número já cadastrado!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }	
	
	}	
	
}

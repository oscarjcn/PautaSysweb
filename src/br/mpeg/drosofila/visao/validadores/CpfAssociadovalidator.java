package br.mpeg.drosofila.visao.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpSession;

import br.mpeg.drosofila.controle.AssociadoControle;
import br.mpeg.drosofila.controle.api.IAssociadoControle;
import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.visao.AdmAssociadoBean;

@FacesValidator("br.museugoeldi.valida.associado.cpf")
public class CpfAssociadovalidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		
		HtmlInputText campo = (HtmlInputText) arg1;
		String cpf = object.toString();
		
		if (cpf.trim().equals("")) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("CPF obrigatório.");
	        message.setSummary("CPF obrigatório!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }	
		
		AdmAssociadoBean associadoBean = (AdmAssociadoBean) ((HttpSession)arg0.getExternalContext().getSession(true)).getAttribute("admAssociadoBean");
		IAssociadoControle associadoControle;
		associadoControle = new AssociadoControle();
		Associado associado = null;
		try{
			associado = associadoControle.buscaPorCPF(cpf);
		}catch (javax.persistence.NonUniqueResultException e) {
			e.printStackTrace();
		}catch (org.hibernate.NonUniqueResultException e ) {
			e.printStackTrace();
		}
		
		Associado associadoAdministracao = associadoBean.getAssociado();
		
		
	    if (associado != null && !(associadoAdministracao!=null && associadoAdministracao.getCpf()!=null && associadoAdministracao.getCpf().trim().equals(cpf))) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("CPF do associado já está cadastrado.");
	        message.setSummary("CPF já cadastrado!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }
	    
	    
	}

}

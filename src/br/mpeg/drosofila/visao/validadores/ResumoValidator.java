package br.mpeg.drosofila.visao.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.jsoup.Jsoup;

@FacesValidator("br.museugoeldi.valida.resumo")
public class ResumoValidator implements Validator{

	public static final int TAMANHO_MAXIMO_CONTEUDO_RESUMO = 1600;
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		String conteudoResumoHtml = (String)object;
	    
		String conteudoResumo = Jsoup.parse(conteudoResumoHtml).text();
		System.out.println(conteudoResumo.length());
	    if (conteudoResumo.length()>TAMANHO_MAXIMO_CONTEUDO_RESUMO) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("O resumo está muito grande (contém "+conteudoResumo.length()+" caracteres), deve ter no maximo "
	        		+TAMANHO_MAXIMO_CONTEUDO_RESUMO+" caracteres");
	        message.setSummary("Resumo muito grande!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }	
	
	    if (conteudoResumo.trim().length()==0) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("Conteudo do resumo não pode estar vasio");
	        message.setSummary("Resum obrigatório!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }	
	}	
	
}

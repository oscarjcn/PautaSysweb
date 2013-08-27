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

@FacesValidator("br.museugoeldi.valida.campos.resumo")
public class CamposResumoValidator implements Validator{

	public static final int TAMANHO_MAXIMO_CONTEUDO_RESUMO = 400;
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		String conteudoResumoHtml = (String)object;
		
		String nomeCampo = "";
		if(arg1.getId().trim().equals("tituloresumo"))
			nomeCampo = "Título";
		if(arg1.getId().trim().equals("demaisautores"))
			nomeCampo = "Autor(es)";
		if(arg1.getId().trim().equals("resumoEmails"))
			nomeCampo = "Instituições/Email";
		
		String conteudoResumo = Jsoup.parse(conteudoResumoHtml).text();
		System.out.println("Campo: "+nomeCampo+" - Quantidade de caracteres"+conteudoResumo.length());
	    if (conteudoResumo.length()>TAMANHO_MAXIMO_CONTEUDO_RESUMO) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("O resumo está muito grande (contém "+conteudoResumo.length()+" caracteres), deve ter no maximo "
	        		+TAMANHO_MAXIMO_CONTEUDO_RESUMO+" caracteres");
	        message.setSummary("'"+nomeCampo+"' Tem mais que 400 caracteres!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }	
	
	    if (conteudoResumo.trim().length()==0) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("Conteudo do campo '"+nomeCampo+"' não pode estar vasio");
	        message.setSummary("'"+nomeCampo+"' obrigatório!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }	
	}	
	
}

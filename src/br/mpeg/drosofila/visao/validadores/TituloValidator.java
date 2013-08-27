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

@FacesValidator("br.museugoeldi.valida.titulo")
public class TituloValidator implements Validator{

	public static final int TAMANHO_MAXIMO_CONTEUDO_TITULO = 400;
	public static final int TAMANHO_MINIMO_CONTEUDO_TITULO = 3;
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		String conteudoTituloHtml = (String)object;
	    
		String conteudoTitulo = Jsoup.parse(conteudoTituloHtml).text();
		System.out.println(conteudoTitulo.length());
	    if (conteudoTitulo.length()>TAMANHO_MAXIMO_CONTEUDO_TITULO) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("O Título está muito grande (contém "+conteudoTitulo.length()+" caracteres), deve ter no maximo "
	        		+TAMANHO_MAXIMO_CONTEUDO_TITULO+" caracteres");
	        message.setSummary("Título muito grande!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }	
	
	    if (conteudoTitulo.trim().length()==0) {
	        FacesMessage message = new FacesMessage();
	        message.setDetail("Conteudo do título não pode estar vasio");
	        message.setSummary("título obrigatório!");
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        throw new ValidatorException(message);
	    }	
	}	
	
}

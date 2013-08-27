package br.mpeg.drosofila.util.visao;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class MensagemJSF {

	public static void exibeMensagemErro(String informacao, String descricao){
		exibeMensagem(informacao, descricao, FacesMessage.SEVERITY_ERROR);
	}
	
	
	public static void exibeMensagemInfo(String informacao, String descricao){
		exibeMensagem(informacao, descricao, FacesMessage.SEVERITY_INFO);
	}
	
	public static void exibeMensagemFatal(String informacao, String descricao){
		exibeMensagem(informacao, descricao, FacesMessage.SEVERITY_FATAL);
	}
	
	public static void exibeMensagemAlerta(String informacao, String descricao){
		exibeMensagem(informacao, descricao, FacesMessage.SEVERITY_WARN);
	}
	
	public static void exibeMensagem(String informacao, String descricao, Severity gravidade){
		FacesMessage fm = new FacesMessage();
		fm.setDetail(informacao);
		fm.setSummary(descricao);
		fm.setSeverity(gravidade);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
	
	
}

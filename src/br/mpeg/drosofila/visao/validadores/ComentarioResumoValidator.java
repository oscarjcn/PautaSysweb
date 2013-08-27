package br.mpeg.drosofila.visao.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.visao.MensagemJSF;
import br.mpeg.drosofila.visao.AdministracaoBeans;

@FacesValidator("br.museugoeldi.valida.comentario.resumo")
public class ComentarioResumoValidator implements Validator{

	public static final int TAMANHO_MAXIMO_CONTEUDO_COMENTARIO_RESUMO = 10;
	
	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object object)
			throws ValidatorException {
		String comentarioResumo = (String)object;
	    AdministracaoBeans administracaoBeans = (AdministracaoBeans) Contexto.getSessao().getAttribute("administracaoBeans");
		System.out.println("########### chamou validador ##############");
	    if( comentarioResumo.trim().length()<TAMANHO_MAXIMO_CONTEUDO_COMENTARIO_RESUMO){
	    	String titulo = "Por faovr insira uma justificativa para reprovação do resumo e deve possuir no minimo 10 caracteres.";
			String detalhes = "Justificativa de reprovação é obrigatória."; 
	    	MensagemJSF.exibeMensagemErro(
	    			"Por faovr insira uma justificativa para reprovação do resumo e deve possuir no minimo 10 caracteres."
	    			, "Justificativa de reprovação é obrigatória.");
			 System.out.println("deveria chamar erro");
			  throw new ValidatorException(new FacesMessage("summary", "detail"));
		 }
	}

}

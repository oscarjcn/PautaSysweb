package br.mpeg.drosofila.util.visao;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.mpeg.drosofila.util.controle.Contexto;

public class NavegacaoJSF {

	
	public static void navegaPaginas(String outCome){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outCome);
	}
	
	public static void redirecionar(String pagina){
	    try {
			Contexto.getResponse().sendRedirect(pagina);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void redirecionarPorExternalContext(String url){	
		FacesContext faces = FacesContext.getCurrentInstance();  
		ExternalContext context = faces.getExternalContext();  
		try {
			context.redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

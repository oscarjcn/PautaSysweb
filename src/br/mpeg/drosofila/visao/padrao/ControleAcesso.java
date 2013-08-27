package br.mpeg.drosofila.visao.padrao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;









import br.mpeg.drosofila.controle.PapelControle;
import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.controle.Contexto;

public class ControleAcesso  implements PhaseListener{

	FacesContext facesContext;  
	
	@Override
	public void afterPhase(PhaseEvent event) {
		
		//if(facesContext==null)
		facesContext = event.getFacesContext();  
		
		String uri =  ((HttpServletRequest) facesContext.getExternalContext()  
                .getRequest()).getRequestURI();
		
		Boolean ehAreaAdministrativa = uri.indexOf("administracao") >-1;

		System.out.println("uri: "+uri);
		PapelControle papelControle = new PapelControle();
		Usuario usuario = Contexto.getUsuario();
		
		if(usuario!=null && ehAreaAdministrativa){
			List<Papel> papeis =  papelControle.buscaPapeisUsuario(usuario.getId());
			System.out.println("#### Quantidade de papeis: "+papeis.size());
			if(!podeAcessarAreaAdministrativa(papeis)){
				facesContext.getApplication().getNavigationHandler()
					.handleNavigation(facesContext, null, "acessonegado");
			}
		}
	}
	
	private String verificaPrivilegiosAcesso(String papel) throws Exception{
		String privilegios = null;
		String caminho 
		= ((ServletContext) facesContext.getExternalContext().getContext()).getRealPath("/resources");
		Properties p = new Properties();  
		//System.out.println("caminho: "+caminho);
		try {
			p.load( new FileInputStream(new File(caminho+"/definicoesAcesso.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}  
		
		privilegios = (String) p.get(papel.toLowerCase());
		if(privilegios==null)
			throw new Exception("Papel "+papel+" não foi definido no banco no arquivo: "+caminho);
		return privilegios;
	}
	
	private boolean podeAcessarAreaAdministrativa(List<Papel>papeis){
		String uri = ((HttpServletRequest) facesContext.getExternalContext()  
                .getRequest()).getRequestURI();
		//System.out.println("\n\n Quantidade de papeis "+papeis.size());
		
		for(Papel papel: papeis){
			String definicoesAcesso[] ;
			try {
				definicoesAcesso = dividirPrivilegio(verificaPrivilegiosAcesso(papel.getNome()));
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			System.out.println("#### Nome do papel: "+papel.getNome());
			for(int i=0; i< definicoesAcesso.length; i++){
				System.out.println("URL: "+uri+" -  "+definicoesAcesso[i]);
				if(uri.indexOf(definicoesAcesso[i].trim())>-1){
					return true;
				}
			}
		}
		
		return false;
	}
	
	private String[] dividirPrivilegio(String papeis) throws Exception{
		
		String[] arrayPapeis = new String[1];
		System.out.println(papeis);
		if(papeis.indexOf(",")>-1){
			return papeis.split(",");
		}
		
		arrayPapeis[0] = papeis;
		return arrayPapeis;
	}
	

	@Override
	public void beforePhase(PhaseEvent arg0) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}

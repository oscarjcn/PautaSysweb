package br.mpeg.drosofila.visao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.modelo.Usuario;
@FacesConverter(forClass=Usuario.class)
public class PapelConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if(id.trim().equals("")){
			Usuario usuario = new Usuario();
			usuario.setNome("Nenum usuario selecionado");
			return usuario;
		}
			
		UsuarioControle ec = new UsuarioControle();
		return ec.findById(Integer.parseInt(id));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
		if(objeto==null || !(objeto instanceof Usuario) )
			return "";
		
		Usuario e=null;
		try{
			e = (Usuario) objeto;
		}catch (java.lang.ClassCastException ex) {
			return "";	
		}
		return e.getId().toString();
	}

}

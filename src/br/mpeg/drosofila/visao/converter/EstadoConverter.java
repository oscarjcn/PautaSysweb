package br.mpeg.drosofila.visao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mpeg.drosofila.controle.EstadoControle;
import br.mpeg.drosofila.modelo.Estado;
@FacesConverter(forClass=Estado.class)
public class EstadoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if(id.trim().equals("")){
			Estado estado = new Estado();
			estado.setNome("Nenum estado selecionado");
			return estado;
		}
			
		EstadoControle ec = new EstadoControle();
		return ec.findById(Integer.parseInt(id));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
		if(objeto==null || !(objeto instanceof Estado) )
			return "";
		
		Estado e=null;
		try{
			e = (Estado) objeto;
		}catch (java.lang.ClassCastException ex) {
			return "";	
		}
		return e.getId().toString();
	}

}

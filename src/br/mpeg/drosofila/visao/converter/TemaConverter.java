package br.mpeg.drosofila.visao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mpeg.drosofila.controle.TemaControle;
import br.mpeg.drosofila.modelo.Tema;
@FacesConverter(forClass=Tema.class)
public class TemaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if(id.trim().equals("")){
			Tema Tema = new Tema();
			Tema.setNome("Nenum Tema selecionado");
			return Tema;
		}
			
		TemaControle ec = new TemaControle();
		return ec.findById(Integer.parseInt(id));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
		if(objeto==null || !(objeto instanceof Tema) )
			return "";
		
		Tema e=null;
		try{
			e = (Tema) objeto;
		}catch (java.lang.ClassCastException ex) {
			return "";	
		}
		return e.getId().toString();
	}

}

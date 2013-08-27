package br.mpeg.drosofila.visao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mpeg.drosofila.controle.ResumoControle;
import br.mpeg.drosofila.modelo.Resumo;
@FacesConverter(forClass=Resumo.class)
public class ResumoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if(id.trim().equals("")){
			Resumo estado = new Resumo();
			return estado;
		}
			
		ResumoControle ec = new ResumoControle();
		return ec.findById(Integer.parseInt(id));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
		if(objeto==null || !(objeto instanceof Resumo) )
			return "";
		
		Resumo e=null;
		try{
			e = (Resumo) objeto;
		}catch (java.lang.ClassCastException ex) {
			return "";	
		}
		return e.getId().toString();
	}

}

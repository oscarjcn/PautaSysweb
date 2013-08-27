package br.mpeg.drosofila.visao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mpeg.drosofila.controle.MunicipioControle;
import br.mpeg.drosofila.modelo.Municipio;
@FacesConverter(forClass=Municipio.class, value="municipioConverter")
public class MunicipioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		//return new MunicipioControle().findById(Integer.parseInt(arg2));
		
		if(arg2.trim().equals(""))
			return null;
		
		
		MunicipioControle mc = new MunicipioControle();
		Municipio municipio = (Municipio) mc.findById(Integer.parseInt(arg2));
		return municipio;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
		
		if(objeto==null )
			return "";
		
		if(!(objeto instanceof Municipio) || objeto instanceof String)
			return objeto.toString();
		
		
		Municipio m = null; 
			try{
			m = (Municipio) objeto;
			}catch(java.lang.ClassCastException ex){
				return "";	
			}
		return m.getId().toString();
	}

	

}

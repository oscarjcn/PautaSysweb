package br.mpeg.drosofila.visao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mpeg.drosofila.controle.IndicadorControle;
import br.mpeg.drosofila.modelo.Indicador;


@FacesConverter(forClass = Indicador.class)
public class IndicadorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		System.out.println("as Object"+arg2);
		if(arg2.trim().equals(""))
			return null;
		
		return new IndicadorControle().findById(Integer.parseInt(arg2));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		System.out.println("AS String"+arg2.toString());
		
		if(arg2==null )
			return "";
		
		if(!(arg2 instanceof Indicador) || arg2 instanceof String)
			return arg2.toString();
		
		
		Indicador indicador = null; 
			try{
				indicador = (Indicador) arg2;
			}catch(java.lang.ClassCastException ex){
				return "";	
			}
		return indicador.getId().toString();

	}

}

package br.mpeg.drosofila.visao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mpeg.drosofila.controle.InstituicaoControle;
import br.mpeg.drosofila.modelo.Instituicao;

@FacesConverter(forClass = Instituicao.class)
public class InstituicaoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			return new InstituicaoControle().findById(Integer.parseInt(arg2));
		} catch (NullPointerException e) {
			return new Instituicao();
		} catch (NumberFormatException e) {
			return null;
		}

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Instituicao i = (Instituicao) arg2;
		try{
		return i.getId().toString();
		}catch (Exception e) {
			return "";
		}
	}

}

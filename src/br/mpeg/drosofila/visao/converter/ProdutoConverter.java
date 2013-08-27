package br.mpeg.drosofila.visao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.mpeg.drosofila.controle.ProdutoControle;
import br.mpeg.drosofila.modelo.Produto;

@FacesConverter(forClass=Produto.class)
public class ProdutoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return new ProdutoControle().findById(Integer.parseInt(arg2));
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		Produto p = (Produto) arg2;
		return p.getId().toString();
	}

}

package br.mpeg.drosofila.visao.padrao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Home{
private String titulo;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public String getTitulo() {
		setTitulo("Cadastro de Briofita");
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}

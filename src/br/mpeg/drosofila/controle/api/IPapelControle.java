package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.controle.IFacade;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public interface IPapelControle extends IFacade<Papel> {

	public Papel save(Papel obj);

	public List<Papel> update(Papel obj);

	public List<Papel> delete(Papel obj);

	public List<Papel> findAll();

	public Papel findById(Integer id);

	public Papel instaceObject();

	public List<Usuario> buscaUsuarios(String nomePapel);

}
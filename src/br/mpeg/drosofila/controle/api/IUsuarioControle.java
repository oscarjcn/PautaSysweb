package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.controle.IFacade;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public interface IUsuarioControle extends IFacade<Usuario> {

	public Usuario save(Usuario obj);

	public List<Usuario> update(Usuario obj);

	public List<Usuario> delete(Usuario obj);

	public List<Usuario> findAll();

	public Usuario findById(Integer id);

	public Usuario findByName(String nome);
	
	public List<Usuario> listaPorPapel(String papel);

	public Usuario instaceObject();

	public Usuario logar(String login, String senha);
	
	public List<Usuario> listaPorPapelRevisor();
	
	public void limpaCacheBanco();
	
	public String criaNome(String nome);
	
	public void criaArquivo(byte[] bytes, String arquivo);
	

}
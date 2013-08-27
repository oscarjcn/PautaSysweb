package br.mpeg.drosofila.controle;

import java.io.Serializable;
import java.util.List;

import br.mpeg.drosofila.controle.api.IPapelControle;
import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.persistencia.PapelDao;
import br.mpeg.drosofila.persistencia.api.IPapelDao;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public class PapelControle implements IPapelControle, Serializable {

	private static final long serialVersionUID = 1L;
	private final IPapelDao dao = new PapelDao();

	public Papel save(Papel Papel) {
		dao.salvar(Papel);
		return null;
	}

	@SuppressWarnings("unused")
	public List<Papel> update(Papel Papel) {
		dao.alterar(Papel);
		return dao.listaTudo();
	}

	public List<Papel> delete(Papel Papel) {
		new PapelDao().excluir(Papel);
		return dao.listaTudo();
	}

	public Papel findById(Integer id) {
		return dao.findById(id);
	}

	public Papel findByName(String nome) {
		return dao.findByName(nome);
	}
	
	public List<Papel> listaAdministradores(){
		return dao.listaPorTipo("A");
	}
	
	public List<Papel> listaRevisores(){
		return dao.listaPorTipo("R");
	}
	
	public List<Papel> buscaPapeisUsuario(int usuarioID){
		return dao.buscaPapelPorUsuario(usuarioID);
	}
	public List<Usuario> buscaUsuarios(String nomePapel){
		return dao.buscaUsuarios(nomePapel);
	}
	
	public Papel instaceObject() {
		Papel Papel = new Papel();
		return Papel;
	}

	public List<Papel> findAll() {
		return dao.listaTudo();
	}

	public Papel findPapelByPedido() {
		return null;
	}
	
	
}

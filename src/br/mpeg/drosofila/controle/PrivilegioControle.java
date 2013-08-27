package br.mpeg.drosofila.controle;

import java.io.Serializable;
import java.util.List;

import br.mpeg.drosofila.controle.api.IPrivilegioControle;
import br.mpeg.drosofila.modelo.Privilegio;
import br.mpeg.drosofila.persistencia.PrivilegioDao;
import br.mpeg.drosofila.persistencia.api.IPrivilegioDao;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public class PrivilegioControle implements IPrivilegioControle, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IPrivilegioDao dao = new PrivilegioDao();

	public Privilegio save(Privilegio Privilegio) {

		dao.salvar(Privilegio);
		
		return null;
	}

	@SuppressWarnings("unused")
	public List<Privilegio> update(Privilegio Privilegio) {
		dao.alterar(Privilegio);
		return dao.listaTudo();
	}

	public List<Privilegio> delete(Privilegio Privilegio) {
		new PrivilegioDao().excluir(Privilegio);
		return dao.listaTudo();
	}

	public Privilegio findById(Integer id) {
		return dao.findById(id);
	}

	public Privilegio findByName(String nome) {
		return null;
	}

	public Privilegio instaceObject() {
		Privilegio Privilegio = new Privilegio();
		return Privilegio;
	}


	public List<Privilegio> findAll() {
		return dao.listaTudo();
	}

	public Privilegio findPrivilegioByPedido() {
		
		return null;
	}


	
}

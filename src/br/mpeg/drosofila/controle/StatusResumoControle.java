package br.mpeg.drosofila.controle;

import java.io.Serializable;
import java.util.List;

import br.mpeg.drosofila.controle.api.IStatusResumoControle;
import br.mpeg.drosofila.modelo.StatusResumo;
import br.mpeg.drosofila.persistencia.StatusResumoDao;
import br.mpeg.drosofila.persistencia.api.IStatusResumoDao;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public class StatusResumoControle implements IStatusResumoControle, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IStatusResumoDao dao = new StatusResumoDao();

	public StatusResumo save(StatusResumo StatusResumo) {
		dao.salvar(StatusResumo);
		return null;
	}

	@SuppressWarnings("unused")
	public List<StatusResumo> update(StatusResumo StatusResumo) {
		dao.alterar(StatusResumo);
		return dao.listaTudo();
	}

	public List<StatusResumo> delete(StatusResumo StatusResumo) {
		new StatusResumoDao().excluir(StatusResumo);
		return dao.listaTudo();
	}

	public StatusResumo findById(Integer id) {
		return dao.findById(id);
	}

	public StatusResumo findByName(String nome) {
		return null;
	}

	public StatusResumo instaceObject() {
		StatusResumo StatusResumo = new StatusResumo();
		return StatusResumo;
	}

	public StatusResumo buscaStatusResumo(String nome){
		return dao.buscaStatusResumo(nome);
	}

	public List<StatusResumo> findAll() {
		return dao.listaTudo();
	}

	public StatusResumo findStatusResumoByPedido() {
		return null;
	}


	
}

package br.mpeg.drosofila.controle;

import java.io.Serializable;
import java.util.List;
import br.mpeg.drosofila.controle.api.IParametrosControle;
import br.mpeg.drosofila.modelo.Parametros;
import br.mpeg.drosofila.persistencia.ParametrosDao;
import br.mpeg.drosofila.persistencia.api.IParametrosDao;

/**
 * @author Victor Coutinho
 */
public class ParametrosControle implements IParametrosControle, Serializable {

	private static final long serialVersionUID = 1L;
	private final IParametrosDao dao = new ParametrosDao();

	public Parametros save(Parametros Parametros){
		dao.salvar(Parametros);
		return null;
	}

	@SuppressWarnings("unused")
	public List<Parametros> update(Parametros Parametros){
		dao.alterar(Parametros);
		return dao.listaTudo();
	}

	public List<Parametros> delete(Parametros Parametros){
		new ParametrosDao().excluir(Parametros);
		return dao.listaTudo();
	}

	public Parametros findById(Integer id){
		return dao.findById(id);
	}

	public Parametros findByName(String nome){
		return null;
	}

	public Parametros instaceObject(){
		Parametros Parametros = new Parametros();
		return Parametros;
	}

	public List<Parametros> findAll(){
		return dao.listaTudo();
	}

	public Parametros findParametrosByPedido(){
		return null;
	}
}

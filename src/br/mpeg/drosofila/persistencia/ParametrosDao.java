package br.mpeg.drosofila.persistencia;

import java.util.List;

import br.mpeg.drosofila.modelo.Parametros;
import br.mpeg.drosofila.persistencia.api.IParametrosDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class ParametrosDao extends GenericDao<Parametros> implements IParametrosDao {

	@Override
	public void salvar(Parametros entity) {
		super.save(entity);

	}

	@Override
	public void alterar(Parametros entity) {
		super.update(entity);

	}

	@Override
	public void excluir(Parametros entity) {
		super.delete(entity);

	}

	@Override
	public List<Parametros> listaTudo() {
		return super.findAll();
	}

	

}

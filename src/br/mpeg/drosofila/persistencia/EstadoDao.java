package br.mpeg.drosofila.persistencia;

import java.util.List;

import br.mpeg.drosofila.modelo.Estado;
import br.mpeg.drosofila.persistencia.api.IEstadoDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class EstadoDao extends GenericDao<Estado> implements IEstadoDao{

	@Override
	public void salvar(Estado objeto) {
		super.save(objeto);
	}

	@Override
	public void alterar(Estado objeto) {
		super.update(objeto);
	}

	@Override
	public void excluir(Estado objeto) {
		super.delete(objeto);
	}

	@Override
	public List<Estado> listaTudo() {
		return super.findAll();
	}
}

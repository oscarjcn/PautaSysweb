package br.mpeg.drosofila.persistencia;

import java.util.List;

import br.mpeg.drosofila.modelo.Instituicao;
import br.mpeg.drosofila.persistencia.api.IInstituicaoDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class InstituicaoDao extends GenericDao<Instituicao> implements IInstituicaoDao{

	@Override
	public void salvar(Instituicao objeto) {
		super.save(objeto);
		
	}

	@Override
	public void alterar(Instituicao objeto) {
		super.update(objeto);
		
	}

	@Override
	public void excluir(Instituicao objeto) {
		super.delete(objeto);
		
	}

	@Override
	public List<Instituicao> listaTudo() {
		return findAll();
	}

}

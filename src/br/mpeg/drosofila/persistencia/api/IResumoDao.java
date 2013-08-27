package br.mpeg.drosofila.persistencia.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.util.persistencia.IDAO;

public interface IResumoDao extends IDAO<Resumo>{

	public List<Resumo> listaResumoRevisorId(int id);
	public List<Resumo> listaResumoAutor(String cpf);
	public List<Resumo> listaEnviados();
	
}

package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.util.controle.IFacade;

public interface IResumoControle extends IFacade<Resumo>{

	public List<Resumo> listaResumoRevisorId(int id);
	
	public List<Resumo> listaResumoAutor(String cpf);

	public List<Resumo> listaEnviados();
	
	public List<Resumo> listaResumoParticipanteId(int id);
}

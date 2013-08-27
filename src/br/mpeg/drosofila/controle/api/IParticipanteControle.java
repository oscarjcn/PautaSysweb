package br.mpeg.drosofila.controle.api;

import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.util.controle.IFacade;

public interface IParticipanteControle extends IFacade<Participante>{

	public Participante buscaPorNumeroAssociado(Integer numero);
	public Participante pesquisaPorEmail(String email);
	public Participante buscaParticipanteUsuarioId(Integer usuarioId);
}

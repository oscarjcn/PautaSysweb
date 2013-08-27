package br.mpeg.drosofila.persistencia.api;

import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.util.persistencia.IDAO;

public interface IParticipanteDao extends IDAO<Participante>{
	
	public Participante buscaPorNumeroAssociado(Integer numero);
	public Participante pesquisaPorEmail(String email);
	public Participante buscaParticipanteUsuarioId(Integer usuarioId);

}

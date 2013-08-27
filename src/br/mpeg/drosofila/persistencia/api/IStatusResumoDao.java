package br.mpeg.drosofila.persistencia.api;

import br.mpeg.drosofila.modelo.Boleto;
import br.mpeg.drosofila.modelo.StatusResumo;
import br.mpeg.drosofila.util.persistencia.IDAO;

public interface IStatusResumoDao extends IDAO<StatusResumo>{
	public StatusResumo buscaStatusResumo(String nome);

}

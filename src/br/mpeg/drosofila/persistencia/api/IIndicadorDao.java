package br.mpeg.drosofila.persistencia.api;

import java.util.Date;
import java.util.List;

import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.util.persistencia.IDAO;

public interface IIndicadorDao extends IDAO<Indicador>{

	public List<Indicador> listaIndicadoresParaDataAtual(Date data, boolean desconto, boolean parcelado);
	public Indicador buscaIndicadorParticipante(int participanteId);
	
}

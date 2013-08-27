package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.util.controle.IFacade;

public interface IIndicadorControle extends IFacade<Indicador> {
	public List<Indicador> listaIndicadoresParaDataAtual(boolean desconto, boolean parcelado);
	public Indicador buscaIndicadorParticipante(int participanteId);
}

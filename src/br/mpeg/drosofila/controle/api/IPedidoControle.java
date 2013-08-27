package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.util.controle.IFacade;

public interface IPedidoControle extends IFacade<Pedido>{
	public List<Pedido> buscaPedidosParticipanteId(Integer participanteId);
}

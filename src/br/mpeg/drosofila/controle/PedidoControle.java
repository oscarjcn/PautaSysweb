package br.mpeg.drosofila.controle;

import java.util.List;

import br.mpeg.drosofila.controle.api.IPedidoControle;
import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.persistencia.PedidoDao;
import br.mpeg.drosofila.util.controle.GenericControle;

public class PedidoControle extends GenericControle<Pedido> implements IPedidoControle{

	public PedidoControle() {
		super(new PedidoDao());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pedido instaceObject() {
		return super.instanceObject();
	}

	@Override
	public List<Pedido> buscaPedidosParticipanteId(Integer participanteId) {
		return ((PedidoDao) getDao()).buscaPedidosParticipanteId(participanteId);
	}

}

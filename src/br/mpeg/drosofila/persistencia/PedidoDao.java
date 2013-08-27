package br.mpeg.drosofila.persistencia;

import java.util.List;

import javax.persistence.TypedQuery;

import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.util.persistencia.GenericDao;
import br.mpeg.drosofila.util.persistencia.IDAO;

public class PedidoDao extends GenericDao<Pedido> implements IDAO<Pedido> {

	@Override
	public void salvar(Pedido entity) {
		super.save(entity);
	}

	@Override
	public void alterar(Pedido entity) {
		super.update(entity);
	}

	@Override
	public void excluir(Pedido entity) {
		super.delete(entity);
	}

	@Override
	public List<Pedido> listaTudo() {
		return super.findAll();
	}
	
	public List<Pedido> buscaPedidosParticipanteId(Integer participanteId){
		TypedQuery<Pedido> query = getEntityManager().createQuery(
				"SELECT p FROM Pedido AS p" +
				" JOIN p.participante AS part" +
				" WHERE part.id = :participanteId", Pedido.class);
		query.setParameter("participanteId", participanteId);
		return query.getResultList();
	}
	
	public static void main(String[] args) {
		PedidoDao  dao = new PedidoDao();
		List a = dao.buscaPedidosParticipanteId(1);
		System.out.println("\n\nTamanho da lista: "+a.size());
	}
	

}

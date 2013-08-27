package br.mpeg.drosofila.persistencia;

import java.util.List;

import javax.persistence.TypedQuery;

import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.persistencia.api.IParticipanteDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class ParticipanteDao extends GenericDao<Participante> implements IParticipanteDao{

	@Override
	public void salvar(Participante objeto) {
		super.save(objeto);
	}

	@Override
	public void alterar(Participante objeto) {
		super.update(objeto);
	}

	@Override
	public void excluir(Participante objeto) {
		super.delete(objeto);
	}

	@Override
	public List<Participante> listaTudo() {
		return super.findAll();
	}
	
	public Participante pesquisaPorCPF(String cpf){
		return super.findByUniqueColumn("cpf", cpf);
	}
	
	public Participante pesquisaPorRg(String rg){
		return super.findByUniqueColumn("rg", rg);
	}
	
	public Participante pesquisaPorEmail(String email){
		return super.findByUniqueColumn("email", email);
	}
	
	public Participante buscaPorNumeroAssociado(Integer numero){
		//try{
			return super.findByUniqueColumn("numeroSocio", numero);
		/*}catch (javax.persistence.NoResultException e) {
			return null;
		}*/
	}
	
	public Participante buscaParticipanteUsuarioId(Integer usuarioId){
		TypedQuery<Participante> query = getEntityManager().createQuery(
				"SELECT p FROM Usuario AS u" +
				" JOIN u.participante AS p" +
				" WHERE u.id = :usuarioId", Participante.class);
		query.setParameter("usuarioId", usuarioId);
		return query.getSingleResult();
	}
	
	public static void main(String[] args) {
		ParticipanteDao dao = new ParticipanteDao();
		Participante participante = dao.buscaPorNumeroAssociado(2929293);
		System.out.println(participante.getNome());
	}

}

package br.mpeg.drosofila.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.persistencia.api.IResumoDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class ResumoDao extends GenericDao<Resumo> implements IResumoDao{

	public ResumoDao() {
		getEntityManager().clear();
	}
	
	@Override
	public void salvar(Resumo objeto) {
		super.save(objeto);
		
	}

	public void update(Resumo resumo){	
		super.update(resumo);
	}
	
	@Override
	public void alterar(Resumo objeto) {
		super.update(objeto);
		
	}

	@Override
	public void excluir(Resumo objeto) {
		super.delete(objeto);
		
	}

	@Override
	public List<Resumo> listaTudo() {
		return super.findAll();
	}
	
/*	 @SuppressWarnings("unchecked")
		public List<Resumo> listaResumoParticipanteId(int id){
	    	EntityManager em = getEntityManager();
	    	em.clear();
	    	//return (List<T>)
	    	Query query = (Query) em.createQuery("SELECT r FROM Resumo r WHERE r.participante.id = :id");
	    	query.setParameter("id", id);
	    	return query.getResultList();
	    }*/
	 
		public List<Resumo> listaResumoRevisorId(int id){
	    	EntityManager em = getEntityManager();
	    	//em.clear();
	    	//return (List<T>)
	    	TypedQuery<Resumo> query = em.createQuery("SELECT r FROM Resumo r" +
	    			" Join r.statusResumo s" +
	    			" WHERE r.revisor.id = :id AND s.nome != :name", Resumo.class);
	    	query.setParameter("id", id);
	    	query.setParameter("name", "Pendente");
	    	return query.getResultList();
	    }
		
		public List<Resumo> listaResumoParticipanteId(int id){
	    	EntityManager em = getEntityManager();
	    	//em.clear();
	    	//return (List<T>)
	    	TypedQuery<Resumo> query = em.createQuery("SELECT r FROM Participante p" +
	    			" Join p.resumos r" +
	    			" WHERE p.id = :id ", Resumo.class);
	    	query.setParameter("id", id);
	    	return query.getResultList();
	    }
		
		public List<Resumo> listaEnviados(){
	    	EntityManager em = getEntityManager();
	    	//em.clear();
	    	//return (List<T>)
	    	TypedQuery<Resumo> query = em.createQuery("SELECT r FROM Resumo r" +
	    			" Join r.statusResumo s" +
	    			" WHERE s.nome != :name", Resumo.class);
	    	query.setParameter("name", "Pendente");
	    	return query.getResultList();
	    }
		
		public List<Resumo> listaResumoAutor(String cpf){
	    	EntityManager em = getEntityManager();
	    	//em.clear();
	    	TypedQuery<Resumo> query = em.createQuery("SELECT r FROM Resumo r WHERE r.cpfAutor = :cpf", Resumo.class);
	    	query.setParameter("cpf", cpf);
	    	return query.getResultList();
	    }
		
		public static void main(String[] args) {
			ResumoDao resumoDao = new ResumoDao();
			System.out.println("\n\n #####\n "+resumoDao.listaResumoParticipanteId(1).size());
		}

}

package br.mpeg.drosofila.persistencia;

import java.util.List;

import javax.management.Query;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import br.mpeg.drosofila.modelo.Comprovante;
import br.mpeg.drosofila.persistencia.api.IComprovanteDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class ComprovanteDao extends GenericDao<Comprovante> implements IComprovanteDao {

	@Override
	public void salvar(Comprovante entity) {
		super.save(entity);
	}

	@Override
	public void alterar(Comprovante entity) {
		super.update(entity);
	}

	@Override
	public void excluir(Comprovante entity) {
		super.delete(entity);
	}

	@Override
	public List<Comprovante> listaTudo() {
		return super.findAll();
	}
	
	public List<Comprovante> listaComprovantesParticipante(Integer id){
		EntityTransaction tx = getEntityManager().getTransaction();
		TypedQuery<Comprovante> query = null;
		query = getEntityManager().createQuery("SELECT c FROM "+Comprovante.class.getSimpleName()+" as c WHERE c.participante.id = :id", Comprovante.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	/*
	public Comprovante recuperaComprovante(){
		 int result = 0;
	        Long quantidadeVendida=0l;
	    	EntityTransaction tx = getEntityManager().getTransaction();
	    	Query query = null;
	        try {
	            tx.begin();
	            
	            query = (Query) getEntityManager().createQuery("SELECT b FROM "+Comprovante.class.getSimpleName()+
	            		" as b WHERE (item.produto.id = :id )");
	            
	            tx.commit();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	        quantidadeVendida = (Long) query.getSingleResult();
	        
	     return  ((quantidadeVendida==null)? 0: quantidadeVendida);
	}*/

	

}

package br.mpeg.drosofila.persistencia;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.mpeg.drosofila.modelo.Tema;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.persistencia.api.ITemaDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class TemaDao extends GenericDao<Tema> implements ITemaDao {

	@Override
	public void salvar(Tema entity) {
		super.save(entity);

	}

	@Override
	public void alterar(Tema entity) {
		super.update(entity);

	}

	@Override
	public void excluir(Tema entity) {
		super.delete(entity);

	}

	@Override
	public List<Tema> listaTudo() {
		return super.findAll();
	}
	
	/*
	public Tema recuperaTema(){
		 int result = 0;
	        Long quantidadeVendida=0l;
	    	EntityTransaction tx = getEntityManager().getTransaction();
	    	Query query = null;
	        try {
	            tx.begin();
	            
	            query = (Query) getEntityManager().createQuery("SELECT b FROM "+Tema.class.getSimpleName()+
	            		" as b WHERE (item.produto.id = :id )");
	            
	            tx.commit();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	        quantidadeVendida = (Long) query.getSingleResult();
	        
	     return  ((quantidadeVendida==null)? 0: quantidadeVendida);
	}*/

	

}

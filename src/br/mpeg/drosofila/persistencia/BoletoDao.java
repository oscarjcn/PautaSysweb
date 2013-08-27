package br.mpeg.drosofila.persistencia;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.mpeg.drosofila.modelo.Boleto;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.persistencia.api.IBoletoDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class BoletoDao extends GenericDao<Boleto> implements IBoletoDao {

	@Override
	public void salvar(Boleto entity) {
		super.save(entity);

	}

	@Override
	public void alterar(Boleto entity) {
		super.update(entity);

	}

	@Override
	public void excluir(Boleto entity) {
		super.delete(entity);

	}

	@Override
	public List<Boleto> listaTudo() {
		return super.findAll();
	}
	
	/*
	public Boleto recuperaBoleto(){
		 int result = 0;
	        Long quantidadeVendida=0l;
	    	EntityTransaction tx = getEntityManager().getTransaction();
	    	Query query = null;
	        try {
	            tx.begin();
	            
	            query = (Query) getEntityManager().createQuery("SELECT b FROM "+Boleto.class.getSimpleName()+
	            		" as b WHERE (item.produto.id = :id )");
	            
	            tx.commit();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	        quantidadeVendida = (Long) query.getSingleResult();
	        
	     return  ((quantidadeVendida==null)? 0: quantidadeVendida);
	}*/

	

}

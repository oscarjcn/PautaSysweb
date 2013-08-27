package br.mpeg.drosofila.persistencia;

import java.util.List;

import br.mpeg.drosofila.modelo.Privilegio;
import br.mpeg.drosofila.persistencia.api.IPrivilegioDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class PrivilegioDao extends GenericDao<Privilegio> implements IPrivilegioDao {

	@Override
	public void salvar(Privilegio entity) {
		super.save(entity);
	}

	@Override
	public void alterar(Privilegio entity) {
		super.update(entity);
	}

	@Override
	public void excluir(Privilegio entity) {
		super.delete(entity);
	}

	@Override
	public List<Privilegio> listaTudo() {
		return super.findAll();
	}
	
	/*
	public Privilegio recuperaPrivilegio(){
		 int result = 0;
	        Long quantidadeVendida=0l;
	    	EntityTransaction tx = getEntityManager().getTransaction();
	    	Query query = null;
	        try {
	            tx.begin();
	            
	            query = (Query) getEntityManager().createQuery("SELECT b FROM "+Privilegio.class.getSimpleName()+
	            		" as b WHERE (item.produto.id = :id )");
	            
	            tx.commit();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	        quantidadeVendida = (Long) query.getSingleResult();
	        
	     return  ((quantidadeVendida==null)? 0: quantidadeVendida);
	}*/

	

}

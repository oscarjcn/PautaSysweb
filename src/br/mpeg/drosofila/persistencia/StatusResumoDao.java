package br.mpeg.drosofila.persistencia;

import java.util.List;

import br.mpeg.drosofila.modelo.StatusResumo;
import br.mpeg.drosofila.persistencia.api.IStatusResumoDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class StatusResumoDao extends GenericDao<StatusResumo> implements IStatusResumoDao {

	//public static final int STATUS_RESUMO_PENDENTE = 1;
	//public static final int STATUS_RESUMO_REVISADO = 2;
	
	//public static final String STATUS_RESUMO_PENDENTE = 1;
	
	public static final String STATUS_RESUMO_PENDENTE = "pendente";
	public static final String STATUS_RESUMO_ENVIADO = "enviado";
	public static final String STATUS_RESUMO_APROVADO = "aprovado";
	public static final String STATUS_RESUMO_REPROVADO = "reprovado";
	public static final String STATUS_RESUMO_REENVIADO = "reenviado";
	public static final String STATUS_RESUMO_REVISADO = "revisado";
	
	@Override
	public void salvar(StatusResumo entity) {
		super.save(entity);
	}

	@Override
	public void alterar(StatusResumo entity) {
		super.update(entity);
	}

	@Override
	public void excluir(StatusResumo entity) {
		super.delete(entity);
	}

	@Override
	public List<StatusResumo> listaTudo() {
		return super.findAll();
	}
	
	public StatusResumo buscaStatusResumo(String nome){
		try {
			return super.findByUniqueColumn(StatusResumo.class.getField("nome").getName(), nome);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	public StatusResumo recuperaStatusResumo(){
		 int result = 0;
	        Long quantidadeVendida=0l;
	    	EntityTransaction tx = getEntityManager().getTransaction();
	    	Query query = null;
	        try {
	            tx.begin();
	            
	            query = (Query) getEntityManager().createQuery("SELECT b FROM "+StatusResumo.class.getSimpleName()+
	            		" as b WHERE (item.produto.id = :id )");
	            
	            tx.commit();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	        quantidadeVendida = (Long) query.getSingleResult();
	        
	     return  ((quantidadeVendida==null)? 0: quantidadeVendida);
	}*/

	

}

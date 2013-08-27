package br.mpeg.drosofila.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.persistencia.api.IAssociadoDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class AssociadoDao extends GenericDao<Associado> implements IAssociadoDao {

	Class<Associado> associado = Associado.class;
	
	@Override
	public void salvar(Associado entity) {
		super.save(entity);

	}

	@Override
	public void alterar(Associado entity) {
		super.update(entity);

	}

	@Override
	public void excluir(Associado entity) {
		super.delete(entity);

	}
	

	@Override
	public List<Associado> listaTudo() {
		return super.findAll();
	}
	
	public Associado buscaPorNumero(Integer numero){
		try {
			EntityManager em = getEntityManager();
	    	em.clear();
	    	TypedQuery<Associado> q = em.createQuery("SELECT a FROM "+associado.getName()
	    			+" a WHERE a.numero = :numero ", associado);
	    	q.setParameter("numero", numero);
	    	return   q.getSingleResult();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch ( javax.persistence.NoResultException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Associado buscaPorCPF(String cpf) throws NonUniqueResultException{
		try {
			EntityManager em = getEntityManager();
	    	em.clear();
	    	TypedQuery<Associado> q = em.createQuery("SELECT a FROM "+associado.getName()
	    			+" a WHERE a.cpf = :cpf", associado);
	    	q.setParameter("cpf", cpf);
	    	return   q.getSingleResult();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( javax.persistence.NoResultException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Associado buscaPorCPF(String cpf, boolean pagamentoEmDia) throws NonUniqueResultException{
		try {
			EntityManager em = getEntityManager();
	    	em.clear();
	    	TypedQuery<Associado> q = em.createQuery("SELECT a FROM "+associado.getName()
	    			+" a WHERE a.cpf = :cpf AND a.pagamentoEmDia = :emDia", associado);
	    	q.setParameter("cpf", cpf);
	    	q.setParameter("emDia", pagamentoEmDia);
	    	return   q.getSingleResult();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( javax.persistence.NoResultException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Associado buscaPorNumeroCPF(Integer numero, String CPF){
		try {
	    	EntityManager em = getEntityManager();
	    	em.clear();
	    	TypedQuery<Associado> q = em.createQuery("SELECT a FROM "+associado.getName()+" a " +
	    			" WHERE a.numero = :numero AND a.cpf = :cpf AND a.pagamentoEmDia = true", associado);
	    	q.setParameter("numero", numero);
	    	q.setParameter("cpf", CPF);
	    	return   q.getSingleResult();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( javax.persistence.NoResultException e){
			//e.printStackTrace();
		}
			return null;
	}
	
	public Associado buscaPorNumeroCPF(Integer numero, String CPF, boolean emDia){
		try {
	    	EntityManager em = getEntityManager();
	    	em.clear();
	    	TypedQuery<Associado> q = em.createQuery("SELECT a FROM "+associado.getName()+" a " +
	    			" WHERE a.numero = :numero AND a.cpf = :cpf AND a.pagamentoEmDia = :emDia", associado);
	    	q.setParameter("numero", numero);
	    	q.setParameter("cpf", CPF);
	    	q.setParameter("emDia", emDia);
	    	return   q.getSingleResult();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( javax.persistence.NoResultException e){
			//e.printStackTrace();
		}
			return null;
	}
	
	public Associado verificaExiste(Integer numero, String CPF){
		try {
	    	EntityManager em = getEntityManager();
	    	em.clear();
	    	TypedQuery<Associado> q = em.createQuery("SELECT a FROM "+associado.getName()+" a " +
	    			" WHERE a.numero = :numero AND a.cpf = :cpf", associado);
	    	q.setParameter("numero", numero);
	    	q.setParameter("cpf", CPF);
	    	return   q.getSingleResult();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( javax.persistence.NoResultException e){
			//e.printStackTrace();
		}
			return null;
	}
	
	
	public Associado verificaExiste(String email){
		try {
	    	EntityManager em = getEntityManager();
	    	em.clear();
	    	TypedQuery<Associado> q = em.createQuery("SELECT a FROM "+associado.getName()+" a " +
	    			" WHERE a.email = :email", associado);
	    	q.setParameter("email", email);
	    	return   q.getSingleResult();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		catch ( javax.persistence.NoResultException e){	e.printStackTrace();	}
			return null;
	}
	
	/*
	public Associado recuperaAssociado(){
		 int result = 0;
	        Long quantidadeVendida=0l;
	    	EntityTransaction tx = getEntityManager().getTransaction();
	    	Query query = null;
	        try {
	            tx.begin();
	            
	            query = (Query) getEntityManager().createQuery("SELECT b FROM "+Associado.class.getSimpleName()+
	            		" as b WHERE (item.produto.id = :id )");
	            
	            tx.commit();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	        quantidadeVendida = (Long) query.getSingleResult();
	        
	     return  ((quantidadeVendida==null)? 0: quantidadeVendida);
	}*/

	public static void main(String[] args) {
		AssociadoDao associadoDao = new AssociadoDao();
		//Associado a= associadoDao.buscaPorNumeroCPF(123, "12345678-90");
		Associado a = associadoDao.verificaExiste("oscar.chamma@gmail.com");
		System.out.println(a.getNome());
	}




}

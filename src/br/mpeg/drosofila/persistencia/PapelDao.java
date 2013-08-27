package br.mpeg.drosofila.persistencia;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.persistencia.api.IPapelDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class PapelDao extends GenericDao<Papel> implements IPapelDao, Serializable{

	@Override
	public void salvar(Papel entity) {
		super.save(entity);
	}

	@Override
	public void alterar(Papel entity) {
		super.update(entity);
	}

	@Override
	public void excluir(Papel entity) {
		super.delete(entity);

	}

	public List<Papel> buscaPapelPorUsuario(int usuarioID){
		//EntityTransaction tx = getEntityManager().getTransaction();
		List<Papel> papeiUsuario =null;
        try {
           // tx.begin();
            TypedQuery<Papel> query =  getEntityManager().createQuery("SELECT p From Papel p JOIN  p.usuarios u WHERE u.id = :id", Papel.class);
            query.setParameter("id", usuarioID);
            papeiUsuario = query.getResultList();
           //tx.commit();
        } catch (Throwable t) {
            t.printStackTrace();
            //tx.rollback();
        }
        return papeiUsuario;
	}
	
	public List<Usuario> buscaUsuarios(String nomePapel){
		//EntityTransaction tx = getEntityManager().getTransaction();
		List<Usuario> usuarios =null;
        try {
          //  tx.begin();
            TypedQuery<Usuario> query =  getEntityManager().createQuery("SELECT u From Papel p JOIN  p.usuarios u WHERE p.nome = :nomePapel", Usuario.class);
            query.setParameter("nomePapel", nomePapel);
            usuarios = query.getResultList();
           //tx.commit();
        } catch (Throwable t) {
            t.printStackTrace();
            //tx.rollback();
        }
        return usuarios;
	}
	
	@Override
	public List<Papel> listaTudo() {
		return super.findAll();
	}
	
	public List<Papel> listaPorTipo(String tipo){
		Field f = null;
		try {
			f = Produto.class.getDeclaredField("tipo");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		return super.findByColumn(f, tipo);
	}
	
	/*
	public Papel recuperaPapel(){
		 int result = 0;
	        Long quantidadeVendida=0l;
	    	EntityTransaction tx = getEntityManager().getTransaction();
	    	Query query = null;
	        try {
	            tx.begin();
	            
	            query = (Query) getEntityManager().createQuery("SELECT b FROM "+Papel.class.getSimpleName()+
	            		" as b WHERE (item.produto.id = :id )");
	            
	            tx.commit();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	        quantidadeVendida = (Long) query.getSingleResult();
	        
	     return  ((quantidadeVendida==null)? 0: quantidadeVendida);
	}*/

	public static void main(String[] args) {
		PapelDao papelDao = new PapelDao();
		List<Papel> papeis = papelDao.buscaPapelPorUsuario(18);
		System.out.println(papeis.get(0).getNome());
	}

}

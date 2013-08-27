package br.mpeg.drosofila.util.persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	
	private static EntityManager em;
	private static EntityManagerFactory emf;
	

	public static EntityManager getEntityManager() {

		if(emf==null){
			emf = Persistence.createEntityManagerFactory("drosofila");
		}
		if (em == null || !em.isOpen()) {
			em = emf.createEntityManager();
		}	
				

		return em;

	}
	
	public static void limpaCache(){  
        emf.getCache().evictAll();  
    }  
	
}

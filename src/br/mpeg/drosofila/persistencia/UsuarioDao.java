package br.mpeg.drosofila.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.persistencia.api.IUsuarioDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;
import br.mpeg.drosofila.util.persistencia.HibernateUtil;

public class UsuarioDao extends GenericDao<Usuario> implements IUsuarioDao {

	@Override
	public void salvar(Usuario entity) {
		super.save(entity);
	}

	@Override
	public void alterar(Usuario entity) {
		super.update(entity);
	}

	@Override
	public void excluir(Usuario entity) {
		super.delete(entity);
	}

	@Override
	public List<Usuario> listaTudo() {
		return super.findAll();
	}
	
	public void limpaCache(){
		this.getEntityManager().clear();
	}
	
	public List<Usuario> listaPorPapel(String tipoPapel) {
		Session session;
		List<Usuario> usuarios = null;
		EntityManager em = getEntityManager();
		
		String hql = "SELECT distinct u FROM Usuario u JOIN u.papeis papel WHERE papel.tipo= :papel";
		TypedQuery<Usuario>query =  em.createQuery(hql, Usuario.class);
		query.setParameter("papel", tipoPapel);
		return query.getResultList();
		/*try {
			
			session = (Session) getEntityManager().getDelegate();
			
			Criteria criteria = session.createCriteria(Usuario.class);
			criteria.createAlias("papel","p");
			criteria.add(Restrictions.eq("p.NOME", papel));
			usuarios = criteria.list();
		} catch (Exception e) {
		}
		return usuarios;*/	
	}
	
	public List<Usuario> listaPorPapel(Papel papel) {
		Session session;
		List usuarios = null;
		try {
			session = (Session) getEntityManager().getDelegate();
			
			Criteria criteria = session.createCriteria(Usuario.class);
			criteria.add(Restrictions.eq("papel_id", papel.getId()));
			usuarios = criteria.list();
		} catch (Exception e) {
		}
		return usuarios;	
	}
	
	

	public Usuario logar(String login, String senha) {
		Session session;
		Usuario usuario = null;
		try {
			getEntityManager().clear();
			session = (Session) getEntityManager().getDelegate();

			usuario = (Usuario) session.createCriteria(Usuario.class)
					.add(Restrictions.eq("email", login))
					.add(Restrictions.eq("senha", senha)).uniqueResult();
		} catch (Exception e) {
		}
		return usuario;
	}

	public Usuario primeiroAcesso(String login) {
		Session session;
		Usuario usuario = null;
		try {
			session = (Session) getEntityManager().getDelegate();
			usuario = (Usuario) session.createCriteria(Usuario.class)
					.add(Restrictions.eq("email", login))
					.add(Restrictions.isNull("senha"))
					.add(Restrictions.isNotNull("id")).uniqueResult();
		} catch (Exception e) {
		}
		return usuario;
	}
	public Usuario pesquisaPorEmail(String email){
		return super.findByUniqueColumn("email", email);
	}

}

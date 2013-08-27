package br.mpeg.drosofila.util.persistencia;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.persistencia.PedidoDao;
import br.mpeg.drosofila.persistencia.ProdutoDao;


public class GenericDao<T extends Serializable> implements Serializable{

    @PersistenceContext(unitName = "drosofila")
    private EntityManager entityManager;
    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
	public GenericDao() {
        this.entityManager = HibernateUtil.getEntityManager();
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public EntityManager getEntityManager() {
    	entityManager = HibernateUtil.getEntityManager();
        return entityManager;
    }

    protected void save(T entity) {
        EntityTransaction tx = getEntityManager().getTransaction();
        
        System.out.println("Chamou função para salvar");
        
        try {
        	getEntityManager().setFlushMode(FlushModeType.COMMIT);
        	getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            getEntityManager().getTransaction().commit();
        } catch (Throwable t) {
            t.printStackTrace();
            tx.rollback();
        } finally {
            close();
        }
    }

    protected void update(T entity) {
        EntityTransaction tx = getEntityManager().getTransaction();
        try {
            tx.begin();
            getEntityManager().merge(entity);
            tx.commit();
        } catch (Throwable t) {
            t.printStackTrace();
            tx.rollback();
        } finally {
            close();
        }

    }

    protected void delete(T entity) {
        EntityTransaction tx = getEntityManager().getTransaction();
        
        try {
            tx.begin();
            getEntityManager().remove(entity);
            tx.commit();
        } catch (Throwable t) {
            t.printStackTrace();
           // tx.rollback();
        } finally {
            close();
        }
    }

    @SuppressWarnings("unchecked")
	public List<T> findAll() {
        Session session = (Session) getEntityManager().getDelegate();
        return session.createCriteria(persistentClass).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
    

    @SuppressWarnings("unchecked")
	public T findByName(String nome) {
        Session session = (Session) getEntityManager().getDelegate();
        limpaSessao(session);
        //System.out.println(persistentClass.getName());
        return (T) session.createCriteria(persistentClass).add(Restrictions.eq("nome", nome).ignoreCase()).uniqueResult();
    }

    protected void limpaSessao(Session session){
    	session.clear();
        session.setCacheMode(CacheMode.IGNORE); 
    }
    
    @SuppressWarnings("unchecked")
	public List<T> findByColumn(Field field, String valor){
    	Session session = (Session) getEntityManager().getDelegate();
    	limpaSessao(session);
    	return  session.createCriteria(persistentClass).add(Restrictions.eq(field.getName(), valor).ignoreCase()).list();
    }
    
    @SuppressWarnings("unchecked")
	public T findByUniqueColumn(String field, String valorUnico){
    	Session session = (Session) getEntityManager().getDelegate();
    	limpaSessao(session);
    	return (T) session.createCriteria(persistentClass).add(Restrictions.eq(field, valorUnico).ignoreCase()).uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
	public T findByUniqueColumn(String field, int valorUnico){
    	/*Session session = (Session) getEntityManager().getDelegate();
    	limpaSessao(session);*/
    	//return (T) session.createCriteria(persistentClass).add(Restrictions.eq(field, valorUnico).ignoreCase()).uniqueResult();
    	TypedQuery<T>query = getEntityManager().createQuery("SELECT a FROM "+persistentClass.getSimpleName()+" a WHERE ("+field+"= :"+field+")", persistentClass);
    	query.setParameter(field, valorUnico);
    	return query.getSingleResult();
    }
   
    
    @SuppressWarnings("unchecked")
	public T findById(Integer id) {
        Session session = (Session) getEntityManager().getDelegate();
        //limpaSessao(session);
        return (T) session.createCriteria(persistentClass).add(Restrictions.eq("id", id)).uniqueResult();
    }

    private void close() {
        /*if (getEntityManager().isOpen()) {
            getEntityManager().close();
        }*/
       // shutdown();
    }

    /*private void shutdown() {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.createNativeQuery("SHUTDOWN").executeUpdate();
        em.close();
    }*/
    
    public static void main(String[] args) {
		PedidoDao pedidoDao = new PedidoDao();
		ProdutoDao produtoDao = new ProdutoDao();
		
		Produto produto = produtoDao.findById(2);
		Pedido pedido = new Pedido();
		ItemCompra  itemCompra = new ItemCompra();
		
		itemCompra.setProduto(produto);
		itemCompra.setPedido(pedido);
		List<ItemCompra> itensCompra = new ArrayList<ItemCompra>();
		itensCompra.add(itemCompra);
		
		pedido.setItemsCompra(itensCompra);
		pedido.setDataPedido(new Date());
		
		pedidoDao.save(pedido);
		
	}
    
    
    
}

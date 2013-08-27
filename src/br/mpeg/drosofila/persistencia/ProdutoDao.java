package br.mpeg.drosofila.persistencia;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.persistencia.api.IProdutoDao;
import br.mpeg.drosofila.util.persistencia.GenericDao;

public class ProdutoDao extends GenericDao<Produto> implements IProdutoDao {

	@Override
	public void salvar(Produto objeto) {
		super.save(objeto);
	}

	@Override
	public void alterar(Produto objeto) {
		super.update(objeto);
	}

	@Override
	public void excluir(Produto objeto) {
		super.delete(objeto);
	}

	@Override
	public List<Produto> listaTudo() {
		return super.findAll();
	}
	
	
	public List<Produto> listaPorTipo(String tipo){
		Field f = null;
		try {
			f = Produto.class.getDeclaredField("tipo");
		} catch (SecurityException e) {
			 System.err.println(e.getStackTrace());
		} catch (NoSuchFieldException e) {
			System.err.println(e.getStackTrace());
		}
		
		List<Produto> produtos = new ArrayList<Produto>();
		try {
			 produtos = super.findByColumn(f, tipo);
		} catch (Exception e) {
			System.err.println();
		}
		return produtos;
	}
	
	public Produto buscaInscricao() {
        int result = 0;
    	EntityTransaction tx = getEntityManager().getTransaction();
    	Query query = null;
      /*  try {
            tx.begin();*/
            
            query = (Query) getEntityManager().createQuery("SELECT p FROM "+Produto.class.getSimpleName()+
            		" as p WHERE (p.tipo = 'I')");
            
/*            tx.commit();
        } catch (Throwable t) {
            t.printStackTrace();
        }*/
        
        return  (Produto) query.getSingleResult();
    }
	
	
    public Long findQuantidadeProdutosVendidos(int  id) {
        int result = 0;
        Long quantidadeVendida=0l;
    	EntityTransaction tx = getEntityManager().getTransaction();
    	Query query = null;
       /* try {
            tx.begin();*/
            
            query = (Query) getEntityManager().createQuery("SELECT sum(item.quantidade) FROM "+ItemCompra.class.getSimpleName()+
            		" as item WHERE (item.produto.id = :id AND item.nivelPrioridade != :nivelPrioridade)");
            int nivelPrioridadeOpcaoSecundaria = 2;
            query.setParameter("nivelPrioridade", nivelPrioridadeOpcaoSecundaria);
            query.setParameter("id", id);
   /*         tx.commit();
        } catch (Throwable t) {
            t.printStackTrace();
        }*/
        quantidadeVendida = (Long) query.getSingleResult();
        
        return  ((quantidadeVendida==null)? 0: quantidadeVendida);
    }
    
    
    public Long findQuantidadeProdutosVendidosConfirmados(int  id) {
        int result = 0;
        Long quantidadeVendida=0l;
    	//EntityTransaction tx = getEntityManager().getTransaction();
    	Query query = null;
       /* try {
            tx.begin();*/
            
            query = (Query) getEntityManager().createQuery("SELECT sum(item.quantidade) FROM "+ItemCompra.class.getSimpleName()+
            		" as item WHERE (item.produto.id = :id AND item.pedido.boleto.quitado=true AND item.nivelPrioridade != :nivelPrioridade )");
            int nivelPrioridadeOpcaoSecundaria = 2;
            query.setParameter("nivelPrioridade", nivelPrioridadeOpcaoSecundaria);
            query.setParameter("id", id);
            //tx.commit();
        /*} catch (Throwable t) {
            t.printStackTrace();
        }*/
        quantidadeVendida = (Long) query.getSingleResult();
        
        return  ((quantidadeVendida==null)? 0: quantidadeVendida);
    }
    
    public static void main(String[] args) {
		ProdutoDao dao = new ProdutoDao();
		System.out.println(dao.findQuantidadeProdutosVendidosConfirmados(3));
	}
	

}

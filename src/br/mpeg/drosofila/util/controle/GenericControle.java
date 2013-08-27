/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mpeg.drosofila.util.controle;

import java.io.Serializable;
import java.util.List;

import br.mpeg.drosofila.util.persistencia.IDAO;

/**
 *
 * @author Victor Coutinho
 * 
 * Classe generica da camada de controle
 * 
 */
public  class GenericControle<T>  implements Serializable{
   
    private IDAO<T> dao;

    public GenericControle(IDAO<T> dao) {
        this.dao = dao;
    }
    
    public T save(T obj) {
       dao.salvar(obj);
       return obj;
    }
    
    public List<T> update(T obj) {
        dao.alterar(obj);
        return dao.listaTudo();
    }
    
    public List<T> delete(T obj) {
        dao.excluir(obj);
        return dao.listaTudo();
    }
    
    public List<T> findAll() {
       return dao.listaTudo();
    }

    
    public T findById(Integer id) {
        return dao.findById(id);
    }
    
    public T findByName(String nome) {
       return dao.findByName(nome);
    }
    
    //public abstract  T instaceObject();
    
    @SuppressWarnings({ "null", "unchecked" })
	public T instanceObject(){
    	T obj = null;
    	try {
			return (T) obj.getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj; 
    }

    public IDAO<T> getDao() {
        return dao;
    }

    public void setDao(IDAO<T> dao) {
        this.dao = dao;
    }
    
    
    
    
}

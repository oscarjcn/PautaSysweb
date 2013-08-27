package br.mpeg.drosofila.util.controle;

import java.util.List;


public interface IFacade<T extends java.io.Serializable> {
	
	public T save(T obj);

	public List<T> update(T obj);

	public List<T> delete(T obj);

	public List<T> findAll();

	public T findById(Integer id);

	public T findByName(String nome);

	public T instaceObject();
        

	//public Long getCountResult(String consulta);

	//public T findByDescricao(Class classe, String valorDesc,	String campoDesc, String condicao);

	//public String setFiltro(T objetoFiltro, String where);
}

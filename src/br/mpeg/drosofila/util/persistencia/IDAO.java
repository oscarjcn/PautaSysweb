package br.mpeg.drosofila.util.persistencia;

import java.util.List;

public interface IDAO<T> {

	public abstract void salvar(T entity);

	public abstract void alterar(T entity);

	public abstract void excluir(T entity);

	public abstract List<T> listaTudo();

	public abstract T findByUniqueColumn(String field, String string);

	public abstract T findByName(String nome);

	public abstract T findById(Integer id);
}

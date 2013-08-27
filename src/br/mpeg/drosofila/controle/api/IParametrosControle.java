package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Parametros;
import br.mpeg.drosofila.util.controle.IFacade;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public interface IParametrosControle extends IFacade<Parametros> {

	public Parametros save(Parametros obj);

	public List<Parametros> update(Parametros obj);

	public List<Parametros> delete(Parametros obj);

	public List<Parametros> findAll();

	public Parametros findById(Integer id);

	public Parametros instaceObject();
}
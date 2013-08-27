package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Tema;
import br.mpeg.drosofila.util.controle.IFacade;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public interface ITemaControle extends IFacade<Tema> {

	public Tema save(Tema obj);

	public List<Tema> update(Tema obj);

	public List<Tema> delete(Tema obj);

	public List<Tema> findAll();

	public Tema findById(Integer id);

	public Tema instaceObject();


}
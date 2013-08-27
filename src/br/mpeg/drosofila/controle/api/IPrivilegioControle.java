package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Privilegio;
import br.mpeg.drosofila.util.controle.IFacade;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public interface IPrivilegioControle extends IFacade<Privilegio> {

	public Privilegio save(Privilegio obj);

	public List<Privilegio> update(Privilegio obj);

	public List<Privilegio> delete(Privilegio obj);

	public List<Privilegio> findAll();

	public Privilegio findById(Integer id);

	public Privilegio instaceObject();


}
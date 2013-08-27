package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.StatusResumo;
import br.mpeg.drosofila.util.controle.IFacade;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public interface IStatusResumoControle extends IFacade<StatusResumo> {

	public StatusResumo save(StatusResumo obj);

	public List<StatusResumo> update(StatusResumo obj);

	public List<StatusResumo> delete(StatusResumo obj);

	public List<StatusResumo> findAll();

	public StatusResumo findById(Integer id);

	public StatusResumo instaceObject();
	
	public StatusResumo buscaStatusResumo(String nome);

}
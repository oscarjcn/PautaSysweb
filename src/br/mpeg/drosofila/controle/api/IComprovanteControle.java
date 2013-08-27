package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Comprovante;
import br.mpeg.drosofila.util.controle.IFacade;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public interface IComprovanteControle extends IFacade<Comprovante> {

	public Comprovante save(Comprovante obj);

	public List<Comprovante> update(Comprovante obj);

	public List<Comprovante> delete(Comprovante obj);

	public List<Comprovante> findAll();

	public Comprovante findById(Integer id);

	public Comprovante instaceObject();

	
	public List<Comprovante> listaComprovantesParticipante(Integer id);

}
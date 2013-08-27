package br.mpeg.drosofila.controle.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Boleto;
import br.mpeg.drosofila.util.controle.IFacade;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public interface IBoletoControle extends IFacade<Boleto> {

	public Boleto save(Boleto obj);

	public List<Boleto> update(Boleto obj);

	public List<Boleto> delete(Boleto obj);

	public List<Boleto> findAll();

	public Boleto findById(Integer id);

	public Boleto instaceObject();


}
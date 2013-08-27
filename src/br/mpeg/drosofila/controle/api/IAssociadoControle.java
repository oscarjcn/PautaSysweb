package br.mpeg.drosofila.controle.api;

import java.util.List;

import javax.persistence.NonUniqueResultException;

import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.util.controle.IFacade;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public interface IAssociadoControle extends IFacade<Associado> {

	public Associado save(Associado obj);

	public List<Associado> update(Associado obj);

	public List<Associado> delete(Associado obj);

	public List<Associado> findAll();

	public Associado findById(Integer id);

	public Associado instaceObject();
	
	public Associado buscaPorNumeroCPF(Integer numero, String CPF);
	
	public Associado buscaPorNumeroCPF(Integer numero, String CPF, boolean emDia);
	
	public Associado verificaExiste(Integer numero, String CPF);

	public Associado verificaExiste(String email);
	
	public Associado buscaPorNumero(Integer numero);
	
	public Associado buscaPorCPF(String CPF);
	
	public Associado buscaPorCPF(String cpf, boolean pagamentoEmDia) throws NonUniqueResultException;
}
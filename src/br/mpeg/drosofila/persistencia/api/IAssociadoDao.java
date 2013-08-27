package br.mpeg.drosofila.persistencia.api;

import java.util.List;

import javax.persistence.NonUniqueResultException;

import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.modelo.Boleto;
import br.mpeg.drosofila.util.persistencia.IDAO;

public interface IAssociadoDao extends IDAO<Associado>{
	public Associado buscaPorNumero(Integer numero);
	public Associado buscaPorNumeroCPF(Integer numero, String CPF);
	public Associado buscaPorNumeroCPF(Integer numero, String CPF, boolean emDia);
	public Associado verificaExiste(Integer numero, String CPF);	
	public Associado verificaExiste(String email);
	public Associado buscaPorCPF(String CPF);
	public Associado buscaPorCPF(String cpf, boolean pagamentoEmDia) throws NonUniqueResultException;
}

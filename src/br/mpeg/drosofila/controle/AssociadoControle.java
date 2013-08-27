package br.mpeg.drosofila.controle;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NonUniqueResultException;

import br.mpeg.drosofila.controle.api.IAssociadoControle;
import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.persistencia.AssociadoDao;
import br.mpeg.drosofila.persistencia.api.IAssociadoDao;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public class AssociadoControle implements IAssociadoControle, Serializable {

	private static final long serialVersionUID = 1L;
	private final IAssociadoDao dao = new AssociadoDao();

	public Associado save(Associado Associado) {

		dao.salvar(Associado);
		
		return null;
	}

	@SuppressWarnings("unused")
	public List<Associado> update(Associado Associado) {
		dao.alterar(Associado);
		return dao.listaTudo();
	}

	public List<Associado> delete(Associado Associado) {
		new AssociadoDao().excluir(Associado);
		return dao.listaTudo();
	}

	public Associado findById(Integer id) {
		return dao.findById(id);
	}

	public Associado findByName(String nome) {
		return null;
	}

	public Associado instaceObject() {
		Associado Associado = new Associado();
		return Associado;
	}

	public Associado buscaPorNumero(int numero){
		return  dao.buscaPorNumero(numero);
	}
	
	public Associado buscaPorNumeroCPF(Integer numero, String CPF){
		return dao.buscaPorNumeroCPF(numero, CPF);
	}
		
	public Associado buscaPorNumeroCPF(Integer numero, String CPF, boolean emDia){
		return dao.buscaPorNumeroCPF(numero, CPF, emDia);
	}
	
	public Associado verificaExiste(Integer numero, String CPF){
		return dao.verificaExiste(numero, CPF);
	}
	
	public Associado verificaExiste(String email){
		return dao.verificaExiste(email);
	}
	
	public Associado buscaPorCPF(String cpf){
		return dao.buscaPorCPF(cpf);
	}
	public Associado buscaPorCPF(String cpf, boolean pagamentoEmDia) throws NonUniqueResultException{
		return dao.buscaPorCPF(cpf, pagamentoEmDia);
	}
	
	public Associado buscaPorNumero(Integer numero){
		return dao.buscaPorNumero(numero);
	}
	
	public Associado buscaPorNome(String nome){
		return dao.findByUniqueColumn("nome", nome);
	}
	
	public List<Associado> findAll() {
		return dao.listaTudo();
	}

	public Associado findAssociadoByPedido() {
		
		return null;
	}

	
}

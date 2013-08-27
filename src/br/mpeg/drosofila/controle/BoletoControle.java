package br.mpeg.drosofila.controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.mpeg.drosofila.controle.api.IBoletoControle;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.modelo.Boleto;
import br.mpeg.drosofila.persistencia.ParticipanteDao;
import br.mpeg.drosofila.persistencia.ResumoDao;
import br.mpeg.drosofila.persistencia.BoletoDao;
import br.mpeg.drosofila.persistencia.api.IBoletoDao;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.criptografia.GeraHash;
import br.mpeg.drosofila.visao.ParticipanteBeans;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public class BoletoControle implements IBoletoControle, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final IBoletoDao dao = new BoletoDao();

	public Boleto save(Boleto Boleto) {

		dao.salvar(Boleto);
		
		return null;
	}

	@SuppressWarnings("unused")
	public List<Boleto> update(Boleto Boleto) {
		dao.alterar(Boleto);
		return dao.listaTudo();
	}

	public List<Boleto> delete(Boleto Boleto) {
		new BoletoDao().excluir(Boleto);
		return dao.listaTudo();
	}

	public Boleto findById(Integer id) {
		return dao.findById(id);
	}

	public Boleto findByName(String nome) {
		return null;
	}

	public Boleto instaceObject() {
		Boleto Boleto = new Boleto();
		return Boleto;
	}


	public List<Boleto> findAll() {
		return dao.listaTudo();
	}

	public Boleto findBoletoByPedido() {
		
		return null;
	}


	
}

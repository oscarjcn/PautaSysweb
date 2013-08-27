package br.mpeg.drosofila.controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.mpeg.drosofila.controle.api.ITemaControle;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.modelo.Tema;
import br.mpeg.drosofila.persistencia.ParticipanteDao;
import br.mpeg.drosofila.persistencia.ResumoDao;
import br.mpeg.drosofila.persistencia.TemaDao;
import br.mpeg.drosofila.persistencia.api.ITemaDao;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.criptografia.GeraHash;
import br.mpeg.drosofila.visao.ParticipanteBeans;

/**
 * 
 * @author Victor Coutinho
 * 
 */
public class TemaControle implements ITemaControle, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ITemaDao dao = new TemaDao();

	public Tema save(Tema Tema) {

		dao.salvar(Tema);
		
		return null;
	}

	@SuppressWarnings("unused")
	public List<Tema> update(Tema Tema) {
		dao.alterar(Tema);
		return dao.listaTudo();
	}

	public List<Tema> delete(Tema Tema) {
		new TemaDao().excluir(Tema);
		return dao.listaTudo();
	}

	public Tema findById(Integer id) {
		return dao.findById(id);
	}

	public Tema findByName(String nome) {
		return null;
	}

	public Tema instaceObject() {
		Tema Tema = new Tema();
		return Tema;
	}


	public List<Tema> findAll() {
		return dao.listaTudo();
	}

	public Tema findTemaByPedido() {
		
		return null;
	}


	
}

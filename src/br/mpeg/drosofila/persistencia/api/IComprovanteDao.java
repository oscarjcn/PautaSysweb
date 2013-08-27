package br.mpeg.drosofila.persistencia.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Boleto;
import br.mpeg.drosofila.modelo.Comprovante;
import br.mpeg.drosofila.util.persistencia.IDAO;

public interface IComprovanteDao extends IDAO<Comprovante>{

	public List<Comprovante> listaComprovantesParticipante(Integer id);
}

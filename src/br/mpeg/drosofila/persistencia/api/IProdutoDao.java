package br.mpeg.drosofila.persistencia.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.util.persistencia.IDAO;

public interface IProdutoDao extends IDAO<Produto>{
	public List<Produto> listaPorTipo(String tipo);
}

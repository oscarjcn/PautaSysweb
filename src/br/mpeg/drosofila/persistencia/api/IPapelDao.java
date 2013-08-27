package br.mpeg.drosofila.persistencia.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Boleto;
import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.persistencia.IDAO;

public interface IPapelDao extends IDAO<Papel>{
	public List<Papel> listaPorTipo(String tipo);
	public List<Papel> buscaPapelPorUsuario(int usuarioID);
	public List<Usuario> buscaUsuarios(String nomePapel);
}

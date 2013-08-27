package br.mpeg.drosofila.persistencia.api;

import java.util.List;

import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.persistencia.IDAO;

public interface IUsuarioDao extends IDAO<Usuario>{

	Usuario logar(String login, String criptografar);

	Usuario primeiroAcesso(String loginName);
	
	List<Usuario> listaPorPapel(String papel);
	List<Usuario> listaPorPapel(Papel papel);
	
	public void limpaCache();

}

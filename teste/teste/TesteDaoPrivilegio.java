package teste;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.mpeg.drosofila.controle.PapelControle;
import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.persistencia.UsuarioDao;

public class TesteDaoPrivilegio {

	public static void main(String args[]){
		char aChar = "a".charAt(0);

		UsuarioDao usuarioDao =  new UsuarioDao();
		PapelControle papelControle =  new PapelControle();
		
		Papel papel = papelControle.findByName("cadastrado");
		System.out.println("id: "+papel.getId()+" -papel:  "+papel.getNome());
		
		Usuario usuario = new Usuario("teste@teste.com", "teste", "123123");
//		usuario.setPapeis(Arrays.asList(papel));
		List<Papel> lista = new ArrayList<Papel>();
		lista.add(papel);
		usuario.setPapeis(lista);
		
		usuarioDao.salvar(usuario);
	}
}

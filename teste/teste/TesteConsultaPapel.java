package teste;

import java.util.List;

import br.mpeg.drosofila.controle.PapelControle;
import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.persistencia.UsuarioDao;

public class TesteConsultaPapel {
	
	public static void main(String[] args) {
		/*PapelControle  controlePapel =  new PapelControle();
		Papel papel =  controlePapel.findByName("cadastrado");
		System.out.println(papel.getNome());*/
		
		
		/*UsuarioControle usuarioControle = new UsuarioControle();
		/*Usuario u = usuarioControle.find("Oscar");
		System.out.println(u.getNome());*/
		
		/*UsuarioDao dao = new UsuarioDao();
		Papel papel = new Papel();
		papel.setId(3);*/
		UsuarioControle usuarioControle = new UsuarioControle();
		List<Usuario> usuarios = usuarioControle.listaPorPapelRevisor();//dao.listaPorPapel("C");
		System.out.println(usuarios.get(0).getNome());
	}

}

package br.mpeg.drosofila.teste;

import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.modelo.Usuario;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Usuario usuario = new UsuarioControle().logar("victorcoutinho1985@gmail.com", "qwe123");
		System.out.println("participante");
		System.out.println(usuario.getParticipante());
		System.out.println("instituicao");
		System.out.println(usuario.getParticipante().getInstituicao().getNome());
		for (Resumo	 res : usuario.getParticipante().getResumos()) {
			System.out.print(res.getId());
			System.out.println(" - "+res.getCaminhoArquivo());
			
		}

	}

}

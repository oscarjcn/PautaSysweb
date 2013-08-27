package br.mpeg.drosofila.visao;

import br.mpeg.drosofila.controle.AssociadoControle;
import br.mpeg.drosofila.controle.api.IAssociadoControle;
import br.mpeg.drosofila.modelo.Associado;


public class PagamentoBeansTest {
	
	
	public static void main(String[] args) {	
		conultaPArticpanteAssociado();
	}
	public static void pegaExtensaoNome(){
		String nomeArquivo = "ksdfmksdfmk.skdmf.kmsdf.docx";
		System.out.println(nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length()));
	}
	
	public static void conultaPArticpanteAssociado(){
		IAssociadoControle associadoControle = new AssociadoControle();
		Associado a= associadoControle.verificaExiste(2929293, "99999988888");
		System.out.println("Associado: "+a);
	}

}

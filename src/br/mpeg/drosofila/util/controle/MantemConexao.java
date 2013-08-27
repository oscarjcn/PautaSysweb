package br.mpeg.drosofila.util.controle;

import java.util.Date;

import br.mpeg.drosofila.controle.EstadoControle;

//TODO Verificar configurações do c3p0 para substituir esta classe
public class MantemConexao extends Thread{

	public static boolean iniciado=false;
	
	
	//TODO Verificar configurações do c3p0 para substituir esta classe
	public void run(){

		iniciado=true;
		
		while(true){
			EstadoControle controle = new EstadoControle();
			try {
				System.out.println("Manutenção da conexão dormindo. "+new Date());
				sleep((60*60*1000*4));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long  teste = controle.findAll().size();
			System.out.println("Executando consulta. "+new Date()+"   -  "+teste);
		}
	}
	
}

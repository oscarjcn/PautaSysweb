package teste;

public class TesteExpressoesRegulares {
	
	
	public static void deixaApenasLetras(String texto){
		if(texto.matches("[A_Z]*"))
			System.out.println("valido");
		else
			System.out.println("invalido");
	}

	public static void main(String[] args) {
		String texto="asdajs & d1238123128";
		System.out.println("teste com 'askdmasmd'");
		deixaApenasLetras("askdmasmd");
		System.out.println("\n");
		
		System.out.println("teste com 'askdmasmdAJJNASN'");
		deixaApenasLetras("askdmasmdAJJNASN");
		System.out.println("\n");

		System.out.println("teste com 'askdm asmd asdasdadsASANS'");
		deixaApenasLetras("askdm asmd asdasdadsASANS");
		System.out.println("\n");

		System.out.println("teste com 'asdajsd1238123128'");
		deixaApenasLetras("asdajsd1238123128");
		System.out.println("\n");
		
		texto="asdajs & d1238123128";
		System.out.println("teste com '"+texto+"'");
		deixaApenasLetras(texto);
		System.out.println("\n");
		
		texto="asdajs () d1238123128'";
		System.out.println("teste com '"+texto+"'");
		deixaApenasLetras(texto);
		System.out.println("\n");
		
	}
	
	
}

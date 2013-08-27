package br.mpeg.drosofila.visao;

public class ParticipanteBeans2 {
	public static void main(String[]arg){  
        String nome = "David Kennedy Souza Araujo";  
        String primeiroNome = "";  
          
        for (int i=0;i<nome.length();i++){  
            if ((i==0) && (nome.substring(i, i+1).equalsIgnoreCase(" "))){  
                System.out.println("Erro: Nome digitado iniciado com tecla ESPAÇO.");  
                break;  
            }  
            else if (!nome.substring(i, i+1).equalsIgnoreCase(" ")){  
                primeiroNome += nome.substring(i, i+1);  
            }  
            else  
                break;  
                  
        }  
        System.out.println(primeiroNome);  


}  
}

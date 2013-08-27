package br.mpeg.drosofila.util.String;

public class StringUtil {
	
	public static String apenasAlphaNumerico(String texto){
	 return texto.replaceAll("[^\\p{ASCII}]", "").replaceAll(" ", "_").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("[\\\\]", "").replaceAll("[^A-z0-9]", "");
	}
}

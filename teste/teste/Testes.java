package teste;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Testes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		teste();

	}
	
	public static void teste(){
		Pattern p = Pattern.compile("[A-Za-z0-9][A-Za-z0-9][A-Za-z0-9]+");
	    Matcher m = p.matcher("12f");

	    boolean matchFound = m.matches();
	    System.out.println(matchFound+"");
	}

	public static void testenull(){
		String rg = null;
		if(rg==null || rg.trim().equals("")){
			System.out.println("ok");
		}else{
			System.out.println("erro");
		}
	}
	
}

package br.mpeg.drosofila.teste;

import java.text.DecimalFormat;  
import java.text.DecimalFormatSymbols;  
import java.util.Locale;  

import br.mpeg.drosofila.visao.converter.MoedaConverter;

public class Moeda {

	 /** 
     * Simbolos especificos do Dolar Americano 
     */  
    private static final DecimalFormatSymbols DOLAR = new DecimalFormatSymbols(Locale.US);  
    /** 
     * Mascara de dinheiro para Dolar Americano 
     */  
    public static final DecimalFormat DINHEIRO_DOLAR = new DecimalFormat("¤ ###,###,##0.00",DOLAR);  
    /** 
     * Simbolos especificos do Euro 
     */  
    private static final DecimalFormatSymbols EURO = new DecimalFormatSymbols(Locale.GERMANY);  
    /** 
     * Mascara de dinheiro para Euro 
     */  
    public static final DecimalFormat DINHEIRO_EURO = new DecimalFormat("¤ ###,###,##0.00",EURO);  
    /** 
     * Locale Brasileiro 
     */  
    private static final Locale BRAZIL = new Locale("pt","BR");  
    /** 
     * Sï¿½mbolos especificos do Real Brasileiro 
     */  
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);  
    /** 
     * Mascara de dinheiro para Real Brasileiro 
     */  
    public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00",REAL);  
      
    /** 
     * Mascara texto com formatacao monetaria 
     * @param valor Valor a ser mascarado 
     * @param moeda Padrao monetario a ser usado 
     * @return Valor mascarado de acordo com o padrao especificado 
     */  
    public static String mascaraDinheiro(double valor, DecimalFormat moeda){  
        return moeda.format(valor);  
    }  
    
    public static void main(String[] args) {
		System.out.println(Moeda.mascaraDinheiro(3d, Moeda.DINHEIRO_REAL));
	}
	
}

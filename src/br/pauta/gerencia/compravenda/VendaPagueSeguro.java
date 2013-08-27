package br.pauta.gerencia.compravenda;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import sun.org.mozilla.javascript.internal.annotations.JSFunction;

import com.sun.mail.handlers.text_html;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Currency;
import br.com.uol.pagseguro.domain.PaymentRequest;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.mpeg.drosofila.controle.IndicadorControle;
import br.mpeg.drosofila.controle.ProdutoControle;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.util.visao.MensagemJSF;

public class VendaPagueSeguro {
	private PaymentRequest paymentRequest = new PaymentRequest();
	
	public String efetuaPagamento (){	
		paymentRequest.setCurrency(Currency.BRL);  
		paymentRequest.setSender("Oscar teste", "oscar.chamma@gmail.com", "91", "83200175");
		paymentRequest.addItem(
				"0001",  
			    "chiclete",   
			    new Integer(1),   
			    new BigDecimal("0.50"),   
			    new Long(1000),   
			    null  
			);
		URL paymentURL = null;
		 try {
			paymentURL = paymentRequest.register(new  
					AccountCredentials(  
					    "secretaria@abrh-pa.com.br",   
					    "2EDD16ADAB894BDF8680A7E2A08732E7")  
					);
		} catch (PagSeguroServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return paymentURL.toString();
		 //redirecionarParaPagueSeguro(paymentURL.toString());
	}
	
	
	public void adicionaProduto(Pedido pedido){
		Long pesoEmGramas = null;
		for(ItemCompra itemCompra: pedido.getItemsCompra()){
/*			paymentRequest.addItem(
					itemCompra.getId().toString(),  
				    itemCompra.getProduto().getNome(),   
				    new Integer(itemCompra.getQuantidade()),   
				    new BigDecimal(formataPreco(itemCompra.getProduto().getPreco())),   
				    new Long(1000),
				    null  
				);*/
			paymentRequest.addItem(
					itemCompra.getId().toString(),  
				    itemCompra.getProduto().getNome(),   
				    new Integer(itemCompra.getQuantidade()),   
				    new BigDecimal(formataPreco(itemCompra.getProduto().getPreco())), //new BigDecimal("0.50"),   
				    new Long(1000),   
				    null  
				);
		}
	}
	
	
	private void configuraIndicadorParticipante(Participante participante){
		if(participante.getIndicador()== null){
			IndicadorControle indicadorControle = new IndicadorControle();
			participante.setIndicador(indicadorControle.buscaIndicadorParticipante(participante.getId()));
		}
	}
	
	public String efetuaPagamento(Participante participante, List<Pedido> pedidos){	
		paymentRequest.setCurrency(Currency.BRL);  
		paymentRequest.setSender(participante.getNome(), participante.getEmail(),  participante.getDdd().toString(), participante.getTelefone());
		configuraIndicadorParticipante(participante);
		
		for (Pedido pedido : pedidos) {
			if(pedido.getItemsCompra().get(0).getProduto().getTipo().trim().equals("I")){
				pedido.setValorPedido(Double.parseDouble(participante.getIndicador().getValor().toString()));
				pedido.getItemsCompra().get(0).getProduto().setPreco(participante.getIndicador().getValor());
			}
				adicionaProduto(pedido);
		}
/*		paymentRequest.addItem(
				"0001",  
			    "chiclete",   
			    new Integer(1),   
			    new BigDecimal("0.50"),   
			    new Long(1000),   
			    null  
			);*/
		URL paymentURL = null;
		 try {
			paymentURL = paymentRequest.register(new  
					AccountCredentials(  
						    "secretaria@abrh-pa.com.br",   
						    "2EDD16ADAB894BDF8680A7E2A08732E7")  
					);
		} catch (PagSeguroServiceException e) {
			MensagemJSF.exibeMensagemErro("Por favor, verifique se seu e-mail ddd ou telefone estão corretos.", "Por favor, verifique se seu e-mail ddd ou telefone estão corretos.");
			e.printStackTrace();
		}
		 //redirecionarParaPagueSeguro(paymentURL.toString());
		 return paymentURL.toString();
	}
	
	
	

	  
	public static void main(String[] args) {
		/*VendaPagueSeguro pagueSeguro = new VendaPagueSeguro();
		pagueSeguro.efetuaPagamento(); */
		System.out.println(formataPreco(BigDecimal.valueOf(77777.77)));
	}

	public static String formataPreco(BigDecimal teste){
		System.out.println(teste);
		java.math.BigDecimal num = new java.math.BigDecimal( teste.toString() );  
		
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance( new Locale( "pt_br" ) );
		
		/* java.math.BigDecimal num = new java.math.BigDecimal( "123456" );
			java.text.DecimalFormat df = new java.text.DecimalFormat( "#,##0.00" );
			System.out.println( frm.format( num ) );
		 */
		java.text.DecimalFormat df = new java.text.DecimalFormat( ".##" );
		
		String formatado = df.format( num ).replace(",", ".");
		System.out.println(formatado);
		System.out.println(formatado.split("\\.")[1]);
		
		if(formatado.split("\\.")[1].length()<2)
			formatado+="0";
		System.out.println( "######################################################## numero formatado"+formatado);
		return  formatado; 
	}
	
	public static String formataPreco(Float numero){
		return  formataPreco(BigDecimal.valueOf(numero)); 
	}
	
}

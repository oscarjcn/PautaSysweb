/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mpeg.drosofila.util.boleto;


import java.io.File;
import java.math.BigDecimal;
import java.util.Date;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.SacadorAvalista;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.EnumAceite;


/**
 *
 * @author ojneto
 */
public class GeradorBoleto {
    
    Endereco enderecoSac;
    Sacado sacado;
    BoletoViewer boletoViewer;
    public GeradorBoleto() {
        enderecoSac = new Endereco();
    }
    
    
    public void gerar(String nomeEmpresa, String cpfCNPJEmpresa, String numeroDoDocumento, String numeroBoleto){
    	String convenio = "2270969";
    	String nossoNumero = numeroBoleto;
    	int tamanhoMinimo=10;
    	nossoNumero = completaComZeroNaFrente(nossoNumero, convenio, tamanhoMinimo);
    	
    	gerar(nomeEmpresa, cpfCNPJEmpresa, nomeEmpresa, cpfCNPJEmpresa, numeroDoDocumento, nossoNumero);
    	
    }


	private static String completaComZeroNaFrente(String numeroBoleto, String convenio,  int tamanhoMinimo) {
		String nossoNumero="";
		
		if(numeroBoleto.length()<tamanhoMinimo){
    		for( int i=0; i<(tamanhoMinimo - numeroBoleto.length()); i++)
    			nossoNumero += "0"; 
    	}
    	nossoNumero = convenio+nossoNumero+numeroBoleto;
		return nossoNumero;
	}
    
    public BoletoViewer gerar(String nomeCedente, String cpfCNPJCedente, String nomeSacadorAvalista, String cpfCNPJSacadorAvalista, String numeroDocumento,
    		String nossoNumero){
        Cedente cedente = new Cedente(nomeCedente, cpfCNPJCedente);//"PROJETO JRimum", "00.000.208/0001-00");
        Sacado sacado = this.sacado;//new Sacado("JavaDeveloper Pronto Para F�rias", "881.605.562-68");

        // Informando o endere�o do sacado.
        Endereco enderecoSac = configEnderecoSac(new Endereco());
        sacado.addEndereco(enderecoSac);
        
        
        SacadorAvalista sacadorAvalista = new SacadorAvalista(nomeSacadorAvalista, cpfCNPJSacadorAvalista);//"JRimum Enterprise", "00.000.000/0001-91");
        // Informando o endere�o do sacador avalista.
        Endereco enderecoSacAval = configuraEnderecoSacAval(new Endereco());
        sacadorAvalista.addEndereco(enderecoSacAval);
        

        // Informando dados sobre a conta banc�ria do t�tulo.
        ContaBancaria contaBancaria = configuraBancaria( new ContaBancaria(BancosSuportados.BANCO_DO_BRASIL.create()));
        
        
        Titulo titulo = new Titulo(contaBancaria, sacado, cedente, sacadorAvalista);
        titulo.setNumeroDoDocumento(numeroDocumento);//"123456");
        
        
        titulo.setNossoNumero(nossoNumero);//"99345678912");// verificar nosso numero
        //titulo.setDigitoDoNossoNumero("5");
        
        titulo.setValor(BigDecimal.valueOf(0.5));
        
        titulo.setDataDoDocumento(new Date());
        titulo.setDataDoVencimento(new Date());
        titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
        
        titulo.setAceite(EnumAceite.A);
      
        titulo.setDesconto(new BigDecimal(0));
        titulo.setDeducao(BigDecimal.ZERO);
        titulo.setMora(BigDecimal.ZERO);
        titulo.setAcrecimo(BigDecimal.ZERO);
        titulo.setValorCobrado(BigDecimal.ZERO);
        
        
        
        
        Boleto boleto = new Boleto(titulo);
                
        boleto.setLocalPagamento("Pag�vel "/*preferencialmente na Rede X ou */+"em " +
                        "qualquer Banco at� o Vencimento.");
        /*boleto.setInstrucaoAoSacado("Senhor sacado, sabemos sim que o valor " +
                        "cobrado n�o � o esperado, aproveite o DESCONT�O!");
        boleto.setInstrucao1("PARA PAGAMENTO 1 at� Hoje n�o cobrar nada!");
        boleto.setInstrucao2("PARA PAGAMENTO 2 at� Amanh� N�o cobre!");
        boleto.setInstrucao3("PARA PAGAMENTO 3 at� Depois de amanh�, OK, n�o cobre.");
        boleto.setInstrucao4("PARA PAGAMENTO 4 at� 04/xx/xxxx de 4 dias atr�s COBRAR O VALOR DE: R$ 01,00");
        boleto.setInstrucao5("PARA PAGAMENTO 5 at� 05/xx/xxxx COBRAR O VALOR DE: R$ 02,00");
        boleto.setInstrucao6("PARA PAGAMENTO 6 at� 06/xx/xxxx COBRAR O VALOR DE: R$ 03,00");
        boleto.setInstrucao7("PARA PAGAMENTO 7 at� xx/xx/xxxx COBRAR O VALOR QUE VOC� QUISER!");
        boleto.setInstrucao8("AP�S o Vencimento, Pag�vel Somente na Rede X.");*/
        
        
        
        boletoViewer = new BoletoViewer(boleto);

//        File arquivoPdf = boletoViewer.getPdfAsFile("C:\\Users\\Victor Coutinho\\BoletoBB2.pdf");
        //boletoViewer.getpdf
        return boletoViewer;
    }
    
    public void gerarBoletoPDF(String caminho){
    	@SuppressWarnings("unused")
		File arquivoPdf = boletoViewer.getPdfAsFile(caminho);
    }


    public GeradorBoleto(Endereco enderecoSac, Sacado sacado) {
        this.enderecoSac = enderecoSac;
        this.sacado = sacado;
    }

    
    
    
    private ContaBancaria configuraBancaria(ContaBancaria contaBancaria) {
        // Informando dados sobre a conta banc�ria do t�tulo.
        contaBancaria.setNumeroDaConta(new NumeroDaConta(25427, "4"));
        contaBancaria.setCarteira(new Carteira(17));
        contaBancaria.setAgencia(new Agencia(0253, "4"));
        return contaBancaria;
    }
    
    private Endereco configEnderecoSac(Endereco enderecoSac){
        enderecoSac.setUF(this.enderecoSac.getUF());
        enderecoSac.setLocalidade(this.enderecoSac.getLocalidade());
        enderecoSac.setCep(this.enderecoSac.getCEP());
        enderecoSac.setBairro(this.enderecoSac.getBairro());
        enderecoSac.setLogradouro(this.enderecoSac.getLogradouro());
        enderecoSac.setNumero(this.enderecoSac.getNumero());
        return enderecoSac;
    }
    
    private Endereco configuraEnderecoSacAval(Endereco enderecoSacAval){
        enderecoSacAval.setUF(UnidadeFederativa.PA);
        enderecoSacAval.setLocalidade("Bras�lia");
        enderecoSacAval.setCep(new CEP("59000-000"));
        enderecoSacAval.setBairro("Grande Centro endere�o de quem recebe o valor");
        enderecoSacAval.setLogradouro("Rua Eternamente Principal");
        enderecoSacAval.setNumero("001");
        return enderecoSacAval;
    }
    
    public Sacado configuraSacado(String nome, String cpf){
        sacado = new Sacado(nome, cpf);
        return sacado;
    }
    
    
    public  Endereco configuraEnderecoSac(String cidade, String cep, String bairro, String logradouro, String numero, UnidadeFederativa uf){ 
        enderecoSac.setUF(uf);
        enderecoSac.setLocalidade(cidade);
        enderecoSac.setCep(new CEP(cep));
        enderecoSac.setBairro(bairro);
        enderecoSac.setLogradouro(logradouro);
        enderecoSac.setNumero(numero);
        return enderecoSac;
    }
    
    
    public static void main(String args[]){
        //gerar();
        GeradorBoleto geradorBoleto= new GeradorBoleto();
        geradorBoleto.configuraSacado("Victor ", "904.352.142-68");
        geradorBoleto.configuraEnderecoSac("Belem", "66035-170", "Nazar�", "Av. Nazar� esque benjamin", "405", UnidadeFederativa.PA);
        geradorBoleto.gerar("Rita de Cassia Oliveira dos Santos", "223.032.782-87", "000001","001");
        geradorBoleto.gerarBoletoPDF("/home/ojneto/BoletoBB6.pdf");
        

        //testes para gera��o de nome de arquivo 
        System.out.println(Math.random()*1000);
		/*try {
             new PrintWriter (new BufferedWriter(new FileWriter(new File("")), 1*1024*1024));
        } catch (IOException ex) {
            Logger.getLogger(GeradorBoleto.class.getName()).log(Level.SEVERE, null, ex);
        }*
        // validador de e-mail
        if("a@sas.asdsa".matches("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$")){
            System.out.println("email valido");
        }else
            System.out.println("email INvalido");*/
    	
    	
    }
    
}



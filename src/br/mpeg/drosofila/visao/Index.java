package br.mpeg.drosofila.visao;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import br.mpeg.drosofila.controle.PapelControle;
import br.mpeg.drosofila.controle.ParticipanteControle;
import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.controle.MantemConexao;
import br.mpeg.drosofila.util.mail.EnvioEmail;
import br.mpeg.drosofila.util.visao.NavegacaoJSF;

@SessionScoped
@ManagedBean
public class Index implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private String email;
	private String email2;
	private String senha;
	private String senha2;
	private int indiceAbaAtual;
	public static final int INDICE_ABA_RESUMO = 1;
	public static final int INDICE_ABA_PAGAMENTO = 3;
	public static final int INDICE_ABA_PARTICIPANTE = 0;
	
	private String urlWebSiteEvento;


	public void mudancaAba(TabChangeEvent event){
		String id = event.getTab().getId();
	    if (id.equals("dados-participante")) {
	    	setIndiceAbaAtual(0);
	    } else if (id.equals("dados-resumos")) {
	    	setIndiceAbaAtual(2);
	    } else if (id.equals("dados-compras")) {
	    	setIndiceAbaAtual(3);
	    }else if (id.equals("pagamento")) {
	    	setIndiceAbaAtual(4);
	    }
	    

	}
	
	public void onChange(TabChangeEvent event) {
		String indiceAtual  = (String) event.getComponent().getAttributes().get("activeIndex");
		System.out.println(indiceAtual);
	}

	
	public String getTitulo() {
		nome = email = email2 = senha = senha2 = "";
		return "Bem vindo ao Cadastro para evento de Congresso de Gest�o de Pessoas";
	}
	
	
	private void iniciaVerificacaoConexao(){
		if(!MantemConexao.iniciado)
			 new MantemConexao().start();
		
	}
	
	
	public boolean loginServlet(){
		Usuario logado;
		UsuarioControle controle = new UsuarioControle();
		logado = controle.logar(email, senha);
		
		try{
			Contexto.getSessao().setAttribute("Usuario", logado);	
		}catch (Exception e) {		
			e.printStackTrace();
		}
		
		if(logado !=null || !logado.equals(null) || logado.getEmail()!=null){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	public String login(){
		Usuario logado;
		if(!email.trim().equals("") && !senha.trim().equals("")){
			UsuarioControle controle = new UsuarioControle();
			logado = controle.logar(email, senha);
		}else
			logado=null;
			
		
			
		try{
			if(logado !=null || !logado.equals(null) || logado.getEmail()!=null){
				Contexto.getSessao().setAttribute("Usuario", logado);
				if(logado.isAdministrador()){
					iniciaVerificacaoConexao();
					Map<String, String> emails = new HashMap<String, String>();
					emails.put("oscar.chamma@gmail.com", "Oscar Chamma ");
					String texto = "administrador logado" +new Date();
					String assunto ="assunto";
					
					//EnvioEmail.emailDeTexto(emails, texto, assunto);
					EnvioEmail e = new EnvioEmail();
					e.emailDeTexto(emails, texto, assunto);	
				}
				//System.out.println("sucesso");
				return "sucesso";
			}
			else{
				System.out.println("falha");
				insereMensagemContextoMensagemWeb("Nome do usuario e/ou senha incorretos.", "Nome do usuario e/ou senha incorretos.");
			}
		}catch (Exception e) {
			insereMensagemContextoMensagemWeb("Nome do usuario e/ou senha incorretos.", "Nome do usuario e/ou senha incorretos.");
			e.printStackTrace();
		}
		
		return "errologin";//"errologin";
	}
	
	/*public void cadastrar(ActionEvent actionEvent) { 
		String x =cadastro();
		if(x == null){
			FacesContext context = FacesContext.getCurrentInstance();  
	          
			String primeiroNome="";
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
			
			
			
			
	        context.addMessage(null, new FacesMessage("Sucesso", "Olá, "+primeiroNome));  
		}
	}*/
	
	public String cadastrar() { 
		try{
			if(!email.trim().equals(email2)){
				insereMensagemContextoMensagemWeb("E-mail não coincide com confirmação de e-mail ", "E-mail não coincide com confirmação de e-mail ");
				return "";
			}
			if(!senha.trim().equals(senha2)){
				insereMensagemContextoMensagemWeb("Senha não coincide com confirmação de senha ", "Senha não coincide com confirmação de senha");
				return "";
			}
			
			
			UsuarioControle usuarioControle = new UsuarioControle();
			if( usuarioControle.pesquisaPorEmail(email)!=null){
				insereMensagemContextoMensagemWeb("E-mail já foi cadastrado para este evento", "E-mail já foi cadastrado para este evento");
				return "";
			}
			if(usuarioControle.findByName(nome) !=null){
				insereMensagemContextoMensagemWeb("O nome inserido já foi cadastrado para este evento", "O nome inserido já foi cadastrado para este evento");
				return "";
			}
		
		}catch (Exception e) {
			insereMensagemContextoMensagemWeb("Desculpe, ocorreu algum erro no sistema", "Erro de banco\n detalhes: "+e.getMessage());
		}
		
		String x =cadastro();
		if(x == null){
			FacesContext context = FacesContext.getCurrentInstance();  
	          
			String primeiroNome="";
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
			return "";
			
	        //context.addMessage(null, new FacesMessage("Sucesso", "Olá, "+primeiroNome));  
		}
		
		return x;
	}
	
	private void insereMensagemContextoMensagemWeb(String mensagem, String detalhe){
		FacesMessage fm = new FacesMessage();
		fm.setDetail(mensagem);
		fm.setSummary(detalhe);
		fm.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
	
	public String logOut(){
		Contexto.getSessao().invalidate();
		//NavegacaoJSF.redirecionar("http://www.yahoo.com.br/");
		return "sair";
	}
	
	public String cadastro(){
		Usuario cadastro;
		UsuarioControle controle = new UsuarioControle();
		PapelControle controlePapel = new PapelControle();
		if(email.trim().equals(email2.trim()) && senha.equals(senha2)){
			cadastro = new Usuario(email, nome, senha);
			
			List<Papel> lista = new ArrayList<Papel>();
			lista.add(controlePapel.findByName("cadastrado"));
			cadastro.setPapeis(lista);
			controle.save(cadastro);
			Contexto.getSessao().setAttribute("Usuario", cadastro);
			/*EnvioEmail emailx = new EnvioEmail();
			Map<String, String> emails = new HashMap<String, String>();
			emails.put(getEmail(), nome);
			StringBuilder texto = new StringBuilder();
			texto.append("Prezado ").append(nome)
			.append(", \n \n Obrigado por realizar o seu cadastro Congresso de gest�o de pessoas, aguardamos a finaliza��o do seu cadastro e posterior pagamento para efetivar sua inscri��o.")
			.append("\n \n Abaixo segue os dados do seu cadastro:")
			.append("\n \n email : ").append(email).append("\n ").append("senha : ").append(senha);
			
			
			emailx.setComissaoOrganizadora(true);
			emailx.emailDeTexto(emails, texto.toString(), "Congresso de Gest�o de Pessoas");*/
			return "sucesso";
		} if (email.trim().equals(email2.trim())) {
			String mensagem = "os Emails n�o s�o identicos";
			if(senha.equals(senha2)){
				mensagem = mensagem + "e as senhas n�o coincidem";
			}
			FacesMessage fm = new FacesMessage();
			fm.setDetail(mensagem);
			fm.setSummary("ERROR");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return null;
			
		} if(senha.equals(senha2)){
			String mensagem = "as senhas nãoo coincidem";
			if(email.trim().equals(email)){
				mensagem = mensagem + "e os emails n�o coincidem";
			}
			FacesMessage fm = new FacesMessage();
			fm.setDetail(mensagem);
			fm.setSummary("ERROR");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return null;
		}else{
			System.out.println("entrou aqui");
			return null;
		}
		
	}
	 
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}


	public int getIndiceAbaAtual() {
		return indiceAbaAtual;
	}


	public void setIndiceAbaAtual(int indiceAbaAtual) {
		this.indiceAbaAtual = indiceAbaAtual;
	}

	public String getUrlWebSiteEvento() {
		urlWebSiteEvento = RequisicaoLogin.urlWebSiteEvento;
		return urlWebSiteEvento;
	}

	public void setUrlWebSiteEvento(String urlWebSiteEvento) {
		this.urlWebSiteEvento = urlWebSiteEvento;
	}

	
	

}

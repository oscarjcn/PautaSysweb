package br.mpeg.drosofila.visao;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import br.mpeg.drosofila.controle.ParticipanteControle;
import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.mail.EnvioEmail;
import br.mpeg.drosofila.util.visao.MensagemJSF;

@ManagedBean
@RequestScoped
public class AdministraSenhaUsuario {
	
	private String senha;
	private String email;
	private Participante participante;
	private Usuario usuario;
	private String confirmaSenha;
	private String 	senhaAnterior;
	
	
	public String enviaEmailComNovaSenha(String nome, String emailParticipante, String emailUsuario, String novaSenha){
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(emailParticipante.trim(), nome);
		
		if(!emailParticipante.trim().equals(emailUsuario.trim())){
			emails.put(emailUsuario.trim(), nome);
		}
		
		
		StringBuilder texto = new StringBuilder();
		texto.append("Ola ");
		texto.append(nome);
		texto.append("\n \t Foi feito um pedido de alteraçãoo de senha atraves do seu e-mail. Por este motivo estamos lhe enviando uma nova senha. ");
		texto.append("\n \t Login: "+emailUsuario);
		texto.append("\n \t Nova senha: ");
		texto.append(novaSenha);
		texto.append("\n\n \t Você pode alterar sua senha na Ã¡rea  ");
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "Alteração senha Congresso de gestão de pessoas");
		return "";
	}
	
	public String gerarAlterarEnviarSenha(){
		String novaSenha = gerarSenha();
		if(!alterarSenha(novaSenha, email)){
			MensagemJSF.exibeMensagemAlerta("E-mail utilizado não foi cadastrado", "E-mail invalido!");
			return null;
		}
			
		enviaEmailComNovaSenha(participante.getNome(), participante.getEmail(), usuario.getEmail(), novaSenha);
		MensagemJSF.exibeMensagemInfo("Sua nova senha foi enviada para seu e-mail", "Sua nova senha foi enviada para seu e-mail!");
		return null;
	}
	
	private boolean alterarSenha(String novaSenha, String emailParticipante){
		ParticipanteControle participanteControle = new ParticipanteControle();
		participante = participanteControle.pesquisaPorEmail(emailParticipante);
		if(participante==null)
			return false;
		UsuarioControle usuarioControle = new UsuarioControle();
		usuario = usuarioControle.atualizaSenha(participante.getEmail(), novaSenha);
		if(usuario==null)
			return false;
		
		return true;
	}
	
	public String alteraSenha(){
		Usuario usuario = Contexto.getUsuario();
		UsuarioControle controle = new UsuarioControle();
		Usuario logado = controle.logar(usuario.getEmail(), senhaAnterior);
		if(logado==null){
			MensagemJSF.exibeMensagemAlerta("Senha atual não encontrada", "Senha atual não encontrada");
			return "";
		}
		if(!senha.equals(confirmaSenha)){
			MensagemJSF.exibeMensagemAlerta("Senha diferente da confirmação de senha", "Senha diferente da confirmação de senha");
			return "";
		}
		
		if(senha.equals(senhaAnterior)){
			MensagemJSF.exibeMensagemAlerta("A nova senha é igual a senha que você esta utilizando.", "A nova senha é igual a senha que você esta utilizando.");
			return "";
		}
			
		alterarSenha(senha, usuario.getEmail());
		MensagemJSF.exibeMensagemInfo("Senha alterada com sucesso", "Senha alterada com sucesso");
		return "";
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getSenhaAnterior() {
		return senhaAnterior;
	}

	public void setSenhaAnterior(String senhaAnterior) {
		this.senhaAnterior = senhaAnterior;
	}

	public String gerarSenha() {
		Random r = new Random();
		return  Integer.toString(Math.abs(r.nextInt()), 36)+Integer.toString(Math.abs(r.nextInt()), 36);
	}
	
	
	public static void main(String[] args) {
		AdministraSenhaUsuario administraSenhaUsuario = new AdministraSenhaUsuario();
		administraSenhaUsuario.alterarSenha("123", "oscar.chamma@gmail.com");
	}

}

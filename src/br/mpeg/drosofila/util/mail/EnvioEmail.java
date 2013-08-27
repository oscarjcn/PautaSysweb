package br.mpeg.drosofila.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

public class EnvioEmail implements Runnable{

	private MailJava email;
	private String usuarioSMTP;
	private boolean comissaoOrganizadora;
	
	public EnvioEmail(MailJava email) {
		
	}
	
	public EnvioEmail() {
	}
	@Override
	public void run() {
		JavaMailSender enviador = new JavaMailSender();
		
	//	MailJava email = 
		//	new MailJava();
	
		
		
	//	Map<String, String> map = new HashMap<String, String>();
			   //     map.put("victorcoutinho1985@gmail.com", "Email Victor Gmail");
			  //      map.put("coutinho.victor@hotmail.com", "Email Victor Hotmail");
			  //      map.put("victorcoutinho2004@yahoo.com.br", "Email Victor yahoo");
			 
			    //    email.setToMailsUsers(map);
		try {
			enviador.senderMail(email);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private  void emailDeHtml(Map<String,String> emails, String texto, String assunto){
		email = new MailJava();
		 email.setToMailsUsers(emails);
		email.setSmtpHostMail("smtp.museu-goeldi.br");
		email.setSmtpPortMail("25");
		email.setSmtpAuth("true");
		email.setSmtpStarttls("true");
		email.setUserMail("simposiodrosophila@museu-goeldi.br");
		email.setFromNameMail("Congresso de gest„o de pessoas");
		email.setPassMail("simposiodrosophila18072011");
		
		email.setCharsetMail("ISO-8859-1");
		email.setSubjectMail(assunto);
		email.setBodyMail(htmlMessage());
		email.setTypeTextMail(MailJava.TYPE_TEXT_HTML);
		//Runnable thread = new EnvioEmail(email);
		new Thread(this).start();
	}
	
	public void emailDeTexto3(Map<String,String> emails, String texto, String assunto){
		email = new MailJava();
		email.setToMailsUsers(emails);
		email.setSmtpHostMail("smtp.pautapromocoes.com.br");
		email.setSmtpPortMail("587");
		email.setSmtpAuth("true");
		email.setSmtpStarttls("true");
		email.setUserMail("pautapromocoes@pautapromocoes.com.br");
		email.setFromNameMail("Pauta");
		email.setPassMail("f4m4t6u8");
		email.setCharsetMail("ISO-8859-1");
		email.setSubjectMail(assunto);
		email.setBodyMail(texto);
		email.setTypeTextMail(MailJava.TYPE_TEXT_PLAIN);
		//Runnable thread = new EnvioEmail(email);
		new Thread(this).start();
	}
	
	private void configuraEmailMuseu(Map<String,String> emails, String texto, String assunto){
		email = new MailJava();
		 email.setToMailsUsers(emails);
		email.setSmtpHostMail("smtp.museu-goeldi.br");
		email.setSmtpPortMail("25");
		email.setSmtpAuth("true");
		email.setSmtpStarttls("false");
		email.setUserMail("simposiodrosophila@museu-goeldi.br");
		email.setFromNameMail("Museu Paraense Emilio Goeldi - MPEG (N√£o responda para este e-mail)");
		email.setPassMail("simposiodrosophila18072011");
		email.setCharsetMail("ISO-8859-1");
		email.setSubjectMail(assunto);
		email.setBodyMail(texto);
		email.setTypeTextMail(MailJava.TYPE_TEXT_PLAIN);
		//Runnable thread = new EnvioEmail(email);
		new Thread(this).start();
	}
	
	public void emailDeTexto(Map<String,String> emails, String texto, String assunto){
		email = new MailJava();
		email.setToMailsUsers(emails);
		email.setSmtpHostMail("smtp.pautapromocoes.com.br");//smtp-web.kinghost.net");
		email.setSmtpPortMail("587");
		email.setSmtpAuth("true");
		email.setSmtpStarttls("true");
		if(comissaoOrganizadora){
			email.setUserMail("pautapromocoes@pautapromocoes.com.br");//("c.organizadora.cbc@gmail.com");
			email.setFromNameMail("Comiss„oo Organizadora Congresso de gest„o de pessoas");
			email.setPassMail("f4m4t6u8");//"("@belem2012@");
		}else{
			email.setUserMail("pautapromocoes@pautapromocoes.com.br");//("cc.viicbc2012@gmail.com");
			email.setFromNameMail("Comiss„o Organizadora Congresso de gest„o de pessoas");
			email.setPassMail("f4m4t6u8");//("cbcbelem");				
		}
		email.setCharsetMail("UTF-8");
		email.setSubjectMail(assunto);
		email.setBodyMail(texto);
		email.setTypeTextMail(MailJava.TYPE_TEXT_HTML);
		//Runnable thread = new EnvioEmail(email);
		new Thread(this).start();
	}
	public void emaildeTexto(MailJava email, String texto){
		email.setTypeTextMail(MailJava.TYPE_TEXT_PLAIN);
		email.setBodyMail(texto);
		//Runnable thread = new EnvioEmail(email);
		new Thread(this).start();
		
	}
	public void envioDeHtml(MailJava email, String url, String texto){
		
	}
	
	private static String htmlMessage() {
			        return 
			"<html>\n" +
			"\t<head>\n" +
			"\t\t<title>Email no formato HTML com Javamail!</title> \n" +
			"\t</head>\n" +
			"\t<body>\n" +
			"\t\t<div style='background-color:orange; width:28%; height:100px;'>\n" +
			"\t\t\t<ul>\n" +
			"\t\t\t\t<li>Leia o novo tutorial JavaMail do Programando com Java.</li>\n" +
			"\t\t\t\t<li>Aprenda como enviar emails com anexos.</li>\n" +
			" \t\t\t\t<li>Aprenda a enviar emails em formato texto simples ou html.</li> \n" +
			"\t\t\t\t<li>Aprenda como enviar seu email para mais de um destin√°tario.</li>\n" +
			"\t\t\t</ul>\n" +
			"\t\t\t<p>Visite o blog \n" +
			"\t\t\t\t<a href='http://mballem.wordpress.com/'>Programando com Java</a>\n" +
			"\t\t\t</p>\n" +
			"\t\t</div>\t\n" +
			"\t\t<div style='width:28%; height:50px;' align='center'>\n" +
			"\t\t\tDownload do JavaMail<br/>\n" +
			"\t\t\t<a href='http://www.oracle.com/technetwork/java/javaee/index-138643.html'>\n" +
			"\t\t\t\t<img src='http://www.oracleimg.com/admin/images/ocom/hp/oralogo_small.gif'/>\n" +
			"\t\t\t</a> \n" +
			"\t\t</div>\t\t\n" +
			"\t</body> \n" +
			"</html>";
			    }
	
	public MailJava getEmail() {
		return email;
	}
	public void setEmail(MailJava email) {
		this.email = email;
	}
	public static void main(String[] args) {
		Map<String, String> emails = new HashMap<String, String>();
		emails.put("oscar.chamma@gmail.com", "Oscar Chamma ");
		String texto = "teste";
		String assunto ="assunto";
		
		//EnvioEmail.emailDeTexto(emails, texto, assunto);
		EnvioEmail e = new EnvioEmail();
		e.emailDeTexto(emails, texto, assunto);
	}

	public boolean isComissaoOrganizadora() {
		return comissaoOrganizadora;
	}

	public void setComissaoOrganizadora(boolean comissaoOrganizadora) {
		this.comissaoOrganizadora = comissaoOrganizadora;
	}
	

}

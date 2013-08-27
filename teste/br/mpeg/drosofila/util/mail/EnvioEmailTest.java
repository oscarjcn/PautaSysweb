package br.mpeg.drosofila.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;


public class EnvioEmailTest implements Runnable{
	private MailJava email;
	
	public void emailDeTexto(Map<String,String> emails, String texto, String assunto){
		email = new MailJava();
		email.setToMailsUsers(emails);
		email.setSmtpHostMail("smtp.gmail.com");
		email.setSmtpPortMail("587");
		email.setSmtpAuth("true");
		email.setSmtpStarttls("true");
		
		email.setUserMail("oscar.chamma@gmail.com");
		email.setFromNameMail("Comissão Organizadora VII CBC");
		email.setPassMail("tisu40``basa");
		
		email.setCharsetMail("UTF-8");
		email.setSubjectMail(assunto);
		email.setBodyMail(texto);
		email.setTypeTextMail(MailJava.TYPE_TEXT_HTML);
		//Runnable thread = new EnvioEmail(email);
		new Thread(this).start();
	}
	public static void main(String[] args) {
		EnvioEmailTest emailTest = new EnvioEmailTest();
		Map<String, String> mapa = new HashMap<String, String>();
		mapa.put("oscar.chamma@gmail.com", "Oscar");
		emailTest.emailDeTexto(mapa, "<h2> teste titulo</h2> ação", "Assunto");
	}
	
	@Override
	public void run() {
		JavaMailSender enviador = new JavaMailSender();
		
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

}

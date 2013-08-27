package br.mpeg.drosofila.util.mail;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Victor Coutinho
 * @since 15/03/2011
 * @version 1.0
 * 
 */
public class MailJava {
	// indica se o formato de texto seria texto ou html
	
	/**
	 * indica se o formato de texto seria texto ou html
	 */
	public static final String TYPE_TEXT_PLAIN = "text/plain";
	public static final String TYPE_TEXT_HTML = "text/html";
	/**
	 * indica qual seria o servidor de email(gmail, hotmail...)
	 */
	
	private String smtpHostMail; // indica qual seria o servidor de email(gmail, hotmail...)
	private String smtpPortMail; // indica a porta de acesso ao servidor
	private String smtpAuth; // indica que a necessidade de autenticação no servidor(true ou false)
	private String smtpStarttls; // indica ao servidor que ele estão recebendo uma conexão segura
	private String fromNameMail; // nome do remetente do email
	private String userMail; // email do remetente
	private String passMail; // senha do email do remetente
	private String subjectMail; // assunto do email
	private String bodyMail; // corpo do email, onde estarrá o texto da mensagem
	private Map<String, String> toMailsUsers; // lista com email e nome dos destinatários
	private List<String> fileMails; // lista contendo os arquivos anexos
	private String charsetMail; // charset, no caso de html é necessário
	private String typeTextMail; // tipo do formato da mensagem, texto ou html

	
	public MailJava() {

	}
	
	public MailJava(String smtpHostMail, String smtpPortMail, String smtpAuth,String smtpStarttls, String fromNameMail, String userMail,
			String passMail, String subjectMail, String bodyMail,Map<String, String> toMailsUsers, List<String> fileMails,
			String charsetMail, String typeTextMail) {
		super();
		this.smtpHostMail = smtpHostMail;
		this.smtpPortMail = smtpPortMail;
		this.smtpAuth = smtpAuth;
		this.smtpStarttls = smtpStarttls;
		this.fromNameMail = fromNameMail;
		this.userMail = userMail;
		this.passMail = passMail;
		this.subjectMail = subjectMail;
		this.bodyMail = bodyMail;
		this.toMailsUsers = toMailsUsers;
		this.fileMails = fileMails;
		this.charsetMail = charsetMail;
		this.typeTextMail = typeTextMail;
	}

	// gere os métodos getters and setters
	
	
	public String getSmtpHostMail() {
		return smtpHostMail;
	}

	public void setSmtpHostMail(String smtpHostMail) {
		this.smtpHostMail = smtpHostMail;
	}

	public String getSmtpPortMail() {
		return smtpPortMail;
	}

	public void setSmtpPortMail(String smtpPortMail) {
		this.smtpPortMail = smtpPortMail;
	}

	public String getSmtpAuth() {
		return smtpAuth;
	}

	public void setSmtpAuth(String smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public String getSmtpStarttls() {
		return smtpStarttls;
	}

	public void setSmtpStarttls(String smtpStarttls) {
		this.smtpStarttls = smtpStarttls;
	}

	public String getFromNameMail() {
		return fromNameMail;
	}

	public void setFromNameMail(String fromNameMail) {
		this.fromNameMail = fromNameMail;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getPassMail() {
		return passMail;
	}

	public void setPassMail(String passMail) {
		this.passMail = passMail;
	}

	public String getSubjectMail() {
		return subjectMail;
	}

	public void setSubjectMail(String subjectMail) {
		this.subjectMail = subjectMail;
	}

	public String getBodyMail() {
		return bodyMail;
	}

	public void setBodyMail(String bodyMail) {
		this.bodyMail = bodyMail;
	}

	public Map<String, String> getToMailsUsers() {
		return toMailsUsers;
	}

	public void setToMailsUsers(Map<String, String> toMailsUsers) {
		this.toMailsUsers = toMailsUsers;
	}

	public List<String> getFileMails() {
		return fileMails;
	}

	public void setFileMails(List<String> fileMails) {
		this.fileMails = fileMails;
	}

	public String getCharsetMail() {
		return charsetMail;
	}

	public void setCharsetMail(String charsetMail) {
		this.charsetMail = charsetMail;
	}

	public String getTypeTextMail() {
		return typeTextMail;
	}

	public void setTypeTextMail(String typeTextMail) {
		this.typeTextMail = typeTextMail;
	}

	public static String getTypeTextPlain() {
		return TYPE_TEXT_PLAIN;
	}

	public static String getTypeTextHtml() {
		return TYPE_TEXT_HTML;
	}



}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mpeg.drosofila.visao.padrao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * <p>
 * Classe responsável pelo acesso ao sistema verifica se usuário existe, se é
 * primeiro acesso, se está ativo, adiciona senha, recupera senha, realiza logoff. 
 * 
 * </p>
 * 
 * @author Victor Coutinho
 */
@ManagedBean
@SessionScoped
public class Autenticacao {
	//private Usuario user;
	//private UsuarioClient usuarioControle;
	private boolean primeiraVez = false;
	private String senhaConfirmacao = null;
	private String firstLogin = "";

	public Autenticacao() {
	}

	private String loginName;
	private String password;

	/*public String doLogin() {

		primeiraVez = senhaConfirmacao == null ? false : true;

		//usuarioControle = new UsuarioClient();
	//	user = usuarioControle.usuarioPrimeiroAcesso(loginName);
		
	//	boolean primeiroAcesso = user == null ? false : true;

		if (primeiraVez == false) {
			if (primeiroAcesso) {
				
				firstLogin = loginName;
				setPrimeiraVez(true);
				senhaConfirmacao = "";
				
				return null;
			}
		} else {

			if (loginName.equals(firstLogin)) {
				if (!password.equals(senhaConfirmacao)) {

					return null;
				}
			//	user.setEmail(loginName);
				System.out.println(GeraHash.criptografar(password));
			//	user.setSenha(GeraHash.criptografar(password));
			//	usuarioControle.inserirSenha(user);

			//	setLoginName(user.getEmail());
			//	setPassword(user.getSenha());
			} else {
				firstLogin = "";
			}
		}
		System.out.println(GeraHash.criptografar(password));
		Usuario usuario = usuarioControle.logar(loginName, password);

		if (usuario != null) {
			Contexto.getSessao().setAttribute("Usuario", usuario);

			return "/sistema/home.jsf";
		} else {
			FacesMessage fm = new FacesMessage();
			fm.setDetail("Wrong userName or password.");
			fm.setSummary("ERROR");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return null;
		}

	}*/

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public void setUsuarioControle(UsuarioClient usuarioControle) {
		this.usuarioControle = usuarioControle;
	}

	public UsuarioClient getUsuarioControle() {
		return usuarioControle;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}*/

	public boolean isPrimeiraVez() {
		return primeiraVez;
	}

	public void setPrimeiraVez(boolean primeiraVez) {
		this.primeiraVez = primeiraVez;
	}

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}

	public String getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}

}

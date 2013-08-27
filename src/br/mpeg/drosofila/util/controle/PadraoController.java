package br.mpeg.drosofila.util.controle;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 * 
 * @author Victor Coutinho
 * 
 * @param <T>
 */
public class PadraoController<T extends Serializable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean renderizaEdicao;
	private boolean renderizaExclusao;
	private boolean renderizaLista;
	private boolean renderizaCadastro = true;
	protected DataModel<T> modelo = null;
	protected IFacade<T> facade;
	protected String titulo = "Congresso de Gestão de Pessoas";
	protected String tituloAplicacao = "Congresso de Gestão de Pessoas";
	private String urlImagens;
	//protected Usuario usuario;
	protected T objeto;
	//protected Permissao permissao;
	//private String codigoAplicacao = "1";

	public PadraoController() {
		//usuario = (Usuario) Contexto.getSessao().getAttribute("Usuario");
	}

	public String getTitulo() {
		//inicioSalvar();
		//usuario = (Usuario) Contexto.getSessao().getAttribute("Usuario");
		/*if (usuario != null) {
			if (this.getClass().isAnnotationPresent(Cadastro.class)){
				 
				titulo = this.getClass().getAnnotation(Cadastro.class).tituloFormulario(); 
				if (verificaPermissao() != null) {
					HttpServletResponse response = (HttpServletResponse) Contexto.getExternalContext().getResponse();
					HttpServletRequest request = (HttpServletRequest) Contexto.getExternalContext().getRequest();
					String path = request.getSession().getServletContext().getContextPath();
					try {
						response.sendRedirect(path + "/sistema/falha.jsf");
					} catch (IOException ex) {
					} catch (IllegalStateException e) {
					}
				}}
		}*/

		// exibeCadastro();
		//exibeLista();
		try {
			setModelo(new ListDataModel<T>(facade.findAll()));
			//objeto = facade.instaceObject();
		} catch (NullPointerException e) {
			System.err.println(e.getStackTrace());
		}
		return titulo;
	}

	public String inicioSalvar() {
		objeto = facade.instaceObject();
		exibeCadastro();
		return null;
	}

	public String salvar() {
		modelo = null;
		facade.save(objeto);
		facade.instaceObject();
		return null;
	}

	public String inicioEditar() {
		objeto = getObjetoSelecionado();
		exibeEdicao();
		return null;
	}

	public String editar() {
		modelo = null;
		facade.update(objeto);
		return null;
	}

	public String inicioRemover() {
		objeto = getObjetoSelecionado();
		exibeExclusao();
		return null;
	}

	public String remover() {
		modelo = null;
		facade.delete(objeto);
		exibeLista();
		return null;
	}

	public String exibeCadastro() {
		//objeto = facade.instaceObject();
		setRenderizaCadastro(true);
		setRenderizaEdicao(false);
		setRenderizaLista(false);
		setRenderizaExclusao(false);
		return null;
	}

	public String exibeEdicao() {
		//objeto = modelo.getRowData();
		setRenderizaCadastro(false);
		setRenderizaEdicao(true);
		setRenderizaLista(false);
		setRenderizaExclusao(false);
		return null;
	}

	public String exibeLista() {
		setRenderizaCadastro(false);
		setRenderizaEdicao(false);
		setRenderizaLista(true);
		setRenderizaExclusao(false);
		return null;
	}

	public String exibeExclusao() {
		setRenderizaCadastro(false);
		setRenderizaEdicao(false);
		setRenderizaLista(false);
		setRenderizaExclusao(true);
		return null;
	}

	public T getObjetoSelecionado() {
		return modelo.getRowData();
	}

	public boolean isRenderizaEdicao() {
		return renderizaEdicao;
	}

	public void setRenderizaEdicao(boolean renderizaEdicao) {
		this.renderizaEdicao = renderizaEdicao;
	}

	public boolean isRenderizaLista() {

		return renderizaLista;
	}

	public void setRenderizaLista(boolean renderizaLista) {
		this.renderizaLista = renderizaLista;
	}

	public DataModel<T> getModelo() {
		if (modelo == null) {
			modelo = new ListDataModel<T>(facade.findAll());
		}
		return modelo;
	}

	public void setModelo(DataModel<T> modelo) {
		this.modelo = modelo;
	}

	public String getTituloAplicacao() {
		return tituloAplicacao;
	}

	public void setTituloAplicacao(String tituloAplicacao) {
		this.tituloAplicacao = tituloAplicacao;
	}

	/*public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}*/

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isRenderizaCadastro() {
		return renderizaCadastro;
	}

	public void setRenderizaCadastro(boolean renderizaCadastro) {
		this.renderizaCadastro = renderizaCadastro;
	}

	public boolean isRenderizaExclusao() {
		return renderizaExclusao;
	}

	public void setRenderizaExclusao(boolean renderizaExclusao) {
		this.renderizaExclusao = renderizaExclusao;
	}

	public T getObjeto() {
		return objeto;
	}

	public void setObjeto(T objeto) {
		this.objeto = objeto;
	}

	public void setFacade(IFacade<T> facade) {
		this.facade = facade;
	}

	public IFacade<T> getFacade() {
		return facade;
	}

	public String relatorio() {
		return null;
	}

	public String imprimir() {
		return null;
	}

	/*private String verificaPermissao() {
		PermissaoClient pc = new PermissaoClient(); 
		//PermissaoControle pc = new PermissaoControle();
		 permissao = pc.verificaPermissao(this, usuario, "2");

		if (permissao == null && !usuario.getEmail().equalsIgnoreCase("admin")) {
			return acessoNegado();
		} else {
			return null;
		}
	}*/

	/*private String acessoNegado() {
		
		 * HttpServletResponse response = (HttpServletResponse)
		 * Contexto.getExternalContext().getResponse();
		 * response.sendRedirect(returnPath()+ "/sistema/falha.jsf");
		 

		return "acessoNegado";
	}*/

	public static void addErrorMessages(String erro) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				erro, erro);
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, facesMsg);
	}
public String getUrlImagens() {
	urlImagens = Contexto.getUrlResumo();
	return urlImagens;
}
public void setUrlImagens(String urlImagens) {
	this.urlImagens = urlImagens;
}
}

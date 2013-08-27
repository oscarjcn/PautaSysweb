	package br.mpeg.drosofila.visao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.mpeg.drosofila.controle.AssociadoControle;
import br.mpeg.drosofila.controle.PapelControle;
import br.mpeg.drosofila.controle.ParticipanteControle;
import br.mpeg.drosofila.controle.api.IAssociadoControle;
import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.controle.PadraoController;
import br.mpeg.drosofila.util.mail.EnvioEmail;
import br.mpeg.drosofila.util.visao.MensagemJSF;

@ManagedBean
@SessionScoped
public class AdmAssociadoBean extends PadraoController<Associado> implements Serializable{
	
	private Associado associado;
	private List<Associado> associados;
	private IAssociadoControle associadoControle;
	private int abaAtual = 0;
	private PapelControle papelControle = new PapelControle();
	private SelectItem[] mensalidadeEmDia;
	private SelectItem[] participanteInvalido;
	
	public AdmAssociadoBean() {
		mensalidadeEmDia = createFilterOptionsInscricoes("Sim", "NÃ£o");
		participanteInvalido = createFilterOptionsInscricoes("InvÃ¡lido", "VÃ¡lido");
		associado = new Associado();
		associadoControle = new AssociadoControle();
		associados = associadoControle.findAll();
	}
	
	 private SelectItem[] createFilterOptionsInscricoes(String verdadeiro, String falso)  {
	        SelectItem[] options = new SelectItem[3];  
	        options[0] = new SelectItem("", "Select");
	        options[1] = new SelectItem("FALSE", falso);
	        options[2] = new SelectItem("TRUE", verdadeiro);
	  
	        return options;  
	 }
	 
	public String excluir(){
		ParticipanteControle participanteControle = new ParticipanteControle();
		Participante p = null;
		try{
			p = participanteControle.buscaPorNumeroAssociado(associado.getNumero());
		}catch (javax.persistence.NoResultException e) {}
		
		if(p!=null){
			MensagemJSF.exibeMensagemAlerta("Existe um participante associado a este registro de associado", "NÃ£o pode apagar este registro");
			return "";
		}
			
		associadoControle.delete(associado);
		return "";
	}
	
	public String invalidarCadastro(){
		associado.setInvalido(true);
		associado.setPagamentoEmDia(false);
		associadoControle.update(associado);
		return "";
	}
	
	public String cancelar(){
		associado = new Associado();
		exibeCadastro();
		return "";
	}
	
	public String cadastrar(){
		try {
			associadoControle.save(associado);
		} catch (Exception e) {
			MensagemJSF.exibeMensagemAlerta("NÃ¢o foi possivel cadastrar novo associado", "Ocorreu um erro ao tentar acadastrar associado");
			return "";
		}
		enviaEmailCOnfirmAssociadoTesoureiro();
		MensagemJSF.exibeMensagemInfo("OperaÃ§Ã£o realizada com sucesso!", "Associado salvo com sucesso!");
		associado = new Associado();
		return "";
	}
	
	public String atualizar(){
		exibeCadastro();
		try {
			associado.setInvalido(false);
			associadoControle.update(associado);
		} catch (Exception e) {
			MensagemJSF.exibeMensagemAlerta("NÃ¢o foi possivel atualizar novo associado", "Ocorreu um erro ao tentar atualizar associado");
			return "";
		}
		
		enviaEmailCOnfirmAssociadoTesoureiro();
		ParticipanteControle participanteControle = new ParticipanteControle();
		Participante participante = participanteControle.buscaPorCPF(associado.getCpf());
		if(participante!=null)
			enviaEmailConfirmacaoAssociadoPAssociado(participante.getEmail(), participante.getNome());
		
		MensagemJSF.exibeMensagemInfo("OperaÃ§Ã£o realizada com sucesso!", "Associado atualizado com sucesso!");
		associado = new Associado();
		return "";
	}
	
	private void enviaEmailCOnfirmAssociadoTesoureiro(){
		if(!associado.isPagamentoEmDia())
			return;
			
		List<Usuario> usuarios = papelControle.buscaUsuarios("tesoureiro");
		if(usuarios.size()>0){
			for(Usuario usuario: usuarios){
				enviaEmailConfirmacaoAssociado(usuario.getEmail(), usuario.getNome(), associado.getNome());
			}
		}
	}
	
	private void enviaEmailConfirmacaoAssociado(String emaildestinatario, String nomeDestinatario, String nomeParticipante){
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(emaildestinatario.trim(), nomeDestinatario);
		
		StringBuilder texto = new StringBuilder();
		texto.append("\n \t Olá, houve alteraçãoo no nível de sócio no sistema do Congresso de gestão de pessoas, por favor verifique.")
		.append("\n \t Nome do usuário: ")
		.append(nomeParticipante);
		
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "SÃ³cio adicionado - VII CBC");
		return ;
	}
	
	private void enviaEmailConfirmacaoAssociadoPAssociado(String emaildestinatario, String nomeDestinatario){
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(emaildestinatario.trim(), nomeDestinatario);
		
		StringBuilder texto = new StringBuilder();
		texto.append("\n \t Olá "+nomeDestinatario+".\n Confirmamos que sua anuidade de 2013 está em dia com a Sociedade, dessa"+
					"forma vocá já pode entrar na área de login do Congresso de gestão de pessoas e efetuar pagamento da sua inscrição (com"+
					"ou sem atividade adicional). No menu â curso poderá ver as atividades disponíveis para"+
					"inscrição, caso não prefira se inscrever em nenhuma dessas atividades, pode concretizar sua inscrição"+
					"no menu ˜Pagamento.");
		
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "SÃ³cio adicionado - VII CBC");
		return ;
	}
	
	
	public Associado getAssociado() {
		return associado;
	}
	public void setAssociado(Associado associado) {
		System.out.println(associado.getNome());
		this.associado = associado;
	}
	public List<Associado> getAssociados() {
		associados = associadoControle.findAll();
		return associados;
	}
	public void setAssociados(List<Associado> associados) {
		this.associados = associados;
	}

	public String exibeEdicao(){
		UIViewRoot uiViewRoot = FacesContext.getCurrentInstance().getViewRoot();
		HtmlInputText inputTextNome  = (HtmlInputText) uiViewRoot.findComponent("form_cadastro_associados:nome");
		inputTextNome.setValue(associado.getNome());
		
		HtmlInputText inputTextCpf  = (HtmlInputText) uiViewRoot.findComponent("form_cadastro_associados:cpf");
		inputTextCpf.setValue(associado.getCpf());
		
		HtmlInputText inputTextNumero  = (HtmlInputText) uiViewRoot.findComponent("form_cadastro_associados:numero");
		inputTextNumero.setValue(associado.getNumero());
		
		System.out.println(associado.getNome());
		setAbaAtual(0);
		super.exibeEdicao();
		return null;
	}

	public int getAbaAtual() {
		return abaAtual;
	}

	public void setAbaAtual(int abaAtual) {
		this.abaAtual = abaAtual;
	}

	public SelectItem[] getMensalidadeEmDia() {
		return mensalidadeEmDia;
	}

	public void setMensalidadeEmDia(SelectItem[] mensalidadeEmDia) {
		this.mensalidadeEmDia = mensalidadeEmDia;
	}

	public SelectItem[] getParticipanteInvalido() {
		return participanteInvalido;
	}

	public void setParticipanteInvalido(SelectItem[] participanteInvalido) {
		this.participanteInvalido = participanteInvalido;
	}
	
}

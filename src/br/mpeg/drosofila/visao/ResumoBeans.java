package br.mpeg.drosofila.visao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

//import sun.org.mozilla.javascript.annotations.JSFunction;

import br.mpeg.drosofila.controle.PapelControle;
import br.mpeg.drosofila.controle.ParticipanteControle;
import br.mpeg.drosofila.controle.ResumoControle;
import br.mpeg.drosofila.controle.StatusResumoControle;
import br.mpeg.drosofila.controle.TemaControle;
import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.controle.api.IResumoControle;
import br.mpeg.drosofila.controle.api.IStatusResumoControle;
import br.mpeg.drosofila.modelo.Comprovante;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.modelo.StatusResumo;
import br.mpeg.drosofila.modelo.Tema;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.persistencia.ResumoDao;
import br.mpeg.drosofila.persistencia.StatusResumoDao;
import br.mpeg.drosofila.persistencia.api.IStatusResumoDao;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.controle.PadraoController;
import br.mpeg.drosofila.util.mail.EnvioEmail;
import br.mpeg.drosofila.util.visao.ControleAcesso;
import br.mpeg.drosofila.util.visao.MensagemJSF;

/**
 * @author Victor Coutinho
 */
@ManagedBean
@SessionScoped
public class ResumoBeans extends PadraoController<Usuario> implements
		Serializable {

	private static final long serialVersionUID = 1L;
	private String nomeArquivoSelecionado;
	private List<Resumo> resumos = new ArrayList<Resumo>();
	private Resumo resumo;
	private Resumo resumoSelecionado;
	private byte[] arquivo;
	private ParticipanteControle participanteControle = new ParticipanteControle();
	private long tamanhoArquivo;
	private String conteudoResumo;
	private IStatusResumoDao statusResumoDao = new StatusResumoDao();
	private IStatusResumoControle statusResumoControle =  new StatusResumoControle();
	private IResumoControle resumoControle = new ResumoControle();
	private boolean enviado ;
	private List<Tema> temas;
	private StreamedContent streamedArquivoResumo;
	private int idEdicao;
	private Participante participante;
	private String comentarioResumo;
	
	static final private int  QUANTIDADE_MINIMA_REUMOS_ESPERADA = 2;
	private PapelControle papelControle = new PapelControle();

	private boolean atualizarResumo = false;
	
	public ResumoBeans() {
		participante = buscaUsuario().getParticipante();
		temas = new TemaControle().findAll();
	}

	public void atualizaConteudoResumo(){
		Index index = (Index) Contexto.getSessao().getAttribute("index");
		index.setIndiceAbaAtual(Index.INDICE_ABA_RESUMO);
	}
	
	public void atualizaResumo(ValueChangeEvent e){
		this.conteudoResumo = (String) e.getNewValue();
	}
	
	public void preparaAtualizaResumo(){
		atualizarResumo = true;
		conteudoResumo = resumo.getConteudo();
		resumo.setResumo(new byte[0]);
		System.out.println(resumo.getComentarioResumo()+"  -  "+resumo.getStatusResumo().getNome());
	}
	
	void carregarObjetos() {
		if(resumo == null)
		resumo = new Resumo();
		if(!atualizarResumo){
			resumo.setDemaisAutores(participante.getNomeDeAutor()+"<sup _moz_dirty=\"\">1*</sup>&ensp;");
			resumo.setEmails("<sup _moz_dirty=\"\">1</sup>"+participante.getSiglaDaInstituicao()+"; *"+participante.getEmail());
		}
		
		
		resumos = new ArrayList<Resumo>();
		try {
			if(objeto.getParticipante().getResumos()!=null)
				resumos = resumoControle.listaResumoParticipanteId(objeto.getParticipante().getId());
		} catch (NullPointerException e) {
			resumos = new ArrayList<Resumo>();
		}
		/*
		 * if (objeto.getParticipante().getResumos() == null) { resumos = new
		 * ArrayList<Resumo>(); } else { resumos =
		 * objeto.getParticipante().getResumos(); }
		 */
	}

	private Usuario buscaUsuario(){
		Usuario sessao = (Usuario) Contexto.getSessao().getAttribute("Usuario");
		objeto = new UsuarioControle().findById(sessao.getId());
		return objeto;
	}
	
	@Override
	public String getTitulo() {
		nomeArquivoSelecionado = "";
		/*Usuario sessao = (Usuario) Contexto.getSessao().getAttribute("Usuario");
		objeto = new UsuarioControle().findById(sessao.getId());*/
		objeto = buscaUsuario();
		
		try{
			System.out.println(objeto.getParticipante().getCpf());
		}catch (Exception e) {
			System.out.println("ainda nulo");
		}
		carregarObjetos();
		super.getTitulo();
		return "Resumos";
	}

	public void excluirResumo() {
		System.out.println("chegou aqui");
		ResumoDao rdao = new ResumoDao();
		Resumo resumo = rdao.findById(resumoSelecionado.getId());
		rdao.excluir(resumo);
	}

	public void atualizaConteudoResumoEdicao(){
		enviado = true;
		String tituloResumo = resumoSelecionado.getTitulo();
		resumoSelecionado.setDataSubmissao(new Date());
		resumoSelecionado.setStatusResumo(statusResumoControle
				.buscaStatusResumo(enviado? StatusResumoDao.STATUS_RESUMO_ENVIADO :StatusResumoDao.STATUS_RESUMO_PENDENTE));
		ResumoControle	resumoControle = new ResumoControle();
		resumoControle.update(resumoSelecionado);
		
	
		
		List<Usuario> usuarios = papelControle.buscaUsuarios("coordenador");
		if(usuarios.size()>0){
			for(Usuario usuario: usuarios){
				enviaEmailAdministradorResumos(usuario.getEmail(), objeto.getNome(), tituloResumo);
			}
		}
		
		MensagemJSF.exibeMensagemInfo("Salvo com sucesso", "Operação realizada com sucesso");
		enviado = false;
	}
	
	public static void main(String[] args) {
		StatusResumoControle statusResumoControle = new StatusResumoControle();
		Resumo resumoSelecionado = new  ResumoControle().findById(152); 
		System.out.println("resumo selecionado: "+resumoSelecionado.getTitulo());
		StatusResumo statusResumo = statusResumoControle.buscaStatusResumo(StatusResumoDao.STATUS_RESUMO_ENVIADO);
		System.out.println("\n\nstatus do resumo: "+statusResumo.getNome()+" - id status resumo: "+statusResumo.getId());
		resumoSelecionado.setStatusResumo(statusResumo);
		
		ResumoControle	resumoControle = new ResumoControle();
		resumoControle.update(resumoSelecionado);
	}
	
	private void enviaEmailAdministradorResumos(String emaildestinatario, String nomeParticipante, String titulo){
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(emaildestinatario.trim(), "Coordenador");
		
		StringBuilder texto = new StringBuilder();
		texto.append("\n \t Caro coordenador, houve o envio de um novo resumo para avaliação ao VII CBC")
		.append(", por favor verifique na área administrativa de seu cadastro e indique um avaliador.")
		.append("\n \t Nome do usuário: ")
		.append(nomeParticipante)
		.append("\n \t Título do resumo: ")
		.append(titulo);
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "Resumo enviado VII CBC");
		return ;
	}
	
	private void enviaEmailRevisorResumos(String emaildestinatario, String nomeParticipante, String titulo){
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(emaildestinatario.trim(), "Revisor");
		
		/*UsuarioControle usuarioControle = new UsuarioControle();
		List<Usuario> coordenadores =  usuarioControle.listaPorPapel("O");
		for(Usuario usuario: coordenadores){
			emails.put(usuario.getEmail(), "Coordenador");
		}*/
		
		StringBuilder texto = new StringBuilder();
		texto.append("\n <br /> \t Caro revisor(a), as correções de um resumo submetido para sua avaliação ao VII CBC foram efetuadas e o resumo reenviado")
		.append(", por favor verifique na área administrativa de seu cadastro para reavaliar o resumo abaixo especificado.")
		.append("<br />\n \t Lembramos que você pode indicar resumos para serem apresentados na forma oral, isto pode ser feito na mesma área de avaliação do resumo ao se clicar em 'Exibir detalhes'. ")
		.append("<p style='margin-left: 16'>\n \t Nome do usuário:</p> ")
		.append(nomeParticipante)
		.append("<p style='margin-left: 16'>\n \t Título do resumo: </p>")
		.append(titulo);
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "Resumo enviado VII CBC");
		return ;
	}
	
	
	private boolean verificaAutoresResumos(List<Resumo>resumos){
		boolean salvar = true;
		StringBuffer cpfsInvalidos = new StringBuffer();
		for(Resumo resumo: resumos){
			if(participanteControle.buscaPorCPF(resumo.getCpfAutor())==null){
				salvar=false;
				cpfsInvalidos.append(resumo.getCpfAutor()).append(", ");
			}
		}
		if(!salvar)
			MensagemJSF.exibeMensagemErro("Alguns CPFs de autores estão invalidos", "Os seguintes CPFs não foram cadastrados para o evento: "+cpfsInvalidos.toString());
		return salvar;
	}
	
	
	
	@Override
	public String editar() {

		/*if(!verificaAutoresResumos(resumos))
			return "";*/

		try {
			objeto.getParticipante().setResumos(resumos);
		} catch (NullPointerException e) {
			Participante p = new Participante();
			p.setEmail(objeto.getEmail());
			p.setNome(objeto.getNome());
			objeto.setParticipante(p);
			objeto.getParticipante().setResumos(resumos);
		}

		try {
			for (Resumo resumo : resumos) {
				if(resumo.getId()==null ){
					resumo.setParticipante(objeto.getParticipante());
					//System.out.println(resumo.getConteudo());
					resumoControle.save(resumo);
				}
			} 
			/*facade = new UsuarioControle();
			 new UsuarioControle().updateResumos(objeto);*/
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	public String atualizar() {

		/*if(!verificaAutoresResumos(resumos))
			return "";*/

	/*	try {
			objeto.getParticipante().setResumos(resumos);
		} catch (NullPointerException e) {
			Participante p = new Participante();
			p.setEmail(objeto.getEmail());
			p.setNome(objeto.getNome());
			objeto.setParticipante(p);
			objeto.getParticipante().setResumos(resumos);
		}

		try {
			for (Resumo resumo : resumos) {
				if(((int) resumo.getId())==((int)this.resumo.getId())){
					System.out.println(this.resumo.getId()+" titulo do resumo "+this.resumo.getTitulo());
					resumos.remove(resumo);
					resumos.add(this.resumo);
					this.resumo.setParticipante(objeto.getParticipante());
					ResumoControle resumoControle = new ResumoControle();
					resumoControle.update(this.resumo);
				}
			} 
			facade = new UsuarioControle();
			 new UsuarioControle().updateResumos(objeto);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}*/
		
		this.resumo.setParticipante(objeto.getParticipante());
		ResumoControle resumoControle = new ResumoControle();
		this.resumo.setId(idEdicao);
		resumoControle.update(this.resumo);
		
		return null;
	}
	
	/*public void excluirResumo(){
		ResumoControle resumoControle = new ResumoControle();
		resumoControle.delete(obj)
		
	}*/

	public StreamedContent baixarArquivoResumo(Resumo resumo){
		StreamedContent arquivoComprovante = null;
		InputStream arquivoInput;
		if(resumo!=null && resumo.getResumo()!=null){
			arquivoInput = new ByteArrayInputStream(resumo.getResumo());
			arquivoComprovante = new DefaultStreamedContent(arquivoInput, resumo.getTipoArquivo(), resumo.getCaminhoArquivo());
		}else{
			arquivoInput = null;
		}
		return arquivoComprovante;
	}
	
	public StreamedContent getStreamedArquivoResumo() {
		return streamedArquivoResumo =  baixarArquivoResumo(resumoSelecionado);
	}

	public void setStreamedArquivoResumo(StreamedContent streamedArquivoResumo) {
		this.streamedArquivoResumo = streamedArquivoResumo;
	}

	public void handleFileUpload(FileUploadEvent event) {
		if(resumo== null)
			resumo = new Resumo();
		resumo.setResumo(null);
		setNomeArquivoSelecionado(event.getFile().getFileName());
		System.out.println(event.getFile().getFileName());
		resumo.setResumo(event.getFile().getContents());
		System.out.println("\n\n\n tamanho do arquivo: "+event.getFile().getContents().length+" \n\n\n");
		resumo.setCaminhoArquivo(getNomeArquivoSelecionado());
		resumo.setTipoArquivo(event.getFile().getContentType());
		tamanhoArquivo = event.getFile().getSize();
		System.out.println("\n\n\ntamanho arquivo: "+tamanhoArquivo);	 
	}

	public String cancelar(){
		atualizarResumo = false;
		resumo = new Resumo();
		return "";
	}
	
	
	public String reinitResumo() {
		/*if(tamanhoArquivo<5*1000*1000){
			MensagemJSF.exibeMensagemErro("Arquivo é muito grande", "Por favor, envie um arquivo com tamanho menor que 4mb .");
			resumos.remove(resumo);
			return null;
		}*/

		resumos.add(resumo);
		if(resumo.getConteudo()==null || resumo.getConteudo().trim().equals("")){
			MensagemJSF.exibeMensagemErro("Conteudo do resumo é obrigatório", "Por favor insira conteudo no resumo clicando em 'Inserir resumo' e posteriormente em 'Atualizar texto do resumo'.");
			resumos.remove(resumo);
			return null;
		}
		
		//resumo.setConteudo(conteudoResumo);
		resumo.setCpfAutor(Contexto.getUsuario().getParticipante().getCpf());
		
		if(participanteControle.buscaPorCPF(resumo.getCpfAutor())==null){
			MensagemJSF.exibeMensagemErro("CPF de autor é invalido", "O CPF "+resumo.getCpfAutor()+" não foi cadastrado para o evento. ");
			resumos.remove(resumo);
			return null;
		}
		
		if(resumo.getResumo()==null || resumo.getResumo().length<1){
			MensagemJSF.exibeMensagemErro("Arquivo do resumo é obrigatório", "Por favor selecione um arquivo contendo o resumo clicando em 'Localizar resumo'.");
			resumos.remove(resumo);
			return null;
		}
		
		if(!atualizarResumo && resumoControle.listaResumoAutor(resumo.getCpfAutor()).size()>=QUANTIDADE_MINIMA_REUMOS_ESPERADA){
			MensagemJSF.exibeMensagemErro(
					"É permitido submeter até dois resumos por inscrição, sendo que o inscrito é o primeiro autor."
					,"É permitido submeter até dois resumos por inscrição, sendo que o inscrito é o primeiro autor.");
			resumos.remove(resumo);
			return null;
		}
		// TODO verificar aqui se status resumo re-enviado. 
		if(resumo.getStatusResumo()!=null && 
				resumo.getStatusResumo().getNome().trim().toLowerCase().equals(StatusResumoDao.STATUS_RESUMO_REVISADO.toLowerCase())){
			resumo.setStatusResumo(statusResumoControle.buscaStatusResumo(StatusResumoDao.STATUS_RESUMO_REENVIADO));
			
			enviaEmailRevisorResumos(resumo.getRevisor().getEmail(), resumo.getParticipante().getNome(), resumo.getTitulo());
		}else	
			resumo.setStatusResumo(statusResumoControle.buscaStatusResumo(StatusResumoDao.STATUS_RESUMO_PENDENTE));
	
		
		if(atualizarResumo){
			atualizar();
			atualizarResumo = false;
		}else{
			editar();
		}
		
		
		arquivo = null;
		nomeArquivoSelecionado = null;
		conteudoResumo = "";
		resumo = new Resumo();		
		return null;
	}

	public void setNomeArquivoSelecionado(String nomeArquivoSelecionado) {
		this.nomeArquivoSelecionado = nomeArquivoSelecionado;
	}

	public String getNomeArquivoSelecionado() {
		return nomeArquivoSelecionado;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public Resumo getResumo() {
		return resumo;
	}

	public void setResumo(Resumo resumo) {
		idEdicao = resumo.getId();
		System.out.println("Id do resumo que foi selecionado: "+resumo.getId());
		this.resumo = resumo;
	}

	public void setResumos(List<Resumo> resumos) {
		this.resumos = resumos;
	}

	public List<Resumo> getResumos() {
		if(resumos == null){
			resumos = new ArrayList<Resumo>();
		}
		return resumos;
	}

	public Resumo getResumoSelecionado() {
		return resumoSelecionado;
	}

	public void setResumoSelecionado(Resumo resumoSelecionado) {
		this.resumoSelecionado = resumoSelecionado;
	}

	public long getTamanhoArquivo() {
		return tamanhoArquivo;
	}

	public void setTamanhoArquivo(long tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}

	public String getConteudoResumo() {
		if(conteudoResumo==null || conteudoResumo.trim().equals(""))
			return null;
		return conteudoResumo;
	}

	public void setConteudoResumo(String conteudoResumo) {
		this.conteudoResumo = conteudoResumo;
	}

	public boolean isEnviado() {
		return enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	public List<Tema> getTemas() {
		return temas;
	}

	public void setTemas(List<Tema> temas) {
		this.temas = temas;
	}

	public boolean isAtualizarResumo() {
		return atualizarResumo;
	}

	public void setAtualizarResumo(boolean atualizarResumo) {
		this.atualizarResumo = atualizarResumo;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public String getComentarioResumo() {
		return comentarioResumo;
	}

	public void setComentarioResumo(String comentarioResumo) {
		this.comentarioResumo = comentarioResumo;
	}
	
}

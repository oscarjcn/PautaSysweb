package br.mpeg.drosofila.visao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.uol.pagseguro.properties.PagSeguroConfig;
import br.mpeg.drosofila.controle.AssociadoControle;
import br.mpeg.drosofila.controle.ComprovanteControle;
import br.mpeg.drosofila.controle.IndicadorControle;
import br.mpeg.drosofila.controle.PapelControle;
import br.mpeg.drosofila.controle.ParticipanteControle;
import br.mpeg.drosofila.controle.PedidoControle;
import br.mpeg.drosofila.controle.ProdutoControle;
import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.controle.api.IAssociadoControle;
import br.mpeg.drosofila.controle.api.IPedidoControle;
import br.mpeg.drosofila.controle.api.IUsuarioControle;
import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.modelo.Comprovante;
import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.controle.PadraoController;
import br.mpeg.drosofila.util.mail.EnvioEmail;
import br.mpeg.drosofila.util.visao.MensagemJSF;
import br.mpeg.drosofila.util.visao.NavegacaoJSF;
import br.mpeg.drosofila.visao.beans.auxiliares.InscricaoInfo;
import br.pauta.gerencia.compravenda.VendaPagueSeguro;

@ManagedBean
@SessionScoped
public class PagamentoBeans extends PadraoController<InscricaoInfo> implements Serializable {

	private IPedidoControle pedidoControle;
	private Usuario usuario;
	private List<InscricaoInfo>pedidosInscricao;
	private List<Pedido>pedidosCurso;
	private Participante participante;
	private IUsuarioControle usuarioControle;
	private List<ItemCompra> itensInscricao;
	private List<ItemCompra> itensMiniCurso;
	private boolean exibirMiniCurso = false;
	private boolean pagamentoMiniCursoQuitado = true;
	private String nomeArquivoSelecionado;
	private Comprovante comprovante;
	private Comprovante comprovante2;
	private Comprovante comprovanteAtividade;
	private ComprovanteControle comprovanteControle;
	private boolean visualizarComprovante = false;
	private boolean visualizarComprovEsco = false;
	private boolean visualizarComprovAtividade = false;
	private boolean associadoOkOuNaoSocio=true;
	private StreamedContent arquivoComprovante;
	private StreamedContent arquivoComprovanteEscolaridade;
	private StreamedContent arquivoComprovanteAtividade;
	private InscricaoInfo pedidoInscricao;
	public static final String TIPO_COMPROVANTE_PAGAMENTO = "PGTO";
	public static final String TIPO_COMPROVANTE_ESCOLARIDADE = "ESCO";
	public static final String TIPO_COMPROVANTE_ATIVIDADE = "ATIV";
	public float valorAtividade;
	public PapelControle papelControle = new PapelControle();
	VendaPagueSeguro pagueSeguro;

	
	public PagamentoBeans() {
		pagueSeguro = new VendaPagueSeguro();
		comprovante = new Comprovante();
		comprovante.setTipo(TIPO_COMPROVANTE_ATIVIDADE);
		comprovante2 = new Comprovante();
		comprovante2.setTipo(TIPO_COMPROVANTE_ESCOLARIDADE);
		comprovanteAtividade = new Comprovante();
		comprovanteAtividade.setTipo(TIPO_COMPROVANTE_PAGAMENTO);
		usuario = Contexto.getUsuario();
		pedidoControle = new PedidoControle();
		usuarioControle = new UsuarioControle();
		comprovanteControle = new ComprovanteControle(usuarioControle);
		buscaParticipante();
		pedidoInscricao();
//		pedidoCursos();
	}
	
	public void buscaParticipante(){
		usuarioControle.limpaCacheBanco();
		ParticipanteControle participanteControle= new ParticipanteControle();
		participante = participanteControle.buscaParticipanteUsuarioId(usuario.getId());
		
		//participante = usuarioControle.findById(usuario.getId()).getParticipante();
		if(participante!=null){
			IndicadorControle indicadorControle = new IndicadorControle();
			Indicador indicador = indicadorControle.buscaIndicadorParticipante(participante.getId());
			participante.setIndicador(indicador);
		
			ComprovanteControle comprovanteControle = new ComprovanteControle(usuarioControle);
			participante.setComprovantes(comprovanteControle.listaComprovantesParticipante(participante.getId()));
			if(participante.getComprovantes()!=null && participante.getComprovantes().size()>0){
				//comprovante = participante.getComprovantes().get(0);
				configuraComprovantes(participante.getComprovantes());
			}
		}
	}
	
	private void configuraComprovantes(List<Comprovante>comprovantes){
		if(comprovantes==null)
			return;
		for (Comprovante comprovante: comprovantes) {
			if(comprovante.getTipo().trim().equals(TIPO_COMPROVANTE_PAGAMENTO)){
				this.comprovante = comprovante;
				if(comprovante!=null)
					visualizarComprovante=true;
			}
			if(comprovante.getTipo().trim().equals(TIPO_COMPROVANTE_ESCOLARIDADE)){
				this.comprovante2 = comprovante;
				if(comprovante2!=null)
					visualizarComprovEsco=true;
			}
			if(comprovante.getTipo().trim().equals(TIPO_COMPROVANTE_ATIVIDADE)){
				this.comprovanteAtividade = comprovante;
				if(comprovanteAtividade!=null)
					visualizarComprovAtividade=true;
			}
		}
	}
	
	public List<InscricaoInfo> pedidoInscricao(){
		buscaParticipante(); 
		pedidosInscricao = new ArrayList<InscricaoInfo>();
		if(participante==null || participante.getPedidos() ==null)
			return pedidosInscricao;
		
		  for(Pedido pedido:participante.getPedidos()){
			  for(ItemCompra itemCompra: pedido.getItemsCompra()){
				  if(itemCompra.getProduto().getTipo().trim().equals(ProdutoControle.PRODUTO_TIPO_INSCRICAO)){
					  Produto produto = itemCompra.getProduto();
					  Indicador indicador = participante.getIndicador();
					  Date dataPedido = pedido.getDataPedido();
					  pedidoInscricao = new InscricaoInfo(produto, indicador, dataPedido);
					  pedidosInscricao.add(pedidoInscricao);
				  }
			  }
		  }
		return pedidosInscricao;
	}
	
	
	private void atualizaInformacoesComprovante(Comprovante comprovante, FileUploadEvent event){
		setNomeArquivoSelecionado(event.getFile().getFileName());
		System.out.println("\n\n #### Tipo de conteudo:"+event.getFile().getContentType()+"#### \n\n");
		comprovante.setNomeArquivo(event.getFile().getFileName());
		comprovante.setComprovante(event.getFile().getContents());
		comprovante.setCaminhoArquivo(getNomeArquivoSelecionado());
		comprovante.setTamanhoArquivo(event.getFile().getSize());
		comprovante.setExtencao(this.pegaExtencao(getNomeArquivoSelecionado()));
		comprovante.setTipoArquivo(event.getFile().getContentType());
		comprovante.setParticipante(participante);
		System.out.println("\n\n\ntamanho arquivo: "+comprovante.getTamanhoArquivo());
	}
	private String pegaExtencao(String nomeArquivo){
		return nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length())  ;
	}
	

	public void salvarComprovante(){
		System.out.println("Chamou função para salvar comprovante");
		if(comprovante!=null){
			comprovante.setTipo(TIPO_COMPROVANTE_PAGAMENTO);
			comprovanteControle.save(comprovante);
			visualizarComprovante=true;
			this.nomeArquivoSelecionado = comprovante.getNomeArquivo();
			MensagemJSF.exibeMensagemInfo("Arquivo enviado com sucesso!", "Enviado com sucesso!");
			enviaEmailEnvioComprovanteTesoureiro();
		}
		
	}
	
	public void salvarComprovanteAtividade(){
		if(comprovanteAtividade!=null){
			comprovanteAtividade.setTipo(TIPO_COMPROVANTE_ATIVIDADE);
			comprovanteControle.save(comprovanteAtividade);
			visualizarComprovAtividade = true;
			MensagemJSF.exibeMensagemInfo("Arquivo enviado com sucesso!", "Comprovante escolaridade enviado com sucesso!");
			enviaEmailEnvioComprovanteTesoureiro();
		}
	}
	
	public void salvarComprovanteEsco(){
		if(comprovante2!=null){
			comprovanteAtividade.setTipo(TIPO_COMPROVANTE_ESCOLARIDADE);
			comprovanteControle.save(comprovante2);
			visualizarComprovEsco = true;
			MensagemJSF.exibeMensagemInfo("Arquivo enviado com sucesso!", "Comprovante de minicurso enviado com sucesso!");
			enviaEmailEnvioComprovanteTesoureiro();
		}
	}
	
	private void enviaEmailEnvioComprovanteTesoureiro(){
		
		List<Usuario> usuarios = papelControle.buscaUsuarios("tesoureiro");
		if(usuarios.size()>0){
			for(Usuario usuario: usuarios){
				enviaEmailComprovante(usuario.getEmail(), usuario.getNome(), this.usuario.getNome());
			}
		}
	}
	private void enviaEmailComprovante(String emaildestinatario, String nomeDestinatario, String nomeParticipante){
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(emaildestinatario.trim(), nomeDestinatario);
		
		StringBuilder texto = new StringBuilder();
		texto.append("\n \t Olá, houve mudança referente ao pagamento de participantes do VII CBC, por favor verifique.")
		.append("\n \t Nome do usuário: ")
		.append(nomeParticipante);
		
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "Comprovante enviado VII CBC");
		return ;
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		//comprovante = new Comprovante();
		atualizaInformacoesComprovante(comprovante, event);
		salvarComprovante();
	}
	
	public void manipuladorUploadComprovante(FileUploadEvent event) {
		//comprovante2 = new Comprovante();
		atualizaInformacoesComprovante(comprovante2, event);
		salvarComprovanteEsco();
	}
	
	public void manipuladorUploadComprovanteAtividade(FileUploadEvent event) {
		//comprovante2 = new Comprovante();
		atualizaInformacoesComprovante(comprovanteAtividade, event);
		salvarComprovanteAtividade();
	}

	
	public String removerComprovante(){
		comprovanteControle.delete(comprovante);
		comprovante = new Comprovante();
		visualizarComprovante=false;
		return "";
	}
	
	public String removerComprovanteEsco(){
		comprovanteControle.delete(comprovante2);
		comprovante2 = new Comprovante();
		visualizarComprovEsco=false;
		return "";
	}
	public String removerComprovanteAtividade(){
		comprovanteControle.delete(comprovanteAtividade);
		comprovanteAtividade = new Comprovante();
		visualizarComprovAtividade=false;
		return "";
	}
	
	public String efetuaPagamentoPayPal(){		
		String UrlPagueSeguro = pagueSeguro.efetuaPagamento(participante, participante.getPedidos());
		System.out.println("############### Redirecionando para: "+UrlPagueSeguro);
		NavegacaoJSF.redirecionarPorExternalContext(UrlPagueSeguro);
		return "";
	}
	
	public List<Pedido> pedidoMiniCurso(){
		IAssociadoControle associadoControle = new AssociadoControle();
		Associado a= associadoControle.verificaExiste(participante.getNumeroSocio(), participante.getCpf());
		if((a!=null && a.isPagamentoEmDia()) || a==null)
			associadoOkOuNaoSocio=true;
		else
			associadoOkOuNaoSocio=false;
		
		
		pedidosCurso = new ArrayList<Pedido>();
		buscaParticipante();
		if(participante==null || participante.getPedidos() ==null)
			return pedidosCurso;
		
		participante.setPedidos(pedidoControle.buscaPedidosParticipanteId(participante.getId()));
		
	
		  for(Pedido pedido:participante.getPedidos()){
			  for(ItemCompra itemCompra: pedido.getItemsCompra()){
				  if(itemCompra.getProduto().getTipo().trim().equals(ProdutoControle.PRODUTO_TIPO_MINI_CURSO)){
					  valorAtividade = itemCompra.getProduto().getPreco(); 
					  pedidosCurso.add(pedido);
					  exibirMiniCurso = true;
					  pagamentoMiniCursoQuitado = pedido.getBoleto().isQuitado();
					  break;
				  }
			  }
		  }
			  
		return pedidosCurso;
	}
	
	public String getTitulo(){
		//pedidoMiniCurso();
		return "";
	}
	
	public List<Pedido> getPedidosCurso() {
		pedidoMiniCurso();
		return pedidosCurso;
	}

	public void setPedidosCurso(List<Pedido> pedidosCurso) {
		this.pedidosCurso = pedidosCurso;
	}

/*	public List<Pedido> pedidoCursos(){
		pedidosCurso =  pedidoControle.buscaCursoParticipanteId(usuario.getParticipante().getId());
		return pedidosCurso;
	}*/

	public List<ItemCompra> getItensInscricao() {
		return itensInscricao;
	}

	public List<InscricaoInfo> getPedidosInscricao() {
		pedidoInscricao();
		return pedidosInscricao;
	}

	public void setPedidosInscricao(List<InscricaoInfo> pedidosInscricao) {
		this.pedidosInscricao = pedidosInscricao;
	}

	public void setItensInscricao(List<ItemCompra> itensInscricao) {
		this.itensInscricao = itensInscricao;
	}

	public List<ItemCompra> getItensMiniCurso() {		
		return itensMiniCurso;
	}

	public void setItensMiniCurso(List<ItemCompra> itensMiniCurso) {
		this.itensMiniCurso = itensMiniCurso;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public boolean isExibirMiniCurso() {
		return exibirMiniCurso;
	}

	public void setExibirMiniCurso(boolean exibirMiniCurso) {
		this.exibirMiniCurso = exibirMiniCurso;
	}

	public boolean isPagamentoMiniCursoQuitado() {
		return pagamentoMiniCursoQuitado;
	}

	public void setPagamentoMiniCursoQuitado(boolean pagamentoMiniCursoQuitado) {
		this.pagamentoMiniCursoQuitado = pagamentoMiniCursoQuitado;
	}

	public String getNomeArquivoSelecionado() {
		return nomeArquivoSelecionado;
	}

	public void setNomeArquivoSelecionado(String nomeArquivoSelecionado) {
		this.nomeArquivoSelecionado = nomeArquivoSelecionado;
	}

	public boolean isVisualizarComprovante() {
		if(comprovante!=null && comprovante.getCaminhoArquivo() !=null){
			visualizarComprovante = true;
		}else{
			if(visualizarComprovante)
				MensagemJSF.exibeMensagemAlerta("Erro ao enviar o arquivo", "Erro ao enviar o arquivo");
			visualizarComprovante = false;
		}
		
		return visualizarComprovante;
	}

	public void setVisualizarComprovante(boolean visualizarComprovante) {
		this.visualizarComprovante = visualizarComprovante;
	}
	
	public boolean isVisualizarComprovEsco() {
		return visualizarComprovEsco;
	}

	public void setVisualizarComprovEsco(boolean visualizarComprovEsco) {
		this.visualizarComprovEsco = visualizarComprovEsco;
	}

	public Comprovante getComprovante() {
		return comprovante;
	}

	public void setComprovante(Comprovante comprovante) {
		this.comprovante = comprovante;
		System.out.println(comprovante.getCaminhoArquivo());
	}

	public Comprovante getComprovante2() {
		return comprovante2;
	}

	public void setComprovante2(Comprovante comprovante2) {
		this.comprovante2 = comprovante2;
	}


	public float getValorAtividade() {
		return valorAtividade;
	}

	public void setValorAtividade(float valorAtividade) {
		this.valorAtividade = valorAtividade;
	}
	

	public Comprovante getComprovanteAtividade() {
		return comprovanteAtividade;
	}

	public void setComprovanteAtividade(Comprovante comprovanteAtividade) {
		this.comprovanteAtividade = comprovanteAtividade;
	}
	
	public boolean isVisualizarComprovAtividade() {
		return visualizarComprovAtividade;
	}

	public void setVisualizarComprovAtividade(boolean visualizarComprovAtividade) {
		this.visualizarComprovAtividade = visualizarComprovAtividade;
	}

	public StreamedContent getArquivoComprovante() {
		return streamedComprovante(comprovante);
	}

	public void setArquivoComprovante(StreamedContent arquivoComprovante) {
		this.arquivoComprovante = arquivoComprovante;
	}
	
	public StreamedContent getArquivoComprovanteEscolaridade() {
		return streamedComprovante(comprovante2);
	}
	
	public void setArquivoComprovanteEscolaridade(
			StreamedContent arquivoComprovanteEscolaridade) {
		this.arquivoComprovanteEscolaridade = arquivoComprovanteEscolaridade;
	}

	public StreamedContent getArquivoComprovanteAtividade() {
		return streamedComprovante(comprovanteAtividade);
	}

	public void setArquivoComprovanteAtividade(
			StreamedContent arquivoComprovanteAtividade) {
		this.arquivoComprovanteAtividade = arquivoComprovanteAtividade;
	}


	private StreamedContent streamedComprovante(Comprovante comprovante){
		StreamedContent arquivoComprovante = null;
		InputStream arquivoInput;
		if(comprovante!=null && comprovante.getComprovante()!=null){
			arquivoInput = new ByteArrayInputStream(comprovante.getComprovante());
			arquivoComprovante = new DefaultStreamedContent(arquivoInput, comprovante.getTipoArquivo(), 
					("comprovante_"+(comprovante.getTipo()
							.endsWith(PagamentoBeans.TIPO_COMPROVANTE_ESCOLARIDADE)?"escolaridade":"pagamento"))
					+comprovante.getExtencao());
			visualizarComprovante = true;
			// System.out.println("\n\nnome do arquivo de c"+comprovante.getTamanhoArquivo());
		}else{
			arquivoInput = null;
		}
		return arquivoComprovante;
	}

	public InscricaoInfo getPedidoInscricao() {
		return pedidoInscricao;
	}

	public void setPedidoInscricao(InscricaoInfo pedidoInscricao) {
		this.pedidoInscricao = pedidoInscricao;
	}

	public boolean isAssociadoOkOuNaoSocio() {
		return associadoOkOuNaoSocio;
	}

	public void setAssociadoOkOuNaoSocio(boolean associadoOkOuNaoSocio) {
		this.associadoOkOuNaoSocio = associadoOkOuNaoSocio;
	}
	
}

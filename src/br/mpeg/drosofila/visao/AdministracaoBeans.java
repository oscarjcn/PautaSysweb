package br.mpeg.drosofila.visao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.mpeg.drosofila.controle.ComprovanteControle;
import br.mpeg.drosofila.controle.PapelControle;
import br.mpeg.drosofila.controle.ParticipanteControle;
import br.mpeg.drosofila.controle.PedidoControle;
import br.mpeg.drosofila.controle.ProdutoControle;
import br.mpeg.drosofila.controle.ResumoControle;
import br.mpeg.drosofila.controle.StatusResumoControle;
import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.controle.api.IResumoControle;
import br.mpeg.drosofila.controle.api.IStatusResumoControle;
import br.mpeg.drosofila.controle.api.IUsuarioControle;
import br.mpeg.drosofila.modelo.Comprovante;
import br.mpeg.drosofila.modelo.Endereco;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.modelo.Papel;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.modelo.StatusResumo;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.persistencia.StatusResumoDao;
import br.mpeg.drosofila.persistencia.api.IStatusResumoDao;
import br.mpeg.drosofila.teste.Moeda;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.mail.EnvioEmail;
import br.mpeg.drosofila.util.visao.ComponentesJSF;
import br.mpeg.drosofila.util.visao.ControleAcesso;
import br.mpeg.drosofila.util.visao.MensagemJSF;
import br.mpeg.drosofila.visao.beans.auxiliares.AtividadeInfo;
import br.mpeg.drosofila.visao.beans.auxiliares.ProdutoInfo;
import br.mpeg.drosofila.visao.beans.auxiliares.ResumoInfo;
import br.mpeg.drosofila.visao.validadores.ComentarioResumoValidator;

@ManagedBean
@SessionScoped
public class AdministracaoBeans implements Serializable{
	
	private List<Participante> participantes;
	private List<ResumoInfo> resumos;
	private List<ProdutoInfo> miniCurso;
	private List<ProdutoInfo> passagens;
	private boolean podeEditar;
	private SelectItem[] opcoesInscricaoPaga;
	private SelectItem[] opcoesComprovanteEscolaridade;
	private SelectItem[] opcoesComprovantePagamento;
	private SelectItem[] opcoesComprovanteAtividade;
	private SelectItem[] filtroIndicacaoOral;
	private Endereco endereco;
	private boolean administrador;
	private boolean coordenador;
	private boolean revisor;
	private ResumoInfo[] resumosSelecionados;
	private List<Usuario> revisores;
	private Usuario revisorSelecionado;
	private Resumo resumoParaRevisao;
	private String conteudoResumo;
	private ResumoInfo resumoPRevisao;
	private UIComponent painel;
	private List<AtividadeInfo> atividades;
	private Resumo resumoParaDownload;
	private StreamedContent streamedArquivoResumo;
	
	// Para exibir detalhes
	private Participante participante;

	private ProdutoControle produtoControle = new ProdutoControle();
	private ParticipanteControle participanteControle = new ParticipanteControle();
	private IUsuarioControle usuarioControle = new UsuarioControle();
	private IResumoControle resumoControle = new ResumoControle();
	//private IStatusResumoDao statusResumoDao = new StatusResumoDao();
	private IStatusResumoControle statusResumoControle = new StatusResumoControle();
	private Usuario usuario;
	private String statusResumoSelecionado;
	private Map <String, String> statusResumo;

	
	
	public AdministracaoBeans() {
		opcoesInscricaoPaga = createFilterOptionsInscricoes();
		filtroIndicacaoOral = createFilterOptionsIndicadoOral();
		usuario = Contexto.getUsuario();
		statusResumo = new HashMap<String, String>();
		statusResumo.put(StatusResumoDao.STATUS_RESUMO_APROVADO, "Aprovado");
		statusResumo.put(StatusResumoDao.STATUS_RESUMO_REPROVADO, "Reprovado");
		statusResumo.put("Corre√ß√µes necess√°rias", "Revisado");
		createFilterOptionsComprovantes();
	}
	
	 private SelectItem[] createFilterOptionsInscricoes()  {
	        SelectItem[] options = new SelectItem[3];  
	        options[0] = new SelectItem("", "Select");
	        options[1] = new SelectItem("FALSE", "Pendente");
	        options[2] = new SelectItem("TRUE", "Pago");
	  
	        return options;  
	 }
	 
	 private SelectItem[] createFilterOptionsIndicadoOral()  {
	        SelectItem[] options = new SelectItem[3];  
	        options[0] = new SelectItem("", "Select");
	        options[1] = new SelectItem("FALSE", "Sim");
	        options[2] = new SelectItem("TRUE", "N√£o");
	  
	        return options;  
	 }
	 
	 private void createFilterOptionsComprovantes()  {
	        this.opcoesComprovanteEscolaridade = new SelectItem[2];  
	        opcoesComprovanteEscolaridade[0] = new SelectItem("", "Select");
	        opcoesComprovanteEscolaridade[1] = new SelectItem("ESCO", "Listar comprovantes de escolariade");
	        opcoesComprovantePagamento= new SelectItem[2];  
	        opcoesComprovantePagamento[0] = new SelectItem("", "Select");
	        opcoesComprovantePagamento[1] = new SelectItem("PGTO", "Listar comprovantes de pagamento");
	        opcoesComprovanteAtividade = new SelectItem[2];
	        opcoesComprovanteAtividade[0] = new SelectItem("", "Select");
	        opcoesComprovanteAtividade[1] = new SelectItem("ATIV", "Listar comprovantes de atividade");
	  
	 }
	
	 public void atualizaConteudoResumo(){
		 resumoParaRevisao.setStatusResumo(statusResumoControle.buscaStatusResumo(statusResumoSelecionado));
		
		 if(resumoParaRevisao.getComentarioResumo() != null
				 && !resumoParaRevisao.getComentarioResumo().trim().equals("") 
				 && resumoParaRevisao.getComentarioResumo().trim().length()< ComentarioResumoValidator.TAMANHO_MAXIMO_CONTEUDO_COMENTARIO_RESUMO 
				 && (statusResumoSelecionado.trim().equals("Reprovado") || statusResumoSelecionado.trim().equals("Revisado"))){
			 
			 MensagemJSF.exibeMensagemErro(
					 "Em caso de resumo 'reprovado' ou 'Para correÁo', uma justificativa deve ser inserida, com mais de 10 caracteres."
					 , "Deve-se justificar os resumos reprovados");
			 return ;
		 }
		 if((getStatusResumoSelecionado().trim().equals("Revisado") || getStatusResumoSelecionado().trim().equals("Reprovado"))
				 && (resumoParaRevisao.getComentarioResumo()==null || resumoParaRevisao.getComentarioResumo().trim().equals(""))){
			 MensagemJSF.exibeMensagemErro("Deve ser inserido algum coment√°rio para este resumo"
					 , "Deve ser inserido algum coment√°rio para este resumo");
		 }
		 
		 
		 //statusResumoSelecionado
		 
		 StringBuilder comentarioResumo = new StringBuilder();
		 comentarioResumo.append("\nSeu resumo foi avaliado, caso necessite ser corrigido o mesmo dever ser resubmetido para an√°lise. Acesse sua ¡rea de resumos e clique em 'Editar Resumo' para efetuar as alteraÁıes.");
		 comentarioResumo.append("\nStatus do resumo: ");
		 comentarioResumo.append(resumoParaRevisao.getStatusResumo().getNome());
		 comentarioResumo.append("\n\nTÌtulo do resumo: \n");
		 comentarioResumo.append(resumoParaRevisao.getTitulo());		 
		 comentarioResumo.append("\n\nComentario do resumo: \n");
		 comentarioResumo.append((resumoParaRevisao.getComentarioResumo()==null)? "":resumoParaRevisao.getComentarioResumo());
		 
		 enviarEmailComentarioRevisao(resumoParaRevisao.getParticipante().getNome(), resumoParaRevisao.getParticipante().getEmail(), comentarioResumo);
		 resumoControle.update(resumoParaRevisao);
		 listaInformacoesResumos();
	 }
	 
	public void atualizaResumo(ValueChangeEvent e){
		this.conteudoResumo = (String) e.getNewValue();
	}
	 
	public String atualizarListaResumoRevisor(){
		 StringBuilder mensagemrevisor =  new StringBuilder();
		 /*mensagemrevisor.append("\nCaro: ");
			mensagemrevisor.append(usuario.getNome());*/
			if (resumosSelecionados.length>1){
				mensagemrevisor.append(", vocÍ™ tem "+resumosSelecionados.length+" submetidos ao VII CBC para sua avalia√ß√£o")
						.append(", por favor, verifique na ·rea administrativa de seu cadastro o trabalho a ser analisado. \n");
			}else{
				mensagemrevisor.append(", vocÍ tem um novo resumo submetido ao VII CBC para sua avaliaÁ„o")
				.append(", por favor, verifique na ·rea administrativa de seu cadastro o trabalho a ser analisado. \n");
			}
		 
		 //revisorSelecionado.setResumosDelegados(resumosSelecionados);
		 for(ResumoInfo resumo:resumosSelecionados){
			 resumo.getResumo().setRevisor(revisorSelecionado);
			 if(!resumo.getResumo().getStatusResumo().getNome().equals("Pendente"))
				 resumo.getResumo().setStatusResumo(statusResumoControle.buscaStatusResumo(StatusResumoDao.STATUS_RESUMO_ENVIADO));
			 
			 mensagemrevisor = corpoEmailRevisor(revisorSelecionado, mensagemrevisor, resumo.getResumo());
			 
			 resumoControle.update(resumo.getResumo());
		 }
		 enviarEmailRevisor(revisorSelecionado.getNome(), revisorSelecionado.getEmail(), mensagemrevisor);
		 //usuarioControle.update(revisorSelecionado);
		 listaInformacoesResumos();
		 return "lista";
	 }
	
	public String salvarParticipantes(){
		try{
			for(Participante participante: participantes){
				participanteControle.update(participante);
			}
		}catch(Exception ex){
			MensagemJSF.exibeMensagemInfo("Erro ao tentar alterar", "Erro ao tentar alterar");
			return "erro";
		}
		MensagemJSF.exibeMensagemInfo("Altera√ß√£o salva com sucesso", "Altera√ß√£o salva com sucesso");
		return "sucesso";
	}
	
	public void cleanSubmittedValues() {  
		ComponentesJSF.cleanSubmittedValues(painel);
		painel.getChildren().clear();          
    }  
	
	
	public String salvarPedido(){
		System.out.println("\n\n\nChamou salvar pedido");
		
		PedidoControle pedidoControle = new PedidoControle();
		List<Pedido> pedidos = participante.getPedidos();
		StringBuilder pedidosPagos = new StringBuilder();
		double totalPago=0;
		try{
			for (Pedido pedido : pedidos) {
				pedido.setParticipante(participante);
				System.out.println("boleto "+pedido.getBoleto().isQuitado());
				pedidoControle.update(pedido);
				totalPago+=verificaPedidosPagos(pedido, pedidosPagos);
				
			}
			pedidosPagos.append("\n At√© o momento foi confirmado um pagamento no total de: ").append(Moeda.mascaraDinheiro(totalPago, Moeda.DINHEIRO_REAL));
			
			if(totalPago>0)
				enviarEmail(pedidosPagos);
			//participanteControle.update(participante);
		}catch(Exception ex){
			MensagemJSF.exibeMensagemInfo("Erro ao tentar alterar", "Erro ao tentar alterar");
			return "erro";
		}
		MensagemJSF.exibeMensagemInfo("Altera√ß√£o efetuada com sucesso", "Altera√ß√£o efetuada com sucesso");
		participantes = listaParticipantes();
		atividades = listaAtividades();
		return "sucesso";
		
	}
	
	private double verificaPedidosPagos(Pedido pedido, StringBuilder pedidosPagos){
		if(!pedido.getBoleto().isQuitado())
			return 0;
		
		List<ItemCompra> itensCompra = pedido.getItemsCompra();
		double valorPedido = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'as' HH:mm");
		
		pedidosPagos.append("\n\n ConfirmaÁ„o de pagamento de inscriÁ„o no X Congresso de Gerencia de Pessoas \n Data de pedido da inscri√ß√£o: ")
		.append(dateFormat.format(pedido.getDataPedido())).append("\n Itens:\n");
		for (ItemCompra itemCompra : itensCompra) {
			if(itemCompra.getProduto().getTipo().trim().toUpperCase().equals("I")){
				valorPedido = participante.getIndicador().getValor();
				pedidosPagos.append("\t").append(itemCompra.getProduto().getNome()).append(" - ").append(
						Moeda.mascaraDinheiro(valorPedido, Moeda.DINHEIRO_REAL)).append("\n\t")
				.append(participante.getIndicador().getNome()).append("\n");
			}else{
				pedidosPagos.append("\t")
				//.append((itemCompra.getNivelPrioridade()==1)?"Primeira opÁ„o: ":"Segunda opÁ„o: ")
				.append("Mini-curso prÈ congresso: ").append(itemCompra.getProduto().getNome())
				.append("\n");/*.append(" - ")
				.append(Moeda.mascaraDinheiro(itemCompra.getProduto().getPreco(), Moeda.DINHEIRO_REAL)).append(" quantidade: ")
				.append(itemCompra.getQuantidade()).append(" = ").append((itemCompra.getQuantidade()*itemCompra.getProduto().getPreco())).append("\n\t")
				.append("Descri√ß√£o: ").append(itemCompra.getProduto().getDescricao()).append("\n\n");*/
			}
		}
		/*pedidosPagos.append("\n Total confirmado deste pedido: ")
		.append((valorPedido==0)?pedido.getValorPedido(): valorPedido);*/
		return (valorPedido==0)?pedido.getValorPedido(): valorPedido;
	}
	
	private StringBuilder corpoEmailRevisor(Usuario usuario, StringBuilder mensagemrevisor, Resumo resumo){
	//	mensagemrevisor.append("\n\n Informa√ß√µes do resmo: \n");
		mensagemrevisor.append("\t TÌtulo do resumo: ");
		mensagemrevisor.append(resumo.getTitulo());
		
		/*if(resumo.getStatusResumo().getNome().toLowerCase().trim().equals("pendente")){
			mensagemrevisor.append("\n OBS: O status do resumo esta como Pendente ele n√£o aparecera ate que o usuario envie definitivamente, marcando o resumo como 'Enviado'.");
			mensagemrevisor.append(resumo.getTitulo());
		}*/
		
		return mensagemrevisor;
	}
	
	public void enviarEmail(StringBuilder mensagem){
		String nome = participante.getNome();
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(participante.getEmail(), nome);
		StringBuilder texto = new StringBuilder();
		texto.append("Prezado(a) ").append(nome).append(mensagem);
		
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "Congresso de Gest„o de Pessoas");
	}
	
	public void enviarEmailRevisor(String nomeDestinatario, String emailDestinatario, StringBuilder mensagem){
		String nome = nomeDestinatario;
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(emailDestinatario, nome);
		StringBuilder texto = new StringBuilder();
		texto.append("Prezado(a) ").append(nome).append(mensagem);
		
		//CONTA: 25427-4 AG:0253-4
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "Congresso de Gest„o de Pessoas");
	}
	
	public void enviarEmailComentarioRevisao(String nomeDestinatario, String emailDestinatario, StringBuilder mensagem){
		String nome = nomeDestinatario;
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(emailDestinatario, nome);
		StringBuilder texto = new StringBuilder();
		texto.append("Prezado(a) ").append(nome).append(mensagem);
		
		//CONTA: 25427-4 AG:0253-4
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "Congresso de Gest„o de Pessoas");
	}
	
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
	
	/*
	public String salvarPedidoOld(){
		
		try{
			participanteControle.update(participante);
		}catch(Exception ex){
			MensagemJSF.exibeMensagemInfo("Erro ao tentar alterar", "Erro ao tentar alterar");
			return "erro";
		}
		MensagemJSF.exibeMensagemInfo("Altera√ß√£o salva com sucesso", "Altera√ß√£o salva com sucesso");
		return "sucesso";
		
	}*/
	
	public List<Participante> listaParticipantes(){
		return participanteControle.findAll();
	}
	
	public String navegacaoParticipantes(){
		participantes = listaParticipantes();
		return "listaInscritos";
	}
	
	public String navegacaoAtividades(){
		atividades = listaAtividades();
		return "listaAtividades";
	}
	
	public List<AtividadeInfo> listaAtividades(){
		return listaParticipantesParaAtividades(participanteControle.findAll());
	}
	
	public List<AtividadeInfo> listaParticipantesParaAtividades(List<Participante> participantes){
		List<AtividadeInfo> atividades = new ArrayList<AtividadeInfo>();
		
		PedidoControle pedidoControle =  new PedidoControle();
		
		for(Participante participante: participantes){
			AtividadeInfo atividadeInfo = new AtividadeInfo();
			
			atividadeInfo.setParticipante(participante);
			atividadeInfo.setComprovantes(participante.getComprovantes());
			List<Pedido> pedidos = pedidoControle.buscaPedidosParticipanteId(participante.getId());
			imprimePedidos(pedidos);
			atividadeInfo.setAtividades(pedidos);
			atividadeInfo.setIndicador(participante.getIndicador());
			atividades.add(atividadeInfo);
			
			System.out.println("InformaÁıes de informaÁıes do pedido: "+atividadeInfo.toString());
		}
		
		return atividades;
	}
	
	public void imprimePedidos(List<Pedido> pedidos){
		for(Pedido pedido: pedidos){
			
			System.out.println("################### primeiro produto dos pedidos "+pedido.getId()+": "+pedido.getItemsCompra().get(0).getProduto());
		}
	}
	
	
	public List<ResumoInfo> listaInformacoesResumos(){
		ResumoControle resumoControle = new ResumoControle();
		
		if(usuarioEhAdministrador() || usuarioEhCoordenador())
			return listaResumos(resumoControle.listaEnviados());
		else if(usuarioEhRevisor()){
			return listaResumos(resumoControle.listaResumoRevisorId(usuario.getId()));
		}
		return null;
	}
	
	private List<Papel> papeis = null;
	private Comprovante comprovante;
	public boolean verificaPapeisPertense(Usuario usuario, String tipoPapel){
		if(painel==null){
			PapelControle papelControle = new PapelControle();
			papeis = papelControle.buscaPapeisUsuario(usuario.getId());
		}
		
		for(Papel papel2 : papeis){
			if(papel2.getTipo().trim().equals(tipoPapel.trim()))
				return true;
		}
		return false;
	}
	
	public boolean usuarioEhAdministrador(){
		if(usuario==null)
			usuario = Contexto.getUsuario();
		return verificaPapeisPertense(usuario, "A");
	}
	
	public boolean usuarioEhCoordenador(){
		if(usuario==null)
			usuario = Contexto.getUsuario();
		return verificaPapeisPertense(usuario, "O");
	}
	
	public boolean usuarioEhRevisor(){
		return verificaPapeisPertense(usuario, "R");
	}
	
	private  List<ResumoInfo> listaResumos(List<Resumo> resumos){
		this.resumos = new ArrayList<ResumoInfo>();
		for (Resumo resumo : resumos) {
			ResumoInfo resumoInfo = new ResumoInfo();
			resumoInfo.setResumo(resumo);
			resumoInfo.setParticipante(participanteControle.buscaPorCPF(resumo.getCpfAutor()));
			this.resumos.add(resumoInfo);
		}
		return this.resumos;
	}
	
		
	
	/*public List<ProdutoInfo> listaInformacoesCamisas(){
		this.camisas = configuraListaProdutosInfo(produtoControle.procuraCamisa()); 
		return this.camisas;
	}
	
	public List<ProdutoInfo> listaInformacoesPassagens(){
		this.passagens = configuraListaProdutosInfo(produtoControle.procuraTodasPassagem());
		return this.passagens;
	}*/
	
	public List<ProdutoInfo> listaInformacoesMiniCurso(){
		this.miniCurso = configuraListaProdutosInfo(produtoControle.procuraMiniCurso());
		return this.miniCurso;
	}
	
	private List<ProdutoInfo> configuraListaProdutosInfo(List<Produto> produtos){
		List<ProdutoInfo> produtosInfo = new ArrayList<ProdutoInfo>();
		for (Produto produto : produtos) {
			ProdutoInfo produtoInfo = new ProdutoInfo();
			produtoInfo.setQuantidadePedida(produtoControle.verificaQuantidadeProdutoPedida(produto.getId()));
			produtoInfo.setQuantidadeVendida(produtoControle.verificaQuantidadeProdutoVendida(produto.getId()));
			produtoInfo.setProduto(produto);
			produtosInfo.add(produtoInfo);
		}
		return produtosInfo;
	}
	
	public String buscarComprovante(){
		usuarioControle.limpaCacheBanco();
		participante = participanteControle.findById(participante.getId());
		return "";
	}
	
	public String removerComprovante(){
		ComprovanteControle comprovanteControle = new ComprovanteControle(usuarioControle);
		comprovanteControle.delete(comprovante);
		buscarComprovante();
		return "";
	}
	
	public List<Usuario> listaRevisores(){
		return usuarioControle.listaPorPapelRevisor();
	}

	public void setParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}
	
	public List<Participante> getParticipantes() {
		//participantes = listaParticipantes();
		return participantes;
	}


	public void setResumos(List<ResumoInfo> resumos) {
		this.resumos = resumos;
	}

	public List<ResumoInfo> getResumos() {
	//	listaInformacoesResumos();
		return resumos;
	}
	
	public String navegaListaResumos(){
		listaInformacoesResumos();
		return "resumos";
	}


	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public Participante getParticipante() {
		return participante;
	}
	
	public List<ProdutoInfo> getMiniCurso() {
		listaInformacoesMiniCurso();
		return miniCurso;
	}

	public void setMiniCurso(List<ProdutoInfo> miniCurso) {
		this.miniCurso = miniCurso;
	}

	public SelectItem[] getOpcoesInscricaoPaga() {
		return opcoesInscricaoPaga;
	}

	public void setOpcoesInscricaoPaga(SelectItem[] opcoesInscricaoPaga) {
		this.opcoesInscricaoPaga = opcoesInscricaoPaga;
	}

	public boolean isPodeEditar() {
		Usuario usuario = (Usuario) Contexto.getSessao().getAttribute("Usuario");
		PapelControle papelControle = new PapelControle();
		List<Papel> papeis = papelControle.buscaPapeisUsuario(usuario.getId());
		
		podeEditar = verificaPossuiPapel(papeis, "T");

		return podeEditar;
	}
	
	private boolean verificaPossuiPapel(List<Papel> papeis, String tipoPapel){
		for(Papel papel: papeis){
			if(papel.getTipo().trim().equals(tipoPapel.trim())){
				return true;
			}
		}
		return false;
	}


	public void setPodeEditar(boolean podeEditar) {
		this.podeEditar = podeEditar;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public boolean isAdministrador() {
		administrador = usuarioEhAdministrador();
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public boolean isRevisor() {
		revisor = usuarioEhRevisor();
		return revisor;
	}

	public void setRevisor(boolean revisor) {
		this.revisor = revisor;
	}

	public ResumoInfo[] getResumosSelecionados() {
		return resumosSelecionados;
	}

	public void setResumosSelecionados(ResumoInfo[] resumosSelecionados) {
		this.resumosSelecionados = resumosSelecionados;
	}

	public List<Usuario> getRevisores() {
		revisores = listaRevisores();
		return revisores;
	}

	public void setRevisores(List<Usuario> revisores) {
		this.revisores = revisores;
	}

	public Usuario getRevisorSelecionado() {
		return revisorSelecionado;
	}

	public void setRevisorSelecionado(Usuario revisorSelecionado) {
		this.revisorSelecionado = revisorSelecionado;
	}

	public Resumo getResumoParaRevisao() {
		System.out.println("Get Resumo para revis√£o "+resumoParaRevisao);
		return resumoParaRevisao;
	}

	public void setResumoParaRevisao(Resumo resumoParaRevisao) {
		System.out.println("Set Resumo para revis√£o "+resumoParaRevisao);
		this.resumoParaRevisao = resumoParaRevisao;
	}
	
	public void limpaFormulario(){
		
	}

	public String getConteudoResumo() {
		return conteudoResumo;
	}

	public ResumoInfo getResumoPRevisao() {
		return resumoPRevisao;
	}

	public void setResumoPRevisao(ResumoInfo resumoPRevisao) {
		System.out.println("pegou resumo: ");
		this.resumoPRevisao = resumoPRevisao;
	}

	public void setConteudoResumo(String conteudoResumo) {
		this.conteudoResumo = conteudoResumo;
	}
	
	public void listener(ActionEvent event) {
	    this.conteudoResumo = (String) event.getComponent().getAttributes().get("fileId");
	}

	public UIComponent getPainel() {
		return painel;
	}

	public void setPainel(UIComponent painel) {
		this.painel = painel;
	}

	public String getStatusResumoSelecionado() {
		return statusResumoSelecionado;
	}

	public void setStatusResumoSelecionado(String statusResumoSelecionado) {
		this.statusResumoSelecionado = statusResumoSelecionado;
	}

	public Map<String, String> getStatusResumo() {
		return statusResumo;
	}

	public void setStatusResumo(Map<String, String> statusResumo) {
		this.statusResumo = statusResumo;
	}

	public Comprovante getComprovante() {
		return comprovante;
	}

	public void setComprovante(Comprovante comprovante) {
		this.comprovante = comprovante;
	}

	public void testeListener(ValueChangeEvent event){
		System.out.println(event.getNewValue());
	}

	
	public List<AtividadeInfo> getAtividades() {
		//atividades = listaAtividades();
		return atividades;
	}

	public void setAtividades(List<AtividadeInfo> atividades) {
		this.atividades = atividades;
	}
	
	public SelectItem[] getOpcoesComprovanteEscolaridade() {
		return opcoesComprovanteEscolaridade;
	}

	public void setOpcoesComprovanteEscolaridade(
			SelectItem[] opcoesComprovanteEscolaridade) {
		this.opcoesComprovanteEscolaridade = opcoesComprovanteEscolaridade;
	}

	public SelectItem[] getOpcoesComprovantePagamento() {
		return opcoesComprovantePagamento;
	}

	public void setOpcoesComprovantePagamento(
			SelectItem[] opcoesComprovantePagamento) {
		this.opcoesComprovantePagamento = opcoesComprovantePagamento;
	}

	public SelectItem[] getOpcoesComprovanteAtividade() {
		return opcoesComprovanteAtividade;
	}

	public void setOpcoesComprovanteAtividade(
			SelectItem[] opcoesComprovanteAtividade) {
		this.opcoesComprovanteAtividade = opcoesComprovanteAtividade;
	}

	public Resumo getResumoParaDownload() {
		
		return resumoParaDownload;
	}

	public void setResumoParaDownload(Resumo resumoParaDownload) {
		System.out.println("\n\n titulo do resumo selecionado: "+resumoParaDownload.getTitulo()+"\n\n");
		this.resumoParaDownload = resumoParaDownload;
	}

	public StreamedContent getStreamedArquivoResumo() {
		System.out.println("\n\n titulo do resumo selecionado passado para resumo: "+resumoParaDownload.getTitulo()+"\n\n");
		baixarArquivoResumo(resumoParaDownload);
		return streamedArquivoResumo;
	}

	public void setStreamedArquivoResumo(StreamedContent streamedArquivoResumo) {
		this.streamedArquivoResumo = streamedArquivoResumo;
	}

	public IStatusResumoControle getStatusResumoControle() {
		return statusResumoControle;
	}

	public void setStatusResumoControle(IStatusResumoControle statusResumoControle) {
		this.statusResumoControle = statusResumoControle;
	}

	public boolean isCoordenador() {
		coordenador = usuarioEhCoordenador();
		return coordenador;
	}

	public void setCoordenador(boolean coordenador) {
		this.coordenador = coordenador;
	}

	public SelectItem[] getFiltroIndicacaoOral() {
		return filtroIndicacaoOral;
	}

	public void setFiltroIndicacaoOral(SelectItem[] filtroIndicacaoOral) {
		this.filtroIndicacaoOral = filtroIndicacaoOral;
	}

	public static void main(String[] args) {
		System.out.println("teste");
		System.out.println("\t teste");
		
		/*AdministracaoBeans administracaoBeans = new AdministracaoBeans();
		
		System.out.println(administracaoBeans.listaParticipantes().size());*/
		
		/*List<ResumoInfo> resumos = administracaoBeans.listaInformacoesResumos();
		for (ResumoInfo resumoInfo : resumos) {
			System.out.println(resumoInfo.getParticipante().getNome());
		}*/
		
		
		/*AdministracaoBeans administracaoBeans = new AdministracaoBeans();
		List<ProdutoInfo> passagens = administracaoBeans.listaInformacoesPassagens();
		for (ProdutoInfo produtoInfo : passagens) {
			System.out.println("produto: "+produtoInfo.getProduto().getDescricao()+" " +
					"\n quantidade vendida: "+produtoInfo.getQuantidadeVendida());
		}*/
	}
	
}

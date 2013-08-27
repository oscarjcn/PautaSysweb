package br.mpeg.drosofila.visao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.mpeg.drosofila.controle.ComprovanteControle;
import br.mpeg.drosofila.controle.IndicadorControle;
import br.mpeg.drosofila.controle.ParticipanteControle;
import br.mpeg.drosofila.controle.PedidoControle;
import br.mpeg.drosofila.controle.ProdutoControle;
import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.modelo.Boleto;
import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.persistencia.ItemCompraDao;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.controle.PadraoController;
import br.mpeg.drosofila.util.mail.EnvioEmail;

import br.mpeg.drosofila.util.visao.MensagemJSF;

/**
 * 
 * @author Victor Coutinho
 * 
 */
@ManagedBean
@SessionScoped
public class PedidosBeans extends PadraoController<Usuario> implements
		Serializable {


	private static final long serialVersionUID = 1L;

	private Pedido pedido;
	private Pedido pedidoPardetalheCompra;
	private List<Pedido> pedidos;
	private List<Pedido> pedidosRealizados = new ArrayList<Pedido>();
	//private ItemCompra item;
	private List<ItemCompra> items;
	private List<Produto> cursos;
	//private List<Produto> passagens;
	private Float totalPagar = 0f;
	private Produto cursoPrincipal;
	private Produto cursoSecundario;
	private ProdutoControle produtoControle;
	private boolean selecaoCompraAblitada = true; 
	private boolean pagamentoCursoEfetuado = false;

	public PedidosBeans(){
		/*produtoControle = new ProdutoControle();
		verificPedidosEfetuados(); */
	}
	
	@Override
	public String getTitulo() {
		totalPagar = 0f;
		carregaObjetos();
		return super.getTitulo();
	}

	public void testandoAtualizacao(){
		System.out.println("Chamou testandoAtualizacao");
		/*if(item.getProduto()!=null){
			System.out.println("Chamou a parada"+item.getProduto().getNome());
			System.out.println("Chamou a parada"+item.getQuantidade());
		}
		*/
	}
	
	public String reinit() {
		totalPagar = 0f;
		
		boolean adicionarCurso1=true, adicionarCurso2=true, cursoJaAdicionado1 = false, cursoJaAdicionado2 = false;
		
		for(ItemCompra itemCompra: items){
			if(itemCompra.getNivelPrioridade()==1 ){
				adicionarCurso1=false;
			}
			if(itemCompra.getNivelPrioridade()==2 ){
				adicionarCurso2=false;
			}
			
			if(cursoPrincipal!=null && itemCompra.getProduto().getNome().trim().equals(cursoPrincipal.getNome().trim())){
				cursoJaAdicionado1 = true;
			}
			if(cursoSecundario!=null && itemCompra.getProduto().getNome().trim().equals(cursoSecundario.getNome().trim())){
				cursoJaAdicionado2 = true;
			}
		}
		
		if(adicionarCurso1 && !cursoJaAdicionado1)
			items.add(new ItemCompra(cursoPrincipal, 1));
		if(cursoSecundario!=null && adicionarCurso2 && !cursoJaAdicionado2)
			items.add(new ItemCompra(cursoSecundario, 2));
		if(cursoPrincipal!=null)
			totalPagar = cursoPrincipal.getPreco();
		return null;
	}

	
	private void carregaUsuario(){
		Usuario sessao = (Usuario) Contexto.getSessao().getAttribute("Usuario");
		objeto = new UsuarioControle().findById(sessao.getId());
	}
	
	void carregaObjetos() {
		pedido = new Pedido();
		/*camisas = new ProdutoControle().procuraCamisa();
		System.out.println("numero camisas " + camisas.size());
		passagens = new ProdutoControle().procuraPassagem();
		System.out.println("numero passagens " + passagens.size());*/
		cursos = produtoControle.procuraCurso();
		items = new ArrayList<ItemCompra>();
		carregaUsuario();
		pedidos = new ArrayList<Pedido>();
		facade = new UsuarioControle();
		try {
			if (objeto.getParticipante().getPedidos() != null)
				pedidos = objeto.getParticipante().getPedidos();
		} catch (NullPointerException e) {
			pedidos = new ArrayList<Pedido>();
		}
	}
	
	
	private boolean quantidadeProdutoDispinivel(Produto produto, Integer quantidade){
		//Long soma = 0l;
		Long quantidadeVendida = produtoControle.verificaQuantidadeProdutoPedida(produto.getId());
		/*if(quantidadeProduto.containsKey((produto.getNome()+produto.getDescricao())))
			soma = quantidadeProduto.get(produto.getNome()+produto.getDescricao()) ;
		
		quantidadeProduto.put(produto.getNome()+produto.getDescricao(), soma+quantidade);
		*/	
		
		if(!produto.getTipo().trim().toUpperCase().endsWith(ProdutoControle.PRODUTO_TIPO_MINI_CURSO) 
				|| quantidade+quantidadeVendida<= produto.getQuantidadeDisponiveis()){
			return true;
		}
		

		MensagemJSF.exibeMensagemAlerta("Minicurso não possui mais vagas disponíveis"
				, "Minicurso "+produto.getNome()+" - "+produto.getDescricao()
				+", não possui mais vagas.");
			return false;
	}
	
//	private Map<String, Long> quantidadeProduto = new HashMap<String, Long>();

	private boolean verificaSePedidoPossuiInscricaoPrincipal(){
		for(ItemCompra itemCompra : items){
			if(itemCompra.getNivelPrioridade()==1)
				return true;
		}
		return false;
	}
	
	
	public void criaPedido(){
		if(pedido == null)
			pedido = new Pedido();
	}
	
	public String configuraPedido() {
		List<ItemCompra> itensCompra = new ArrayList<ItemCompra>();
		itensCompra = getItems();
		if(itensCompra!=null && itensCompra.size()>0){
			itensCompra.get(0).setProduto(cursoPrincipal);
			//System.err.println("############produto do  item compra j� cadastrado: "+pedido.getItemsCompra().get(0).getProduto());
		}else{
			itensCompra = new ArrayList<ItemCompra>();
			itensCompra.add(new ItemCompra(cursoPrincipal, 0));
		}
		//System.err.println("############produto do pedido item compra: "+pedido.getItemsCompra().get(0).getProduto());
		
		criaPedido();
		pedido.setItemsCompra(itensCompra);
		System.err.println("############produto do item compra novo: "+pedido.getItemsCompra().get(0).getProduto());
		
		boolean erroQuantidade=false;
		for (ItemCompra ic : pedido.getItemsCompra()) {
			if(!quantidadeProdutoDispinivel(ic.getProduto(), ic.getQuantidade()))
				erroQuantidade=true;
			ic.setPedido(pedido);
		}
		
		if(erroQuantidade){
			MensagemJSF.exibeMensagemAlerta("Curso escolhido n�o possui mais vagas disponiveis", "Curso escolhido n�o possui mais vagas disponiveis");
			return null;
		}
		
		Calendar dataValidade = Calendar.getInstance();
		pedido.setDataPedido(dataValidade.getTime());
		dataValidade.add(Calendar.DAY_OF_MONTH, 10);
		pedido.setDataValidade(dataValidade.getTime());
		
		pedido.setValorPedido(totalPagar.doubleValue());
		carregaUsuario();
		pedido.setParticipante(objeto.getParticipante());
		pedidos = pedido.getParticipante().getPedidos();
		pedidos.add(pedido);
		objeto.getParticipante().setPedidos(pedidos);
		PedidoControle controle = new PedidoControle();
		Boleto boleto = (pedido.getBoleto()==null)? boleto = new Boleto() : pedido.getBoleto(); 
		
		boleto.setValor(pedido.getValorPedido());
		pedido.setBoleto(boleto);
		
		System.err.println("############produto do item compra novo: "+pedido.getItemsCompra().get(0).getProduto());
		controle.save(pedido);
		
		enviarDadosParaComopra(pedido.getItemsCompra());
		// super.editar();
		pedido = new Pedido();
		items = new ArrayList<ItemCompra>();
		MensagemJSF.exibeMensagemInfo("Pedido efetuado com sucesso!  Acesse o menu pagamento.","Pedido efetuado com sucesso! Acesse o menu pagamento.");
		getItems();
		atualizaPedidos();
		selecaoCompraAblitada = false;
		return null;
	}	
	
	
	
	
	
	public String salvarPedido() {
		List<ItemCompra> itensCompra = new ArrayList<ItemCompra>();
		itensCompra = getItems();
		if(itensCompra==null || itensCompra.size()<1){
			MensagemJSF.exibeMensagemAlerta("Para gerar pedido o mesmo deve ter pelo menos um item - "+cursoPrincipal.getNome()
					, "Para gerar pedido o mesmo deve ter adicionado pelo menos um curso - "+cursoPrincipal.getNome());
			return null;
		}
		
		if(!verificaSePedidoPossuiInscricaoPrincipal()){
			MensagemJSF.exibeMensagemAlerta("Para se inscrever em minicursos deve-se selecionar um principal"
					, "Curso principal é obrigatório");
			return null;
		}
			
		pedido.setItemsCompra(itensCompra);
		boolean erroQuantidade=false;
		for (ItemCompra ic : pedido.getItemsCompra()) {
			if(!quantidadeProdutoDispinivel(ic.getProduto(), ic.getQuantidade()))
				erroQuantidade=true;
			ic.setPedido(pedido);
			
		}
		
		if(erroQuantidade){
			return null;
		}
		
		Calendar hoje = Calendar.getInstance();
		pedido.setDataPedido(hoje.getTime());
		hoje.add(Calendar.DAY_OF_MONTH, 10);
		pedido.setDataValidade(hoje.getTime());
		
		pedido.setValorPedido(totalPagar.doubleValue());
		pedido.setParticipante(objeto.getParticipante());
	
		PedidoControle controle = new PedidoControle();
		Boleto boleto = new Boleto();
		boleto.setValor(pedido.getValorPedido());
		pedido.setBoleto(boleto);
		
		
	
		if(selecaoCompraAblitada)
			controle.save(pedido);
		else
			controle.update(pedido);
		
		enviarDadosParaComopra(pedido.getItemsCompra());
		// super.editar();
		pedido = new Pedido();
		items = new ArrayList<ItemCompra>();
		MensagemJSF.exibeMensagemAlerta("Pedido efetuado com sucesso!  Acesse o menu pagamento.","Pedido efetuado com sucesso! Acesse o menu pagamento.");
		getItems();
		atualizaPedidos();
		selecaoCompraAblitada = false;
		return null;
	}	

	
	private void enviarDadosParaComopra(List<ItemCompra> itensCompra){
/*		Usuario usuario = (Usuario) Contexto.getSessao().getAttribute("Usuario");
		String nomeUsuario = usuario.getNome();
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		emails.put(usuario.getEmail(), nomeUsuario);
		StringBuilder texto = new StringBuilder();
		texto.append("Prezado ").append(nomeUsuario)
		.append(", \n \n Obrigado por realizar um pedido de inscrição no minicurso no Congresso de Gest�o de Pessoas.")
		.append("\n \n A baixo segue os dados do pedido").append("\n Conta a qual deve ser feito o depósito idenficado ou transferência bancária: ")
		.append("\nConta: xxxxx-x  - AG: xxxx-x Banco XXXXXX")
		.append("\n \nItens : \n");//.append(email).append("\n ").append("senha : ").append(senha);
		float totalCompra = 0;
		float preco = 0;
		int quantidade=0;
		for (ItemCompra itemCompra : itensCompra) {
			preco = itemCompra.getProduto().getPreco();
			quantidade = itemCompra.getQuantidade();
			texto.append(itemCompra.getProduto().getNome()+" valor unitario: "+preco+" - quantidade: "+quantidade+" = "+(quantidade*preco)+"\n");
			totalCompra+=preco*quantidade;
		}
		texto.append("\n Total a pagar: "+totalCompra);*/
		
	//	emailx.emailDeTexto(emails, texto.toString(), "Cadastro drosóphila");
	}
	
	
	public void excluirPedido(){
			PedidoControle pedidoControle = new PedidoControle();
			pedidoControle.delete(pedidoPardetalheCompra);
		}

	private void verificPedidosEfetuados(){
			Usuario usuarioLogado = (Usuario) Contexto.getSessao().getAttribute("Usuario");
			Participante participante = new ParticipanteControle().buscaParticipanteUsuarioId(usuarioLogado.getId()); 
			int id = participante.getId();
			
			List<Pedido> pedidos = new PedidoControle().buscaPedidosParticipanteId(id);
			if(pedidos.size()>1){
				this.pedido = pedidos.get(1);  
				items = this.pedido.getItemsCompra();
				selecaoCompraAblitada = false;
			}
	}
	
	private void atualizaPedidos(){
		Usuario usuarioLogado = (Usuario) Contexto.getSessao().getAttribute("Usuario");
		List<Pedido> pedidos = new PedidoControle().buscaPedidosParticipanteId(usuarioLogado.getParticipante().getId());
		this.pedidos = pedidos;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<ItemCompra> getItems() {
		verificPedidosEfetuados();
		return items;
	}

	public void setItems(List<ItemCompra> items) {
		this.items = items;
	}

	public List<Produto> getCursos() {
		List<Pedido> pedidos= buscaParticipante().getPedidos();
		
		if(pedidos.size()>1){
			pedido = pedidos.get(1);
			System.out.println("id do pedido: "+pedido.getId());
			System.err.println("id do pedido: "+pedido.getId());
			cursoPrincipal = pedido.getItemsCompra().get(0).getProduto();
			selecaoCompraAblitada = !pedido.getBoleto().isQuitado();
		}
			
		produtoControle = new ProdutoControle();
		cursos = produtoControle.procuraCurso();

		System.out.println();
		return cursos;
	}
	
	
	public Participante buscaParticipante(){
		
		new UsuarioControle().limpaCacheBanco();
		ParticipanteControle participanteControle= new ParticipanteControle();
		Participante participante = participanteControle.buscaParticipanteUsuarioId(Contexto.getUsuario().getId());
		
		return participante;		
	}
	
	

	public void setCursos(List<Produto> cursos) {
		this.cursos = cursos;
	}

	public Float getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(Float totalPagar) {
		this.totalPagar = totalPagar;
	}
	public List<Pedido> getPedidosRealizados() {
		return pedidosRealizados;
	}
	public void setPedidosRealizados(List<Pedido> pedidosRealizados) {
		this.pedidosRealizados = pedidosRealizados;
	}

	public Pedido getPedidoPardetalheCompra() {
		return pedidoPardetalheCompra;
	}

	public void setPedidoPardetalheCompra(Pedido pedidoPardetalheCompra) {
		this.pedidoPardetalheCompra = pedidoPardetalheCompra;
	}

	public Produto getCursoPrincipal() {
		return cursoPrincipal;
	}

	public void setCursoPrincipal(Produto cursoPrincipal) {
		this.cursoPrincipal = cursoPrincipal;
	}

	public Produto getCursoSecundario() {
		return cursoSecundario;
	}

	public void setCursoSecundario(Produto cursoSecundario) {
		this.cursoSecundario = cursoSecundario;
	}

	public boolean isSelecaoCompraAblitada() {
		return selecaoCompraAblitada;
	}

	public void setSelecaoCompraAblitada(boolean selecaoCompraAblitada) {
		this.selecaoCompraAblitada = selecaoCompraAblitada;
	}

	public boolean isPagamentoCursoEfetuado() {
		return pagamentoCursoEfetuado;
	}

	public void setPagamentoCursoEfetuado(boolean pagamentoCursoEfetuado) {
		this.pagamentoCursoEfetuado = pagamentoCursoEfetuado;
	}

}
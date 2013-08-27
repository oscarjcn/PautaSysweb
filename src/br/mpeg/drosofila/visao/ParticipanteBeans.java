package br.mpeg.drosofila.visao;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import br.mpeg.drosofila.controle.AssociadoControle;
import br.mpeg.drosofila.controle.BoletoControle;
import br.mpeg.drosofila.controle.EstadoControle;
import br.mpeg.drosofila.controle.IndicadorControle;
import br.mpeg.drosofila.controle.InstituicaoControle;
import br.mpeg.drosofila.controle.MunicipioControle;
import br.mpeg.drosofila.controle.ParticipanteControle;
import br.mpeg.drosofila.controle.PedidoControle;
import br.mpeg.drosofila.controle.ProdutoControle;
import br.mpeg.drosofila.controle.UsuarioControle;
import br.mpeg.drosofila.controle.api.IAssociadoControle;
import br.mpeg.drosofila.modelo.Associado;
import br.mpeg.drosofila.modelo.Boleto;
import br.mpeg.drosofila.modelo.Endereco;
import br.mpeg.drosofila.modelo.Estado;
import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.modelo.Instituicao;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.modelo.Municipio;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.modelo.Usuario;
import br.mpeg.drosofila.util.controle.Contexto;
import br.mpeg.drosofila.util.controle.PadraoController;
import br.mpeg.drosofila.util.mail.EnvioEmail;
import br.mpeg.drosofila.util.visao.ControleAcesso;
import br.mpeg.drosofila.util.visao.MensagemJSF;
import br.mpeg.drosofila.util.visao.NavegacaoJSF;

@ManagedBean
@SessionScoped
public class ParticipanteBeans extends PadraoController<Usuario> implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nomeInstituicao;
	private List<Indicador> indicadores;
	private Indicador indicador;
	private List<Instituicao> instituicoes;
	private Instituicao instituicao;
	private List<Estado> estados;
	private Estado estado;
	private List<Municipio> municipios;
	private Municipio municipio = new Municipio();
	private Endereco endereco;
	private Participante participante;
	private boolean inscricaoQuitada;
	private String conteudoResumo;
	private String numeroAssociado;
	private boolean parcelamento = false;
	private List<SelectItem> opcoesParcelamento = new ArrayList<SelectItem>();
	private String cpf;
	private String passaporte;
	private String rg;
	private boolean inscricaoAcompanhante = false;
	private boolean ehSocio = false;

	
	
	public ParticipanteBeans() {
		opcoesParcelamento.add(new SelectItem(false, "À vista"));
		opcoesParcelamento.add(new SelectItem(true, "Parcelado"));
	}
	private void configuraCPFPassaport(){
		String rg = participante.getRg();
		if(rg==null || rg.trim().equals("")){
			passaporte = participante.getCpf();
		}else{
			cpf = participante.getCpf();
		}
	}
	
	public boolean verificaPagtInscricaoParticipante(){
		try{
			return participante.getPedidos().get(0).getBoleto().isQuitado();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public void carregaObjeto(){
		try{
			participante = objeto.getParticipante();
			endereco = objeto.getParticipante().getEndereco();
			indicador = objeto.getParticipante().getIndicador();
			IndicadorControle indicadorControle = new IndicadorControle();
			indicador = indicadorControle.buscaIndicadorParticipante(objeto.getParticipante().getId());
			objeto.getParticipante().setIndicador(indicador);
			if(indicador.getNome().trim().toLowerCase().equals("acompanhante"))
				inscricaoAcompanhante = true;
			else
				inscricaoAcompanhante = false;
			instituicao = objeto.getParticipante().getInstituicao();
			//estado = objeto.getParticipante().getEndereco().getMunicipio().getEstado();
			 //carregaMunicipios() ;
			//municipio = objeto.getParticipante().getEndereco().getMunicipio();
			participante.setEstadopais(objeto.getParticipante().getEstadopais());
			inscricaoQuitada = verificaPagtInscricaoParticipante();
			configuraCPFPassaport();
			rg = participante.getRg();
			
		}catch (NullPointerException e) {
			participante = new Participante();
			
			ProdutoControle produtoControle = new ProdutoControle();
			Produto produto= produtoControle.buscaInscricao();
			Pedido pedido = new Pedido();
			ItemCompra itemCompra= new ItemCompra(pedido, produto, 1);
			pedido.setItemsCompra(Arrays.asList(itemCompra));
			pedido.setParticipante(participante);
			pedido.setDataPedido(Calendar.getInstance().getTime());
			pedido.setValorPedido(produto.getPreco().doubleValue());
			participante.setPedidos(Arrays.asList(pedido));
			pedido.setBoleto(new Boleto());
			inscricaoQuitada = false;
			inscricaoQuitada = verificaPagtInscricaoParticipante();
			
			participante.setNome(objeto.getNome());
			participante.setEmail(objeto.getEmail());
			instituicao = new Instituicao();
			indicador = new Indicador();
			endereco = new Endereco();
			municipio = new Municipio();
			estado = new Estado();
		}
		Index index = (Index) Contexto.getSessao().getAttribute("index");
	//	index.setIndiceAbaAtual(((participante!=null)?Index.INDICE_ABA_PARTICIPANTE:Index.INDICE_ABA_PAGAMENTO));
		NavegacaoJSF.navegaPaginas("homeRefresh");
	}
	
	
	
	private boolean verificaSeParticipanteAssociado(){
		if(!participante.getEmail().isEmpty() && !participante.getEmail().trim().equals("")){
			IAssociadoControle associadoControle = new AssociadoControle();
			Associado a= associadoControle.buscaPorCPF(participante.getCpf(), true);//associadoControle.verificaExiste(participante.getEmail());
			if(a==null){
				/*MensagemJSF.exibeMensagemErro(
						"Seu numero de associado e/ou CPF nÃ£o foram encontrados, por favor verifique se seu cadastro como associado foi efetivado ou sua inscriÃ§Ã£o esta pendente."
						, "Numero do associado e/ou CPF nÃ£o foram encontrados ou sua inscriÃ§Ã£o esta pendente.");*
					associadoControle.save(new Associado(participante.getNumeroSocio(), participante.getNome(), participante.getCpf(), false));
					*/
				MensagemJSF.exibeMensagemErro(
				"Seu CPF não foi encontrados, por favor verifique se seu cadastro como associado foi efetivado ou sua inscrição esta pendente."
				, "CPF não foi encontrado ou sua inscrição esta pendente.");
			//associadoControle.save(new Associado(participante.getNumeroSocio(), participante.getNome(), participante.getCpf(), false));
			
				return false;
			} 			
		}
		return true;
	}
	
	@Override
	public String editar() {
		System.out.println("\n\n"+participante.getCpf());
		ParticipanteControle participanteControle = new ParticipanteControle();
		String identificador = "";  
			if(cpf == null || cpf.trim().equals("")){
				if((this.passaporte == null || this.passaporte.trim().equals(""))){
					MensagemJSF.exibeMensagemErro("Você deve preencher o cpf ou passaporte", "CPF ou Passaporte obrigatórios");
					return "";
				}else{
					identificador = passaporte;
					rg = null;
					cpf = null;
				}
			}else{
				identificador = cpf;
				
				if(!(this.passaporte == null || this.passaporte.trim().equals(""))){
					MensagemJSF.exibeMensagemErro("Por favor insira apenas CPF ou passaporte", "Por favor insira apenas CPF ou passaporte");
					return "";
				}
				if(rg==null || rg.trim().equals("")){
					MensagemJSF.exibeMensagemErro("Você deve preencher o RG ou passaporte", "RG obrigatório para Brasileiros ");
					return "";
				}					
			}
		participante.setRg(rg);
		
		participante.setCpf(identificador);
		
		Participante participanteVericicacao = participanteControle.buscaPorCPF(participante.getCpf());
		
		
		if(participanteVericicacao !=null && !participanteVericicacao.getId().equals(participante.getId())){
			FacesMessage fm = new FacesMessage();
			fm.setDetail(((cpf == null || cpf.trim().equals(""))?"Passaporte":"CPF")+" jÃ¡ existe");
			fm.setSummary(((cpf == null || cpf.trim().equals(""))?"Passaporte":"CPF")+" jÃ¡ existe");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return null;
		}
		Participante participanteVericicacaoRG;
		if(participante.getRg()!=null &&  !participante.getRg().trim().equals(""))
			participanteVericicacaoRG = participanteControle.buscaPorRG(participante.getRg());
		else
			participanteVericicacaoRG = null;
		
		
		if(participanteVericicacaoRG !=null && !participanteVericicacaoRG.getId().equals(participante.getId())){
			FacesMessage fm = new FacesMessage();
			fm.setDetail("RG já existe");
			fm.setSummary("RG já existe");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return null;
		}
		
		
		if(ehSocio && !verificaSeParticipanteAssociado() )
			return null;
		/*Boleto  boleto = new Boleto();
		boleto.setQuitado(false);

		BoletoControle boletoControle = new BoletoControle();
		boletoControle.save(boleto);*/
		
		endereco.setMunicipio(municipio);
		participante.setEndereco(endereco);
		
		instituicao = new Instituicao();
		instituicao.setNome(nomeInstituicao.toUpperCase());
		
		
		IndicadorControle indicadorControle = new IndicadorControle();
		int idIndicadorSelecionado = idIndicadorSelecionado = indicador.getId();
		indicador =  indicadorControle.findById(idIndicadorSelecionado);
		
		participante.setIndicador(indicador);
		if(indicador.getNome().trim().toLowerCase().equals("acompanhante"))
			inscricaoAcompanhante = true;
		else
			inscricaoAcompanhante = false;
		
		
		//participante.setNomeDeAutor(participante.getNomeDeAutor().toUpperCase().replaceAll("\\.", " ").replaceAll(" ", "").replaceFirst(",", ", ").trim());
		
		//participante.setIndicador(indicador);
		participante.setInstituicao(instituicao);
		objeto.setParticipante(participante);
		Contexto.getSessao().setAttribute("Usuario", objeto);
		if(!isInscricaoQuitada()){
			Pedido pedido = participante.getPedidos().get(0);
			pedido.setValorPedido(indicador.getValor().doubleValue());
			
			
			System.out.println(idIndicadorSelecionado);
			enviarEmail();
		}
		String mensagemPagamento="", mensagemSociedade="";
		if(!inscricaoQuitada){
			mensagemPagamento = "SEU CADASTRO FOI CONCLUÍDO COM SUCESSO.CONHEÇA OS MINI CURSOS PRÉ-CONGRESSOS E PARTICIPE!";
			mensagemSociedade = "Por favor, aguarde nossa confirmação junto a sociedade de seu número de sócio, em seguida a sessção de pagamento será¡ liberada.";
		}else{
			mensagemPagamento = "Cadastro atualizado com sucesso.";
		}
		MensagemJSF.exibeMensagemInfo(mensagemPagamento, mensagemPagamento);
		if(participante.getNumeroSocio()!=0)
			MensagemJSF.exibeMensagemErro(mensagemSociedade, mensagemSociedade);
				
		return super.editar();
		
	}

	
	public void enviarEmail(){

		//Index index = new Index();
		//Contexto.getSessao().setAttribute("Index", index);
		
		//String nome = participante.getNome();
		EnvioEmail emailx = new EnvioEmail();
		Map<String, String> emails = new HashMap<String, String>();
		//emails.put(participante.getEmail(), nome);
		emails.put("oscar.chamma@gmail.com", "Oscar");
		StringBuilder texto = new StringBuilder();
		texto.append("Prezado(a) ").append("Oscar").append("\n </ br>")
		
			//.append(", \n \n Obrigado por realizar o seu cadastro Congresso de gestão de pessoas, aguardamos a finalização do seu cadastro e posterior pagamento para efetivar sua inscrição, que pode ser feito atravéss do menu 'Pagamento'.");
			
		.append("Obrigado por realizar seu cadastro no X CONGRESSO DE GESTÃO DE PESSOAS.\n</ br>")
		.append("Aguardamos a finalização do seu processo de inscrição através do pagamento.\n</ br>" )
		.append("Após o pagamento aguarde a confirmação de sua inscrição.\n</ br>" )
		.append("Em caso de dúvidas entre em contato com a organização do evento:\n</ br>" )
		.append("pautaeventos@yahoo.com.br\n<br>" )
		.append("(91)3259.4472");	
		
		//.append(", \n \nObrigado por atualizar o seu cadastro no Congresso de Gestão de Pessoas, atravéss do menu 'Pagamento' você pode escolher a forma de pagar a sua inscrição.")
		
		
		/*.append("\n \nO pagamento pode ser efetuado via depÃ³sito ou tranferÃªncia bancÃ¡ria do valor correspondente a inscriÃ§Ã£o selecionada na seguinte conta do Banco do Brasil" )
		.append("\n AG:3702-8  CONTA: 3	4389-7. \nTambÃ©m pode ser efetuado atravÃ©s do PayPal ou cartÃ£o de crÃ©dito.")*/
			texto.append("\n\n Aóss o pagamento aguarde a confirmação com a liberação dos demais campos do seu cadastro.");
		
		emailx.setComissaoOrganizadora(true);
		emailx.emailDeTexto(emails, texto.toString(), "Congresso de gestão de pessoas");
	}
	
public static void main(String[] args) {
	ParticipanteBeans participanteBeans = new ParticipanteBeans();
	participanteBeans.enviarEmail();
}

	@Override
	public String getTitulo() {
		facade = new UsuarioControle();
		Usuario sessao = (Usuario) Contexto.getSessao().getAttribute("Usuario");
		try{
			objeto = new UsuarioControle().findById(sessao.getId());
		}catch (NullPointerException e) {
			try {
				Contexto.getResponse().sendRedirect("/index.jsf");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//objeto = (Usuario) Contexto.getSessao().getAttribute("Usuario");
		carregaObjeto();
		if(objeto==null){
			return "/index.jsf";
		}
		return super.getTitulo();
	}
	
	
	public void carregaMunicipios() {
		System.out.println("pesquisa dos municipios");
		if (getEstado() != null) {
			System.out.println("pesquisa dos municipios");
			
			municipios = new MunicipioControle().listaPorEstados(getEstado());
		}
    }
	
	public List<Instituicao> completaNome(String query) {
		List<Instituicao> sugestao = new ArrayList<Instituicao>();
		CharSequence q;

		if (query.length() > 0) {

			q = query.toUpperCase().subSequence(0, query.length() - 1);
			this.instituicoes = new InstituicaoControle().findAll();

			for (Instituicao it : instituicoes) {
				if (it.getNome().contains(q)) {
					sugestao.add(it);
					if (it.getNome().trim().toUpperCase().equals(query)) {
						objeto.getParticipante().setInstituicao(it);
					}
				}else{
					setNomeInstituicao(query.toUpperCase());
				}
			}
		}
		return sugestao;
	}
	
	public List<Indicador> getIndicadores() {
		//boolean desconto = participante.getNumeroSocio()!=0;
		boolean desconto = !participante.getEmail().equals("") && ehSocio;
		indicadores = new IndicadorControle().listaIndicadoresParaDataAtual(desconto, parcelamento);
		return indicadores;
	}
	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	public List<Instituicao> getInstituicoes() {
		return instituicoes;
	}
	public void setInstituicoes(List<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}
	public String getNomeInstituicao() {
		if(instituicao!=null && (nomeInstituicao==null || nomeInstituicao.trim().equals("")))
			nomeInstituicao = instituicao.getNome();
		return nomeInstituicao;
	}
	public void setNomeInstituicao(String nomeInstituicao) {
		this.nomeInstituicao = nomeInstituicao;
	}
	public List<Estado> getEstados() {
		estados = new EstadoControle().findAll();
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	public List<Municipio> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<Municipio> municipios) {
		this.municipios = municipios;
	}
	public Municipio getMunicipio() {
		return municipio;
	}
	
	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Participante getParticipante() {
		return participante;
	}
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	public Indicador getIndicador() {
		return indicador;
	}
	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public boolean isInscricaoQuitada() {
		return inscricaoQuitada;
	}

	public void setInscricaoQuitada(boolean inscricaoQuitada) {
		this.inscricaoQuitada = inscricaoQuitada;
	}

	public String getConteudoResumo() {
		return conteudoResumo;
	}

	public void setConteudoResumo(String conteudoResumo) {
		this.conteudoResumo = conteudoResumo;
	}

	public String getNumeroAssociado() {
		if(participante.getNumeroSocio()!=0)
			numeroAssociado = String.valueOf(participante.getNumeroSocio());
		else
			numeroAssociado="";
		return numeroAssociado;
	}

	public void setNumeroAssociado(String numeroAssociado) {
		numeroAssociado = numeroAssociado.trim().equals("")? "0" : numeroAssociado;
		
		participante.setNumeroSocio(Integer.parseInt(numeroAssociado));
		this.numeroAssociado = numeroAssociado;
	}


	public boolean isParcelamento() {
		return parcelamento;
	}


	public void setParcelamento(boolean parcelamento) {
		this.parcelamento = parcelamento;
	}


	public List<SelectItem> getOpcoesParcelamento() {
		return opcoesParcelamento;
	}


	public void setOpcoesParcelamento(List<SelectItem> opcoesParcelamento) {
		this.opcoesParcelamento = opcoesParcelamento;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getPassaporte() {
		return passaporte;
	}
	public void setPassaporte(String passaporte) {
		this.passaporte = passaporte;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public boolean isInscricaoAcompanhante() {
		return inscricaoAcompanhante;
	}
	public void setInscricaoAcompanhante(boolean inscricaoAcompanhante) {
		this.inscricaoAcompanhante = inscricaoAcompanhante;
	}
	public boolean isEhSocio() {
		return ehSocio;
	}
	public void setEhSocio(boolean ehSocio) {
		this.ehSocio = ehSocio;
	}
	
	
	//[A-Za-z0-9]
	
}

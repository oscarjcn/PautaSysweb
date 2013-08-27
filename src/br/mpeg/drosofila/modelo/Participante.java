package br.mpeg.drosofila.modelo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sun.xml.internal.bind.v2.runtime.Name;

import br.mpeg.drosofila.controle.ParticipanteControle;

@Entity
//@Table(name="PARTICIPANTE", schema="crustacio")
@Table(name="participante")
public class Participante implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="NOME_COMPLETO", length=70)
	private String nome;
	@Column(unique=true, name="RG")
	private String rg;
	@Column(unique=true, name="CPF")
	private String cpf;
	@Column(name="TELEFONE", length=15)
	private String telefone;
	@Column(name="EMAIL", length=70)
	private String email;
	@Column(name="FORMACAO", length=70)
	private String formacao;
	
	@Column(name="dDD")
	private int ddd;
	
	@Column(name="SIGLAINSTITUICAO", length=70)
	private String siglaDaInstituicao;
	
	@Column(name="AUTOR", length=70)
	private String nomeDeAutor;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Instituicao instituicao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInscricao;
	
	private int numeroSocio;
	private String nomeNoCrachar;
	private String nomeInstCrachar;	
	private String estadopais;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Endereco endereco;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Indicador indicador;

	@OneToMany( mappedBy="participante", orphanRemoval=true)
	private List<Resumo> resumos;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="participante", fetch=FetchType.EAGER)
	private List<Pedido> pedidos;
	
	@OneToMany(mappedBy="participante", fetch= FetchType.LAZY)
	private List<Comprovante> comprovantes;
	
	@Transient
	private boolean inscricaoQuitada;
	
	public boolean verificaPagtInscricaoParticipante(){
		return this.getPedidos().get(0).getBoleto().isQuitado();
	}
	
	public void configuraEstatusConta(boolean quitado){		
		if(pedidos.get(0).getItemsCompra().get(0).getProduto().getTipo().toUpperCase().trim().equals("I"))
			this.getPedidos().get(0).getBoleto().setQuitado(quitado);
	}
	
	public boolean existePedidosPendentes(){
		List<Pedido> pedidos = getPedidos();
		
		boolean pedidosPagos = true;
		
		for (Pedido pedido : pedidos) {
				pedidosPagos = pedido.getBoleto().isQuitado() && pedidosPagos;  
		}
		
		return pedidosPagos;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFormacao() {
		return formacao;
	}
	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Indicador getIndicador() {
		return indicador;
	}
	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}
	public List<Resumo> getResumos() {
		return resumos;
	}
	public void setResumos(List<Resumo> resumos) {
		this.resumos = resumos;
	}
	public List<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	public boolean isInscricaoQuitada() {
		return verificaPagtInscricaoParticipante();
	}
	public void setInscricaoQuitada(boolean inscricaoQuitada) {
		configuraEstatusConta(inscricaoQuitada);
		this.inscricaoQuitada = inscricaoQuitada;
	}

	public Date getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}

	public int getNumeroSocio() {
		return numeroSocio;
	}

	public void setNumeroSocio(int numeroSocio) {
		this.numeroSocio = numeroSocio;
	}

	public String getNomeNoCrachar() {
		return nomeNoCrachar;
	}

	public void setNomeNoCrachar(String nomeNoCrachar) {
		this.nomeNoCrachar = nomeNoCrachar;
	}

	public String getNomeInstCrachar() {
		return nomeInstCrachar;
	}

	public void setNomeInstCrachar(String nomeInstCrachar) {
		this.nomeInstCrachar = nomeInstCrachar;
	}

	public String getNomeDeAutor() {
		return nomeDeAutor;
	}

	public void setNomeDeAutor(String nomeDeAutor) {
		this.nomeDeAutor = nomeDeAutor;
	}

	public String getSiglaDaInstituicao() {
		return siglaDaInstituicao;
	}

	public void setSiglaDaInstituicao(String siglaDaInstituicao) {
		this.siglaDaInstituicao = siglaDaInstituicao;
	}

	public String getEstadopais() {
		return estadopais;
	}

	public void setEstadopais(String estadopais) {
		this.estadopais= estadopais;
	}

	public List<Comprovante> getComprovantes() {
		return comprovantes;
	}

	public void setComprovantes(List<Comprovante> comprovantes) {
		this.comprovantes = comprovantes;
	}

	public Integer getDdd() {
		return ddd;
	}

	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participante other = (Participante) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public static void testeConexao() throws ClassNotFoundException, SQLException {
	    Class.forName( "com.mysql.jdbc.Driver" );
		Connection con = DriverManager.getConnection (
				"jdbc:mysql://localhost:3306/crustacio", "root", "root");
		//"jdbc:mysql://mysql.pautapromocoes.com.br:3306/pautapromocoes", "pautapromocoes", "blockpensil2013");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(
				"SELECT nome FROM usuario");
				while (rs.next()) {
					System.out.println("achoeu");
				}
	}
	
public static void main(String[] args) {
	try {
		testeConexao();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

	
}

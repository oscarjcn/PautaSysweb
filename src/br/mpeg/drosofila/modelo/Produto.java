package br.mpeg.drosofila.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.mpeg.drosofila.util.modelo.GenericModel;

@Entity
//@Table(name = "PRODUTO", schema = "crustacio")
@Table(name = "produto")
public class Produto extends GenericModel {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String nome;
	@Column
	private String descricao;
	@Column
	private float preco;
	/*@ManyToOne
	private Categoria categoria; */
	private String tipo;
	private String codigoBotao;
	@Column
	private int quantidadeDisponiveis;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getCodigoBotao() {
		return codigoBotao;
	}

	public void setCodigoBotao(String codigoBotao) {
		this.codigoBotao = codigoBotao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getQuantidadeDisponiveis() {
		return quantidadeDisponiveis;
	}

	public void setQuantidadeDisponiveis(int quantidadeDisponiveis) {
		this.quantidadeDisponiveis = quantidadeDisponiveis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", descricao="
				+ descricao + ", preco=" + preco + ", tipo=" + tipo
				+ ", codigoBotao=" + codigoBotao + ", quantidadeDisponiveis="
				+ quantidadeDisponiveis + "]";
	}
	

}

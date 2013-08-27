package br.mpeg.drosofila.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.mpeg.drosofila.util.modelo.GenericModel;

@Entity
//@Table(name = "ITEM_COMPRA", schema = "crustacio")
@Table(name = "item_compra")
public class ItemCompra extends GenericModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Produto produto;
	private Integer quantidade = 1;
	private Integer nivelPrioridade = 0;
	@ManyToOne
	private Pedido pedido;

	public ItemCompra(){	}
	
	public ItemCompra(Produto produto, Integer nivelPrioridade){
		this.produto = produto;
		this.nivelPrioridade = nivelPrioridade;
	}
	
	public ItemCompra(Pedido pedido, Produto produto, Integer quantidade){
		this.pedido = pedido;
		this.produto = produto;
		this.quantidade = quantidade;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getNivelPrioridade() {
		return nivelPrioridade;
	}

	public void setNivelPrioridade(Integer nivelPrioridade) {
		this.nivelPrioridade = nivelPrioridade;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		ItemCompra other = (ItemCompra) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

}

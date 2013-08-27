package br.mpeg.drosofila.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.mpeg.drosofila.controle.BoletoControle;
import br.mpeg.drosofila.controle.PedidoControle;
import br.mpeg.drosofila.util.modelo.GenericModel;
@Entity
//@Table(name="PEDIDO",schema="crustacio")
@Table(name="pedido")
public class Pedido extends GenericModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date dataPedido;
	private Date dataValidade;
	private Double valorPedido;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pedido")
	private List<ItemCompra> itemsCompra;
	// @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@ManyToOne
	private Participante participante;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Boleto boleto;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}
	public Date getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	public Double getValorPedido() {
		return valorPedido;
	}
	public void setValorPedido(Double valorPedido) {
		this.valorPedido = valorPedido;
	}
	public List<ItemCompra> getItemsCompra() {
		return itemsCompra;
	}
	public void setItemsCompra(List<ItemCompra> itemsCompra) {
		this.itemsCompra = itemsCompra;
	}
	public Participante getParticipante() {
		return participante;
	}
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	public Boleto getBoleto() {
		return boleto;
	}
	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	
	
	
	
}

package br.mpeg.drosofila.visao.beans.auxiliares;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.engine.jdbc.SerializableBlobProxy;

import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.modelo.Produto;

public class InscricaoInfo implements Serializable {
	
	private String nome;
	private String descricao;
	private float valor;
	private int quantidadeDisponivel;
	private String tipo;
	private Calendar dataPedido;
	private Date datePedido;
	private String codigoBotao;
	private String codigoBotaoAtividade;
	
	public InscricaoInfo(){	}
	
	public InscricaoInfo(Produto produto, Indicador indicador, Calendar dataPedido){
		descricao = produto.getDescricao();
		quantidadeDisponivel = produto.getQuantidadeDisponiveis();
		nome = indicador.getNome();
		valor = indicador.getValor();
		this.dataPedido = dataPedido;
		datePedido = dataPedido.getTime();
		codigoBotao = indicador.getCodigoBotao();
		codigoBotaoAtividade = indicador.getCodigoBotaoAtividade();
	}
	
	public InscricaoInfo(Produto produto, Indicador indicador, Date dataPedido){
		descricao = produto.getDescricao();
		quantidadeDisponivel = produto.getQuantidadeDisponiveis();
		nome = indicador.getNome();
		valor = indicador.getValor();
		this.dataPedido = new GregorianCalendar();
		this.dataPedido.setTime(dataPedido);
		quantidadeDisponivel = produto.getQuantidadeDisponiveis();
		datePedido = dataPedido;
		codigoBotao = indicador.getCodigoBotao();
		codigoBotaoAtividade = indicador.getCodigoBotaoAtividade();
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
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public int getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}
	public void setQuantidadeDisponivel(int quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Calendar getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Calendar dataPedido) {
		this.dataPedido = dataPedido;
	}
	public Date getDatePedido() {
		return datePedido;
	}
	public void setDatePedido(Date datePedido) {
		this.datePedido = datePedido;
	}

	public String getCodigoBotao() {
		return codigoBotao;
	}

	public void setCodigoBotao(String codigoBotao) {
		this.codigoBotao = codigoBotao;
	}

	public String getCodigoBotaoAtividade() {
		return codigoBotaoAtividade;
	}

	public void setCodigoBotaoAtividade(String codigoBotaoAtividade) {
		this.codigoBotaoAtividade = codigoBotaoAtividade;
	}

	
}

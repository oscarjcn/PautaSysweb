package br.mpeg.drosofila.visao.beans.auxiliares;

import br.mpeg.drosofila.modelo.Produto;

public class ProdutoInfo {

	private Produto produto;
	private long quantidadeVendida;
	private long quantidadePedida;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public long getQuantidadeVendida() {
		return quantidadeVendida;
	}

	public void setQuantidadeVendida(long quantidadeVendida) {
		this.quantidadeVendida = quantidadeVendida;
	}

	public long getQuantidadePedida() {
		return quantidadePedida;
	}

	public void setQuantidadePedida(long quantidadePedida) {
		this.quantidadePedida = quantidadePedida;
	}
	
}

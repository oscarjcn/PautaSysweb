package br.mpeg.drosofila.visao.beans.auxiliares;

import java.sql.Date;
import java.util.List;

import br.mpeg.drosofila.controle.PedidoControle;
import br.mpeg.drosofila.modelo.Comprovante;
import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.modelo.ItemCompra;
import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Pedido;
import br.mpeg.drosofila.modelo.Produto;
import br.mpeg.drosofila.visao.PagamentoBeans;

public class AtividadeInfo {
	public Participante participante;
	public Indicador indicador;
	public Date dataPedidoInscricao;
	public Produto atividade1;
	public Produto atividade2;
	public Comprovante comprovanteAtividade;
	public Comprovante comprovanteEscolaridade;
	public Comprovante comprovantePagamento;
	
	public void setComprovantes(List<Comprovante> comprovantes){
		for(Comprovante comprovante: comprovantes){
			System.out.println(comprovante.getTipo());
			if(comprovante.getTipo().trim().equals(PagamentoBeans.TIPO_COMPROVANTE_ATIVIDADE)){
				comprovanteAtividade = comprovante;
				System.out.println("Comprovante atividade verificado"+comprovante.getTipo());
			}
				
			if(comprovante.getTipo().trim().equals(PagamentoBeans.TIPO_COMPROVANTE_ESCOLARIDADE)){
				System.out.println("Comprovante atividade verificado"+comprovante.getTipo());
				comprovanteEscolaridade = comprovante;
			}
				
			if(comprovante.getTipo().trim().equals(PagamentoBeans.TIPO_COMPROVANTE_PAGAMENTO))
				comprovantePagamento = comprovante;
		}
	}
	
	public void setAtividades(List<Pedido> pedidos){
		PedidoControle pedidoControle = new PedidoControle();
		for(Pedido pedido: pedidos){
			for(ItemCompra item: pedido.getItemsCompra()){
				//if(item.getNivelPrioridade()!=null && item.getNivelPrioridade()==1)
					atividade1 = item.getProduto();
				/*if(item.getNivelPrioridade()!=null && item.getNivelPrioridade()==2)
					atividade2 = item.getProduto();*/
			}
		}
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
	public Date getDataPedidoInscricao() {
		return dataPedidoInscricao;
	}

	public void setDataPedidoInscricao(Date dataPedidoInscricao) {
		this.dataPedidoInscricao = dataPedidoInscricao;
	}

	public Produto getAtividade1() {
		return atividade1;
	}
	public void setAtividade1(Produto atividade1) {
		this.atividade1 = atividade1;
	}
	public Produto getAtividade2() {
		return atividade2;
	}
	public void setAtividade2(Produto atividade2) {
		this.atividade2 = atividade2;
	}
	public Comprovante getComprovanteAtividade() {
		return comprovanteAtividade;
	}
	public void setComprovanteAtividade(Comprovante comprovanteAtividade) {
		this.comprovanteAtividade = comprovanteAtividade;
	}

	public Comprovante getComprovanteEscolaridade() {
		return comprovanteEscolaridade;
	}

	public void setComprovanteEscolaridade(Comprovante comprovanteEscolaridade) {
		this.comprovanteEscolaridade = comprovanteEscolaridade;
	}

	public Comprovante getComprovantePagamento() {
		return comprovantePagamento;
	}

	public void setComprovantePagamento(Comprovante comprovantePagamento) {
		this.comprovantePagamento = comprovantePagamento;
	}

	@Override
	public String toString() {
		return "AtividadeInfo [participante=" + participante + ", indicador="
				+ indicador + ", dataPedidoInscricao=" + dataPedidoInscricao
				+ ", atividade1=" + atividade1 + ", atividade2=" + atividade2
				+ ", comprovanteAtividade=" + comprovanteAtividade
				+ ", comprovanteEscolaridade=" + comprovanteEscolaridade
				+ ", comprovantePagamento=" + comprovantePagamento + "]";
	}
	
	
}

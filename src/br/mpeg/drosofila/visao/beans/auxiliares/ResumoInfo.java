package br.mpeg.drosofila.visao.beans.auxiliares;

import br.mpeg.drosofila.modelo.Participante;
import br.mpeg.drosofila.modelo.Resumo;

public class ResumoInfo {
	
	
	private Participante participante;
	private Resumo resumo;
	
	public Participante getParticipante() {
		return participante;
	}
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	public Resumo getResumo() {
		return resumo;
	}
	public void setResumo(Resumo resumo) {
		this.resumo = resumo;
	}

}

package br.mpeg.drosofila.visao;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.mpeg.drosofila.util.persistencia.HibernateUtil;

public class GerenciaConexaoBanco implements PhaseListener{

	@Override
	public void afterPhase(PhaseEvent arg0) {
				
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}

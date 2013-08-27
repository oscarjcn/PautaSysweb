package br.mpeg.drosofila.controle;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import br.mpeg.drosofila.controle.api.IIndicadorControle;
import br.mpeg.drosofila.modelo.Indicador;
import br.mpeg.drosofila.persistencia.IndicadorDao;
import br.mpeg.drosofila.util.controle.GenericControle;
/**
 * 
 * @author Victor Coutinho
 *
 */
public class IndicadorControle extends GenericControle<Indicador> implements IIndicadorControle {

	public IndicadorControle() {
		super(new IndicadorDao());
	}

	// By Oscar J Chamma Neto
	public List<Indicador> listaIndicadoresParaDataAtual(boolean desconto, boolean parcelado){
		return ((IndicadorDao)getDao()).listaIndicadoresParaDataAtual(new GregorianCalendar().getTime(), desconto, parcelado);
	}
	
	@Override
	public Indicador instaceObject() {
		return super.instanceObject();
	}
	
	public Indicador buscaIndicadorParticipante(int participanteId) {
		return ((IndicadorDao)getDao()).buscaIndicadorParticipante(participanteId);
	}

	public static void main(String[] args) {
		Indicador i = new Indicador();
		i.setNome("ALuno PPBIO");
		i.setPrincipal(false);
		
		IndicadorControle ic = new IndicadorControle();
		ic.update(i);
	}
}

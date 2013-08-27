package br.mpeg.drosofila.controle;

import br.mpeg.drosofila.controle.api.IEstadoControle;
import br.mpeg.drosofila.modelo.Estado;
import br.mpeg.drosofila.persistencia.EstadoDao;
import br.mpeg.drosofila.util.controle.GenericControle;
/**
 * 
 * @author Victor Coutinho
 *
 */
public class EstadoControle extends GenericControle<Estado> implements IEstadoControle {

	public EstadoControle() {
		super(new EstadoDao());
	}

	@Override
	public Estado instaceObject() {
		return super.instanceObject();
	}

	

}

package br.mpeg.drosofila.controle;

import java.util.List;

import br.mpeg.drosofila.controle.api.IMunicipioControle;
import br.mpeg.drosofila.modelo.Estado;
import br.mpeg.drosofila.modelo.Municipio;
import br.mpeg.drosofila.persistencia.MunicipioDao;
import br.mpeg.drosofila.util.controle.GenericControle;

public class MunicipioControle extends GenericControle<Municipio> implements
		IMunicipioControle {

	public MunicipioControle() {
		super(new MunicipioDao());
	}

	@Override
	public Municipio instaceObject() {
		return super.instanceObject();
	}
	
	public List<Municipio> listaPorEstados(Estado est){
		return new MunicipioDao().listForState(est);
	}
	

}

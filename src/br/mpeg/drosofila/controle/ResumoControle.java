package br.mpeg.drosofila.controle;

import java.io.Serializable;
import java.util.List;

import br.mpeg.drosofila.controle.api.IResumoControle;
import br.mpeg.drosofila.modelo.Resumo;
import br.mpeg.drosofila.persistencia.ResumoDao;
import br.mpeg.drosofila.util.controle.GenericControle;

public class ResumoControle extends GenericControle<Resumo> implements IResumoControle, Serializable{

	public ResumoControle() {
		super(new ResumoDao());
	}
	
	public  List<Resumo> update(Resumo obj){
		List<Resumo> resumos = super.update(obj); 
		return resumos;
	}
	
	public List<Resumo> listaResumoRevisorId(int id){
		return ((ResumoDao)getDao()).listaResumoRevisorId(id);
	}
	public List<Resumo> listaResumoAutor(String cpf){
		return ((ResumoDao)getDao()).listaResumoAutor(cpf);
	}
	public List<Resumo> listaEnviados(){
		return ((ResumoDao)getDao()).listaEnviados();
	}
	
	public List<Resumo> listaResumoParticipanteId(int id){
		List<Resumo> resumos = ((ResumoDao)getDao()).listaResumoParticipanteId(id);
		//System.out.println("\n\n"+resumos.size());
		return resumos;
	}
	
	@Override
	public Resumo instaceObject() {
		// TODO Auto-generated method stub
		return super.instanceObject();
	}

	
}

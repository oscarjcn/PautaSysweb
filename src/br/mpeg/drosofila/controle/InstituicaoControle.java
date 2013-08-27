package br.mpeg.drosofila.controle;

import br.mpeg.drosofila.controle.api.IInstituicaoControle;
import br.mpeg.drosofila.modelo.Instituicao;
import br.mpeg.drosofila.persistencia.InstituicaoDao;
import br.mpeg.drosofila.util.controle.GenericControle;

public class InstituicaoControle extends GenericControle<Instituicao> implements
		IInstituicaoControle {

	public InstituicaoControle() {
		super(new InstituicaoDao());
	}

	@Override
	public Instituicao instaceObject() {

		return super.instanceObject();
	}

	public static void main(String[] args) {

		Instituicao i = new Instituicao();
		i.setNome("UNICAMPINAS");
		new InstituicaoControle().save(i);
		
	}
}

package br.mpeg.drosofila.modelo;

import static org.junit.Assert.*;
import org.junit.Test;

import br.mpeg.drosofila.controle.ParticipanteControle;


public class ParticipanteTest {

	@Test
	public void verificaSeParticipanteAtualizaInformacoesBoletoAoSalvar() {
		ParticipanteControle participanteControle = new  ParticipanteControle();
		Participante participante = participanteControle.findById(1);
		System.out.println(participante.isInscricaoQuitada());
		
		participante.setInscricaoQuitada(true);
		participanteControle.update(participante);
		
		participante = null; 
		participante = participanteControle.findById(1);
		System.out.println(participante.isInscricaoQuitada());
		assertEquals(participante.isInscricaoQuitada(), true);
		//assertTrue(participante.isInscricaoQuitada());
	}
	
}

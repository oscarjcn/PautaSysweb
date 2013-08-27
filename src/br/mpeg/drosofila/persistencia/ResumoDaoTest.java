package br.mpeg.drosofila.persistencia;

import static junit.framework.Assert.*;

import org.junit.Test;


public class ResumoDaoTest {

	static final private int  QUANTIDADE_MINIMA_REUMOS_ESPERADA = 1;
	
	@Test
	public void verificaQuantidadeResumosAutores(){
		ResumoDao resumoDao = new ResumoDao();
		assertTrue(resumoDao.listaResumoAutor("88160556268").size()>QUANTIDADE_MINIMA_REUMOS_ESPERADA);
	}
	public static void main(String[] args) {
		ResumoDao resumoDao = new ResumoDao();
		System.out.println(resumoDao.listaResumoAutor("88160556268").size());
	}
	
}

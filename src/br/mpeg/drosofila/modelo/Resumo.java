/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mpeg.drosofila.modelo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.mpeg.drosofila.controle.UsuarioControle;

/**
 * 
 * @author ojneto
 */
@Entity
//@Table(name = "RESUMO", schema = "crustacio")
@Table(name = "resumo")
public class Resumo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	private Participante participante;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String tema;
	private String titulo;
	private String cpfAutor;
	private String demaisAutores;
	private String emails;
	private String formaApresentacao;
	private String tipoArquivo;
	private String caminhoArquivo;
	@Lob
	@Column(columnDefinition="TEXT")
	private String conteudo;
	private String comentarioResumo;
	private Date dataSubmissao;
	private boolean indicacaoOral;

	
	//@Column(length=2000)
	//@Transient
	@Lob
	private byte[] resumo;
	@ManyToOne(fetch=FetchType.EAGER)
	private StatusResumo statusResumo;
	@ManyToOne(fetch=FetchType.EAGER)
	private Usuario revisor;
	@ManyToOne(fetch=FetchType.EAGER)
	private Tema temaSelecionado;
	
	@Transient
	private StreamedContent streamedArquivoResumo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpfAutor() {
		return cpfAutor;
	}

	public void setCpfAutor(String cpfAutor) {
		this.cpfAutor = cpfAutor;
	}

	public String getDemaisAutores() {
		return demaisAutores;
	}

	public void setDemaisAutores(String demaisAutores) {
		this.demaisAutores = demaisAutores;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public byte[] getResumo() {
		return resumo;
	}

	public void setResumo(byte[] resumo) {
		this.resumo = resumo;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public StatusResumo getStatusResumo() {
		return statusResumo;
	}

	public void setStatusResumo(StatusResumo statusResumo) {
		this.statusResumo = statusResumo;
	}
	
	public Usuario getRevisor() {
		return revisor;
	}

	public void setRevisor(Usuario revisor) {
		this.revisor = revisor;
	}

	public String getComentarioResumo() {
		return comentarioResumo;
	}

	public void setComentarioResumo(String comentarioResumo) {
		this.comentarioResumo = comentarioResumo;
	}

	public String getFormaApresentacao() {
		return formaApresentacao;
	}

	public void setFormaApresentacao(String formaApresentacao) {
		this.formaApresentacao = formaApresentacao;
	}
	
	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public Tema getTemaSelecionado() {
		return temaSelecionado;
	}

	public void setTemaSelecionado(Tema temaSelecionado) {
		this.temaSelecionado = temaSelecionado;
	}

	public boolean isIndicacaoOral() {
		return indicacaoOral;
	}

	public void setIndicacaoOral(boolean indicacaoOral) {
		this.indicacaoOral = indicacaoOral;
	}

	public StreamedContent baixarArquivoResumo(Resumo resumo){
		StreamedContent arquivoComprovante = null;
		InputStream arquivoInput;
		if(resumo!=null && resumo.getResumo()!=null){
			//System.out.println("\n\nConteudo do resumo: "+resumo.getResumo());
			arquivoInput = new ByteArrayInputStream(resumo.getResumo());
			arquivoComprovante = new DefaultStreamedContent(arquivoInput, resumo.getTipoArquivo(), UsuarioControle.limparNomeArquivo(resumo.getCaminhoArquivo()));
		}else{
			arquivoInput = null;
		}
		return arquivoComprovante;
	}
	
	public StreamedContent getStreamedArquivoResumo() {
		return streamedArquivoResumo =  baixarArquivoResumo(this);
	}

	public void setStreamedArquivoResumo(StreamedContent streamedArquivoResumo) {
		this.streamedArquivoResumo = streamedArquivoResumo;
	}
	public Date getDataSubmissao() {
		return dataSubmissao;
	}

	public void setDataSubmissao(Date dataSubmissao) {
		this.dataSubmissao = dataSubmissao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cpfAutor == null) ? 0 : cpfAutor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resumo other = (Resumo) obj;
		if (cpfAutor == null) {
			if (other.cpfAutor != null)
				return false;
		} else if (!cpfAutor.equals(other.cpfAutor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Resumo clone()  {
		Resumo clone = new Resumo();
		clone.setId(this.id);
		clone.setCaminhoArquivo(this.caminhoArquivo);
		clone.setCpfAutor(this.cpfAutor);
		clone.setDemaisAutores(this.demaisAutores);
		clone.setParticipante(this.participante);
		clone.setTema(this.tema);
		clone.setTitulo(this.titulo);
		clone.setConteudo(this.conteudo);
		return clone;
	}
	
	@Override
	public String toString() {
		return "Resumo [tema=" + tema + ", titulo=" + titulo + ", cpfAutor="
				+ cpfAutor + "]";
	}
}

package br.mpeg.drosofila.modelo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.CascadeType;
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

import br.mpeg.drosofila.visao.PagamentoBeans;

import com.sun.imageio.plugins.common.InputStreamAdapter;

@Entity
//@Table(name = "COMPROVANTE", schema = "crustacio")
@Table(name = "comprovante")
public class Comprovante  implements Serializable{
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private String tipo;
	private String tipoArquivo;
	private String nomeArquivo;
	private String caminhoArquivo;
	//@Transient
	@Lob
	private byte[] comprovante;
	private long tamanhoArquivo;
	@Transient
	private File arquivoComroante;
	@Transient
	private StreamedContent  streamedContent;
	private String extencao;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Participante participante;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}
	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}
	public byte[] getComprovante() {
		return comprovante;
	}
	public void setComprovante(byte[] comprovante) {
		this.comprovante = comprovante;
	}
	public long getTamanhoArquivo() {
		return tamanhoArquivo;
	}
	public void setTamanhoArquivo(long tamanhoArquivo) {
		this.tamanhoArquivo = tamanhoArquivo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	public Participante getParticipante() {
		return participante;
	}
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}
	
	
	public File getArquivoComroante() {
		return arquivoComroante;
	}
	public void setArquivoComroante(File arquivoComroante) {
		this.arquivoComroante = arquivoComroante;
	}
	public String getTipoArquivo() {
		return tipoArquivo;
	}
	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}
	public String getExtencao() {
		return extencao;
	}
	public void setExtencao(String extencao) {
		this.extencao = extencao;
	}
	public StreamedContent getStreamedContent() {
		String nomeArquivo=
				"comprovante_"+(tipo.endsWith(PagamentoBeans.TIPO_COMPROVANTE_ESCOLARIDADE)?"escolaridade":
					((tipo.endsWith(PagamentoBeans.TIPO_COMPROVANTE_PAGAMENTO)? "pagamento":"pagamento-minicurso")));
		
		InputStream arquivoInput;
		arquivoInput = new ByteArrayInputStream(comprovante);
		streamedContent = new DefaultStreamedContent(arquivoInput, this.getTipo(), 
					nomeArquivo+this.getExtencao());
		
		return streamedContent;
	}
	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nomeArquivo == null) ? 0 : nomeArquivo.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Comprovante other = (Comprovante) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeArquivo == null) {
			if (other.nomeArquivo != null)
				return false;
		} else if (!nomeArquivo.equals(other.nomeArquivo))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}
	
	
	
	
	
}

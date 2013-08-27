package br.mpeg.drosofila.modelo;

import java.io.File;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.mpeg.drosofila.util.modelo.GenericModel;
@Entity
//@Table(name="BOLETO", schema="crustacio")
//@Table(name="boleto", schema="crustacio")
@Table(name="boleto")
public class Boleto extends GenericModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Long numeroDocumento;
	private Long nossoNumero;
	private Double valor;
	@Transient
	private Date dataVencimento;
	private boolean quitado;
	@Transient
	private File boleta;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(Long numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public Long getNossoNumero() {
		return nossoNumero;
	}
	public void setNossoNumero(Long nossoNumero) {
		this.nossoNumero = nossoNumero;
	}
	public boolean isQuitado() {
		return quitado;
	}
	public void setQuitado(boolean quitado) {
		this.quitado = quitado;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	public File getBoleta() {
		return boleta;
	}
	public void setBoleta(File boleta) {
		this.boleta = boleta;
	}
	
	
}

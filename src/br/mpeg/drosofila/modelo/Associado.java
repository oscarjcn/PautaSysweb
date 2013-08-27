package br.mpeg.drosofila.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.mpeg.drosofila.util.modelo.GenericModel;


@Entity
//@Table(name="ASSOCIADO", schema="crustacio")
//@Table(name="associado", schema="crustacio")
@Table(name="associado")
public class Associado extends GenericModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	private Integer numero = 0;
	private String nome;
	private String cpf;
	private boolean pagamentoEmDia = true; 
	private boolean invalido = false;
	private String email;
	
	public Associado() {}
	
	public Associado(Integer numero, String nome, String cpf,
			boolean pagamentoEmDia) {
		super();
		this.numero = numero;
		this.nome = nome;
		this.cpf = cpf;
		this.pagamentoEmDia = pagamentoEmDia;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public boolean isPagamentoEmDia() {
		return pagamentoEmDia;
	}
	public void setPagamentoEmDia(boolean pagamentoEmDia) {
		this.pagamentoEmDia = pagamentoEmDia;
	}

	public boolean isInvalido() {
		return invalido;
	}

	public void setInvalido(boolean invalido) {
		this.invalido = invalido;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + numero;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (Associado.class != obj.getClass())
			return false;
		Associado other = (Associado) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero != other.numero)
			return false;
		return true;
	}
		
}

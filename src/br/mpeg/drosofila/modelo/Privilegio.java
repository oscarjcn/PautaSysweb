package br.mpeg.drosofila.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import sun.security.util.BigInt;

@Entity
//@Table(name="PRIVILEGIO", schema="crustacio")
@Table(name="privilegio")
public class Privilegio implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Papel papel;
	private String permissao;
	private int nivelDePermissao;
	private String areaPermitida;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Papel getPapel() {
		return papel;
	}
	public void setPapel(Papel papel) {
		this.papel = papel;
	}
	public String getPermissao() {
		return permissao;
	}
	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}
	public int getNivelDePermissao() {
		return nivelDePermissao;
	}
	public void setNivelDePermissao(int nivelDePermissao) {
		this.nivelDePermissao = nivelDePermissao;
	}
	public String getAreaPermitida() {
		return areaPermitida;
	}
	public void setAreaPermitida(String areaPermitida) {
		this.areaPermitida = areaPermitida;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((areaPermitida == null) ? 0 : areaPermitida.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((permissao == null) ? 0 : permissao.hashCode());
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
		Privilegio other = (Privilegio) obj;
		if (areaPermitida == null) {
			if (other.areaPermitida != null)
				return false;
		} else if (!areaPermitida.equals(other.areaPermitida))
			return false;
		if (id != other.id)
			return false;
		if (permissao == null) {
			if (other.permissao != null)
				return false;
		} else if (!permissao.equals(other.permissao))
			return false;
		return true;
	}
	
	
	
}

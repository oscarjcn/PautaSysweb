package br.mpeg.drosofila.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="status_resumo")
public class StatusResumo implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	public String nome;
	public String descricao;
	@OneToMany(mappedBy="statusResumo")
	public List<Resumo> resumos;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<Resumo> getResumos() {
		return resumos;
	}
	public void setResumos(List<Resumo> resumos) {
		this.resumos = resumos;
	}
	
}

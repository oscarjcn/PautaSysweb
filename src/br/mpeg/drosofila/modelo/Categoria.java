package br.mpeg.drosofila.modelo;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*@Entity
@Table(name = "PRODUTO", schema = "crustacio")*/
@Table(name = "categoria")
public class Categoria {
	
	@Id @GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id;
	private String nome;
	
	/*@OneToMany
	@JoinColumn(name="categoria_id")*/
	private List<Produto> produtos;	
	
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
	
}

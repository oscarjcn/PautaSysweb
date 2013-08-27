package br.mpeg.drosofila.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import br.mpeg.drosofila.util.modelo.GenericModel;


/**
 * 
 * @author Victor Coutinho
 *
 */
@Entity
//@Table(name="USUARIO", schema="crustacio")
@Table(name="usuario")
public class Usuario extends GenericModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true)
	private String email;
	private String nome;
	private String senha;
	private boolean administrador;
	@OneToOne(cascade=CascadeType.ALL)
	private Participante participante;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="usuario_privilegio"//USUARIO_PRIVILEGIO
		, joinColumns=@JoinColumn(name="USUARIO_ID", referencedColumnName="id")
		, inverseJoinColumns=@JoinColumn(name="PAPEL_ID", referencedColumnName="id"))
	private List<Papel> papeis;
	
	/*@ManyToMany
	@JoinTable(name="RESUMOS_DELEGADOS"
				,joinColumns=@JoinColumn(name="USUARIO_ID", referencedColumnName="id")
				,inverseJoinColumns=@JoinColumn(name="RESUMO_ID", referencedColumnName="id"))*/
	@OneToMany(mappedBy="revisor")
	private List<Resumo> resumosDelegados;
	
	public Usuario(){	
	}
	
	public Usuario(String email, String nome, String senha) {
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		this.administrador = false;
	}
	public Integer getId() {
		return (Integer) id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isAdministrador() {
		return administrador;
	}
	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
	public Participante getParticipante() {
		return participante;
	}
	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public List<Papel> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}

	public List<Resumo> getResumosDelegados() {
		return resumosDelegados;
	}

	public void setResumosDelegados(List<Resumo> resumosDelegados) {
		this.resumosDelegados = resumosDelegados;
	}

}

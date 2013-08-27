/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mpeg.drosofila.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ojneto
 */
@Entity
//@Table(name="INSTITUICAO", schema="crustacio")
@Table(name="instituicao")
public class Instituicao  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    private String nome;
    @OneToMany(mappedBy = "instituicao")
    private List<Participante> participantes;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	public void setParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}

	public List<Participante> getParticipantes() {
		return participantes;
	}
    

}

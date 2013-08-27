/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.mpeg.drosofila.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.mpeg.drosofila.util.modelo.GenericModel;

/**
 *
 * @author ojneto
 */
@Entity
//@Table(name="ENDERECO", schema="crustacio")
@Table(name="endereco")
public class Endereco extends GenericModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    

    private String logradouro;
    private String cep;
    private String complemento;
    private String bairro;

    @ManyToOne
    @Transient
    private Municipio municipio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getBairro() {
		return bairro;
	}
    public void setBairro(String bairro) {
		this.bairro = bairro;
	}

    
}

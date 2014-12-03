package br.com.bb.controle.arh.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the demanda database table.
 * 
 */
@Entity
@NamedQuery(name = "Demanda.findAll", query = "SELECT d FROM Demanda d")
public class Demanda implements IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	private int numero;

	private int acao;

	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_fim")
	private Date dtFim;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ini")
	private Date dtIni;

	private int esforco;

	// bi-directional many-to-many association to Funcionario
	@ManyToMany(mappedBy = "demandas")
	private List<Funcionario> funcionarios;

	// bi-directional many-to-many association to Impacto
	@ManyToMany(mappedBy = "demandas")
	private List<Impacto> impactos;

	public Demanda() {
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getAcao() {
		return this.acao;
	}

	public void setAcao(int acao) {
		this.acao = acao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDtFim() {
		return this.dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public Date getDtIni() {
		return this.dtIni;
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}

	public int getEsforco() {
		return this.esforco;
	}

	public void setEsforco(int esforco) {
		this.esforco = esforco;
	}

	public List<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Impacto> getImpactos() {
		return this.impactos;
	}

	public void setImpactos(List<Impacto> impactos) {
		this.impactos = impactos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numero;
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
		Demanda other = (Demanda) obj;
		if (numero != other.numero)
			return false;
		return true;
	}

}
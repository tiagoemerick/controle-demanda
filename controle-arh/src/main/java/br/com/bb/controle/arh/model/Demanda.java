package br.com.bb.controle.arh.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the demanda database table.
 * 
 */
@Entity
@NamedQuery(name = "Demanda.findAll", query = "SELECT d FROM Demanda d")
public class Demanda implements IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	private Long numero;

	private Integer acao;

	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_fim")
	private Date dtFim;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ini")
	private Date dtIni;

	private Integer esforco;

	// bi-directional many-to-many association to Funcionario
	@ManyToMany(mappedBy = "demandas")
	private List<Funcionario> funcionarios;

	// bi-directional many-to-many association to Impacto
	@ManyToMany(mappedBy = "demandas")
	private List<Impacto> impactos;

	public Demanda() {
	}

	public Long getNumero() {
		return this.numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getAcao() {
		return this.acao;
	}

	public void setAcao(Integer acao) {
		this.acao = acao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDtFimFormatada() {
		if (getDtFim() != null) {
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			return sf.format(getDtFim());
		}
		return "";
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

	public String getDtIniFormatada() {
		if (getDtIni() != null) {
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			return sf.format(getDtIni());
		}
		return "";
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}

	public Integer getEsforco() {
		return this.esforco;
	}

	public void setEsforco(Integer esforco) {
		this.esforco = esforco;
	}

	public List<Funcionario> getFuncionarios() {
		if (this.funcionarios == null) {
			this.funcionarios = new ArrayList<Funcionario>();
		}
		return this.funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Impacto> getImpactos() {
		if (this.impactos == null) {
			this.impactos = new ArrayList<Impacto>();
		}
		return this.impactos;
	}

	public void setImpactos(List<Impacto> impactos) {
		this.impactos = impactos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		Long result = 1l;
		result = prime * result + numero;
		return result.intValue();
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

	@Override
	@Transient
	public Object getId() {
		return this.getNumero();
	}

	@Override
	public void setId(Object id) {
		setNumero(Long.valueOf(String.valueOf(id)));
	}

}
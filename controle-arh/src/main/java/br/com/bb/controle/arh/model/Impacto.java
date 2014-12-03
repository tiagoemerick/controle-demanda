package br.com.bb.controle.arh.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the impacto database table.
 * 
 */
@Entity
@NamedQuery(name = "Impacto.findAll", query = "SELECT i FROM Impacto i")
public class Impacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String descricao;

	// bi-directional many-to-many association to Demanda
	@ManyToMany
	@JoinTable(name = "demanda_has_impacto", joinColumns = { @JoinColumn(name = "impacto_id") }, inverseJoinColumns = { @JoinColumn(name = "Demanda_numero") })
	private List<Demanda> demandas;

	public Impacto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Demanda> getDemandas() {
		return this.demandas;
	}

	public void setDemandas(List<Demanda> demandas) {
		this.demandas = demandas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Impacto other = (Impacto) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
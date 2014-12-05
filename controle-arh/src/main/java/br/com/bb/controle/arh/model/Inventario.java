package br.com.bb.controle.arh.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

/**
 * The persistent class for the inventario database table.
 * 
 */
@Entity
@NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i")
public class Inventario implements IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "num_bem")
	private String numBem;

	@Column(name = "chave_funcionario")
	private String chaveFuncionario;

	private String descricao;

	// bi-directional many-to-many association to Funcionario
	@ManyToMany
	@JoinTable(name = "funcionario_has_inventario", joinColumns = { @JoinColumn(name = "inventario_num_bem") }, inverseJoinColumns = { @JoinColumn(name = "Funcionario_chave") })
	private List<Funcionario> funcionarios;

	public Inventario() {
	}

	public String getNumBem() {
		return this.numBem;
	}

	public void setNumBem(String numBem) {
		this.numBem = numBem;
	}

	public String getChaveFuncionario() {
		return this.chaveFuncionario;
	}

	public void setChaveFuncionario(String chaveFuncionario) {
		this.chaveFuncionario = chaveFuncionario;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Funcionario> getFuncionarios() {
		return this.funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numBem == null) ? 0 : numBem.hashCode());
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
		Inventario other = (Inventario) obj;
		if (numBem == null) {
			if (other.numBem != null)
				return false;
		} else if (!numBem.equals(other.numBem))
			return false;
		return true;
	}
	
	@Override
	@Transient
	public Object getId() {
		return this.getNumBem();
	}

	@Override
	public void setId(Object id) {
		setNumBem(String.valueOf(id));
	}

}
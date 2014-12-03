package br.com.bb.controle.arh.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * The persistent class for the funcionario database table.
 * 
 */
@Entity
@NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f")
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String chave;

	private int equipe;

	private String hash;

	private String nome;

	@Column(name = "trocar_senha")
	private Boolean trocarSenha;

	// bi-directional many-to-many association to Demanda
	@ManyToMany
	@JoinTable(name = "funcionario_has_demanda", joinColumns = { @JoinColumn(name = "Funcionario_chave") }, inverseJoinColumns = { @JoinColumn(name = "Demanda_numero") })
	private List<Demanda> demandas;

	// bi-directional many-to-one association to FuncionarioHasMeta
	@OneToMany(mappedBy = "funcionario")
	private List<FuncionarioHasMeta> funcionarioHasMetas;

	// bi-directional many-to-many association to Inventario
	@ManyToMany(mappedBy = "funcionarios")
	private List<Inventario> inventarios;

	public Funcionario() {
	}

	public String getChave() {
		return this.chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public int getEquipe() {
		return this.equipe;
	}

	public void setEquipe(int equipe) {
		this.equipe = equipe;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getTrocarSenha() {
		return this.trocarSenha;
	}

	public void setTrocarSenha(Boolean trocarSenha) {
		this.trocarSenha = trocarSenha;
	}

	public List<Demanda> getDemandas() {
		return this.demandas;
	}

	public void setDemandas(List<Demanda> demandas) {
		this.demandas = demandas;
	}

	public List<FuncionarioHasMeta> getFuncionarioHasMetas() {
		return this.funcionarioHasMetas;
	}

	public void setFuncionarioHasMetas(List<FuncionarioHasMeta> funcionarioHasMetas) {
		this.funcionarioHasMetas = funcionarioHasMetas;
	}

	public FuncionarioHasMeta addFuncionarioHasMeta(FuncionarioHasMeta funcionarioHasMeta) {
		getFuncionarioHasMetas().add(funcionarioHasMeta);
		funcionarioHasMeta.setFuncionario(this);

		return funcionarioHasMeta;
	}

	public FuncionarioHasMeta removeFuncionarioHasMeta(FuncionarioHasMeta funcionarioHasMeta) {
		getFuncionarioHasMetas().remove(funcionarioHasMeta);
		funcionarioHasMeta.setFuncionario(null);

		return funcionarioHasMeta;
	}

	public List<Inventario> getInventarios() {
		return this.inventarios;
	}

	public void setInventarios(List<Inventario> inventarios) {
		this.inventarios = inventarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chave == null) ? 0 : chave.hashCode());
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
		Funcionario other = (Funcionario) obj;
		if (chave == null) {
			if (other.chave != null)
				return false;
		} else if (!chave.equals(other.chave))
			return false;
		return true;
	}

}
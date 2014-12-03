package br.com.bb.controle.arh.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the meta database table.
 * 
 */
@Entity
@NamedQuery(name="Meta.findAll", query="SELECT m FROM Meta m")
public class Meta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_limite")
	private Date dtLimite;

	//bi-directional many-to-one association to FuncionarioHasMeta
	@OneToMany(mappedBy="meta")
	private List<FuncionarioHasMeta> funcionarioHasMetas;

	public Meta() {
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

	public Date getDtLimite() {
		return this.dtLimite;
	}

	public void setDtLimite(Date dtLimite) {
		this.dtLimite = dtLimite;
	}

	public List<FuncionarioHasMeta> getFuncionarioHasMetas() {
		return this.funcionarioHasMetas;
	}

	public void setFuncionarioHasMetas(List<FuncionarioHasMeta> funcionarioHasMetas) {
		this.funcionarioHasMetas = funcionarioHasMetas;
	}

	public FuncionarioHasMeta addFuncionarioHasMeta(FuncionarioHasMeta funcionarioHasMeta) {
		getFuncionarioHasMetas().add(funcionarioHasMeta);
		funcionarioHasMeta.setMeta(this);

		return funcionarioHasMeta;
	}

	public FuncionarioHasMeta removeFuncionarioHasMeta(FuncionarioHasMeta funcionarioHasMeta) {
		getFuncionarioHasMetas().remove(funcionarioHasMeta);
		funcionarioHasMeta.setMeta(null);

		return funcionarioHasMeta;
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
		Meta other = (Meta) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
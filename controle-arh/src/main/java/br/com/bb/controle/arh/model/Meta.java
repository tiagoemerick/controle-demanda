package br.com.bb.controle.arh.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the meta database table.
 * 
 */
@Entity
@NamedQuery(name = "Meta.findAll", query = "SELECT m FROM Meta m")
public class Meta implements IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_limite")
	private Date dtLimite;

	// bi-directional many-to-one association to FuncionarioHasMeta
	@OneToMany(mappedBy = "meta")
	private List<FuncionarioHasMeta> funcionarioHasMetas;

	@Transient
	private Boolean atendido;

	@Transient
	private Date dtLimiteIniPesquisa;

	@Transient
	private Date dtLimiteFimPesquisa;

	public Meta() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void setId(Object id) {
		setId(Integer.valueOf(String.valueOf(id)));
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

	public String getDtLimiteFormatada() {
		if (getDtLimite() != null) {
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			return sf.format(getDtLimite());
		}
		return "";
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

	public Date getDtLimiteIniPesquisa() {
		return dtLimiteIniPesquisa;
	}

	public void setDtLimiteIniPesquisa(Date dtLimiteIniPesquisa) {
		this.dtLimiteIniPesquisa = dtLimiteIniPesquisa;
	}

	public Date getDtLimiteFimPesquisa() {
		return dtLimiteFimPesquisa;
	}

	public void setDtLimiteFimPesquisa(Date dtLimiteFimPesquisa) {
		this.dtLimiteFimPesquisa = dtLimiteFimPesquisa;
	}

	public Boolean getAtendido() {
		return atendido;
	}

	public void setAtendido(Boolean atendido) {
		this.atendido = atendido;
	}

}
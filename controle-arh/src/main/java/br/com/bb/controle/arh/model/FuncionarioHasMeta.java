package br.com.bb.controle.arh.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the funcionario_has_meta database table.
 * 
 */
@Entity
@Table(name="funcionario_has_meta")
@NamedQuery(name="FuncionarioHasMeta.findAll", query="SELECT f FROM FuncionarioHasMeta f")
public class FuncionarioHasMeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FuncionarioHasMetaPK id;

	private Boolean atendido;

	//bi-directional many-to-one association to Funcionario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Funcionario_chave")  
	private Funcionario funcionario;

	//bi-directional many-to-one association to Meta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Meta_id")
	private Meta meta;

	public FuncionarioHasMeta() {
	}

	public FuncionarioHasMetaPK getId() {
		return this.id;
	}

	public void setId(FuncionarioHasMetaPK id) {
		this.id = id;
	}

	public Boolean getAtendido() {
		return this.atendido;
	}

	public void setAtendido(Boolean atendido) {
		this.atendido = atendido;
	}

	public Funcionario getFuncionario() {
		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Meta getMeta() {
		return this.meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
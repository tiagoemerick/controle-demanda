package br.com.bb.controle.arh.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the funcionario_has_meta database table.
 * 
 */
@Embeddable
public class FuncionarioHasMetaPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable = false, updatable = false)
	private String funcionario_chave;

	@Column(insertable = false, updatable = false)
	private int meta_id;

	public FuncionarioHasMetaPK() {
	}

	public String getFuncionario_chave() {
		return this.funcionario_chave;
	}

	public void setFuncionario_chave(String funcionario_chave) {
		this.funcionario_chave = funcionario_chave;
	}

	public int getMeta_id() {
		return this.meta_id;
	}

	public void setMeta_id(int meta_id) {
		this.meta_id = meta_id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FuncionarioHasMetaPK)) {
			return false;
		}
		FuncionarioHasMetaPK castOther = (FuncionarioHasMetaPK) other;
		return this.funcionario_chave.equals(castOther.funcionario_chave) && (this.meta_id == castOther.meta_id);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.funcionario_chave.hashCode();
		hash = hash * prime + this.meta_id;

		return hash;
	}
}
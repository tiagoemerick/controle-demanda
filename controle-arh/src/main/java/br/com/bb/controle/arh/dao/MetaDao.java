package br.com.bb.controle.arh.dao;

import java.io.Serializable;

import br.com.bb.controle.arh.model.Meta;
import br.com.bb.controle.arh.util.Constants;

public class MetaDao extends GenericDao<Meta> implements Serializable {

	private static String INSERT_FUNCIONARIO_HAS_META = "insert into " + Constants.database.SCHEMA + ".Funcionario_has_Meta (Funcionario_chave, Meta_id, atendido) values (':chave', :metaId, :atendido)";

	private static final long serialVersionUID = 1L;

	public MetaDao() {
		super(Meta.class);
	}

	public void insertFuncionarioMeta(String chave, Integer metaId, Boolean atendido) {
		String finalInsert = INSERT_FUNCIONARIO_HAS_META.replace(":chave", chave).replace(":metaId", String.valueOf(metaId)).replace(":atendido", String.valueOf(atendido));

		super.executeNativeSql(finalInsert);
	}

}

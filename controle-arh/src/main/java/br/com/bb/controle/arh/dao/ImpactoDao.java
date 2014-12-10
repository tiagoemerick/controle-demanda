package br.com.bb.controle.arh.dao;

import java.io.Serializable;

import br.com.bb.controle.arh.model.Impacto;
import br.com.bb.controle.arh.util.Constants;

public class ImpactoDao extends GenericDao<Impacto> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String INSERT_IMPACTO_TAREFA = "insert into " + Constants.database.SCHEMA + ".Tarefa_has_impacto (impacto_id, Tarefa_id, acao) values (:impactoId, :tarefaId, :acao)";

	public ImpactoDao() {
		super(Impacto.class);
	}

	public void vincularTarefasImpacto(Integer idImpacto, Integer idTarefa, Integer acao) {
		String finalInsert = INSERT_IMPACTO_TAREFA.replace(":impactoId", String.valueOf(idImpacto)).replace(":tarefaId", String.valueOf(idTarefa)).replace(":acao", String.valueOf(acao));

		super.executeNativeSql(finalInsert);
	}

}

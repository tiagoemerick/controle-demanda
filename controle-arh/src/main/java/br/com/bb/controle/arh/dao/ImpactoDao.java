package br.com.bb.controle.arh.dao;

import java.io.Serializable;
import java.util.List;

import br.com.bb.controle.arh.model.Impacto;
import br.com.bb.controle.arh.model.Tarefa;
import br.com.bb.controle.arh.util.Constants;

public class ImpactoDao extends GenericDao<Impacto> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String INSERT_IMPACTO_TAREFA = "insert into " + Constants.database.SCHEMA + ".Tarefa_has_impacto (impacto_id, Tarefa_id, acao) values (:impactoId, :tarefaId, :acao)";
	private static final String FIND_IMPACTO = "select i.* from " + Constants.database.SCHEMA + ".impacto i where 1 = 1 ";
	private static String DELETE_TAREFA_HAS_IMPACTO = "delete from " + Constants.database.SCHEMA + ".Tarefa_has_impacto where impacto_id = :impactoId ";

	public ImpactoDao() {
		super(Impacto.class);
	}

	public void vincularTarefasImpacto(Integer idImpacto, Integer idTarefa, Integer acao) {
		String finalInsert = INSERT_IMPACTO_TAREFA.replace(":impactoId", String.valueOf(idImpacto)).replace(":tarefaId", String.valueOf(idTarefa)).replace(":acao", String.valueOf(acao));

		super.executeNativeSql(finalInsert);
	}

	public List<Impacto> findByFilter(Impacto impacto) {
		StringBuilder sb = new StringBuilder(FIND_IMPACTO);

		if (impacto != null) {
			if (impacto.getDescricao() != null && !impacto.getDescricao().trim().equals("")) {
				sb.append(" and i.descricao like '%" + impacto.getDescricao() + "%' ");
			}
			if (!impacto.getTarefas().isEmpty()) {
				sb.append(" and i.id in (select ti.impacto_id from " + Constants.database.SCHEMA + ".Tarefa_has_impacto ti where ti.Tarefa_id in ( ");
				int i = 1;
				for (Tarefa f : impacto.getTarefas()) {
					sb.append("'" + f.getId() + "'");
					if (i != impacto.getTarefas().size()) {
						sb.append(",");
						i++;
					}
				}
				sb.append(" ) )");
			}
		}
		return super.findListResultNativeQuery(sb.toString(), Impacto.class);
	}

	public void removeAllImpactosFromTarefa(Impacto impacto) {
		String finalDelete = DELETE_TAREFA_HAS_IMPACTO.replace(":impactoId", String.valueOf(impacto.getId()));

		super.executeNativeSql(finalDelete);
	}

}

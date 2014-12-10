package br.com.bb.controle.arh.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bb.controle.arh.model.Tarefa;
import br.com.bb.controle.arh.util.Constants;

public class TarefaDao extends GenericDao<Tarefa> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String INSERT_FUNCIONARIO_HAS_TAREFA = "insert into " + Constants.database.SCHEMA + ".Funcionario_has_Tarefa (Funcionario_chave, Tarefa_id) values (':chaveFuncionario', :id)";
	private static final String FIND_TAREFA_NUMERO_ACAO = "select d from Tarefa d where d.numero = :numero and d.acao = :acao";
	private static final String FIND_TAREFA_NUMERO = "select d from Tarefa d where d.numero = :numero";
	private static final String FIND_TAREFA_NUMERO_HAS_ACAO = "select d from Tarefa d where d.numero = :numero and d.acao != null";
	private static final String FIND_TAREFA = "select t from Tarefa t where 1 = 1 ";

	public TarefaDao() {
		super(Tarefa.class);
	}

	public void insertFuncionarioTarefa(Integer id, String chave) {
		String finalInsert = INSERT_FUNCIONARIO_HAS_TAREFA.replace(":chaveFuncionario", chave).replace(":id", String.valueOf(id));

		super.executeNativeSql(finalInsert);
	}

	public Tarefa findByNumeroEAcao(Long numero, Integer acao) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numero", numero);
		parameters.put("acao", acao);

		return super.findOneResult(FIND_TAREFA_NUMERO_ACAO, parameters);
	}

	public List<Tarefa> findByNumero(Long numero) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numero", numero);

		return super.findListResult(FIND_TAREFA_NUMERO, parameters, 0);
	}

	public List<Tarefa> findByNumeroHasAcao(Long numero) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numero", numero);

		return super.findListResult(FIND_TAREFA_NUMERO_HAS_ACAO, parameters, 0);
	}

	public List<Tarefa> findByFilter(Tarefa tarefa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder(FIND_TAREFA);

		if (tarefa != null) {
			if (tarefa.getNumero() != null && tarefa.getNumero().compareTo(0l) != 0) {
				sb.append(" and t.numero = :numero ");
				parameters.put("numero", tarefa.getNumero());
			}
			if (tarefa.getAcao() != null && tarefa.getAcao().compareTo(0) != 0) {
				sb.append(" and t.acao = :acao ");
				parameters.put("acao", tarefa.getAcao());
			}
			if (tarefa.getEsforco() != null && tarefa.getEsforco().compareTo(0) != 0) {
				sb.append(" and t.esforco = :esforco ");
				parameters.put("esforco", tarefa.getEsforco());
			}
			if (tarefa.getDescricao() != null && !tarefa.getDescricao().trim().equals("")) {
				sb.append(" and t.descricao like '%" + tarefa.getDescricao() + "%' ");
			}
		}

		return super.findListResult(sb.toString(), parameters, 0);
	}

}

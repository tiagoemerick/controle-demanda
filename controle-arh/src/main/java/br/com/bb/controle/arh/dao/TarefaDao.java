package br.com.bb.controle.arh.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Tarefa;
import br.com.bb.controle.arh.util.Constants;

public class TarefaDao extends GenericDao<Tarefa> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static String INSERT_FUNCIONARIO_HAS_TAREFA = "insert into " + Constants.database.SCHEMA + ".Funcionario_has_Tarefa (Funcionario_chave, Tarefa_id) values (':chaveFuncionario', :id)";
	private static String DELETE_FUNCIONARIO_HAS_TAREFA = "delete from " + Constants.database.SCHEMA + ".Funcionario_has_Tarefa where Tarefa_id = :tarefaId ";
	private static String DELETE_TAREFA_HAS_IMPACTO = "delete from " + Constants.database.SCHEMA + ".Tarefa_has_impacto where Tarefa_id = :tarefaId ";
	private static String FIND_TAREFA_NUMERO_ACAO = "select d from Tarefa d where d.numero = :numero and d.acao = :acao";
	private static String FIND_TAREFA_NUMERO = "select d from Tarefa d where d.numero = :numero";
	private static String FIND_TAREFA_NUMERO_HAS_ACAO = "select d from Tarefa d where d.numero = :numero and d.acao != null";
	private static String FIND_TAREFA_COMPONENT = "select t from Tarefa t where 1 = 1 ";
	private static String FIND_TAREFA = "select t.* from " + Constants.database.SCHEMA + ".Tarefa t where 1 = 1 ";
	private static String FIND_TAREFAS_BY_IMPACTO = "select t.* from " + Constants.database.SCHEMA + ".Tarefa t inner join " + Constants.database.SCHEMA + ".Tarefa_has_impacto ti on ti.Tarefa_id = t.id where ti.impacto_id = :impactoId ";

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

	public List<Tarefa> findByFilterComponent(Tarefa tarefa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder(FIND_TAREFA_COMPONENT);

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

	public List<Tarefa> findByFilter(Tarefa tarefa) {
		List<Tarefa> tarefas = null;
		SimpleDateFormat sfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sfDate = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sb = new StringBuilder(FIND_TAREFA);

		if (tarefa != null) {
			if (tarefa.getNumero() != null && tarefa.getNumero().compareTo(0l) != 0) {
				sb.append(" and t.numero = " + tarefa.getNumero());
			}
			if (tarefa.getAcao() != null && tarefa.getAcao().compareTo(0) != 0) {
				sb.append(" and t.acao = " + tarefa.getAcao());
			}
			if (tarefa.getEsforco() != null && tarefa.getEsforco().compareTo(0) != 0) {
				sb.append(" and t.esforco = " + tarefa.getEsforco());
			}
			if (tarefa.getDescricao() != null && !tarefa.getDescricao().trim().equals("")) {
				sb.append(" and t.descricao like '%" + tarefa.getDescricao() + "%' ");
			}
			if (tarefa.getDtIniPlan() != null) {
				sb.append(" and t.dt_ini_plan >= '" + sfDateTime.format(tarefa.getDtIniPlan()) + "'");
			}
			if (tarefa.getDtFimPlan() != null) {
				sb.append(" and t.dt_fim_plan <= '" + sfDateTime.format(tarefa.getDtFimPlan()) + "'");
			}
			if (tarefa.getDtIniRealizado() != null) {
				sb.append(" and t.dt_ini_realizado >= '" + sfDate.format(tarefa.getDtIniRealizado()) + "'");
			}
			if (tarefa.getDtFimRealizado() != null) {
				sb.append(" and t.dt_fim_realizado <= '" + sfDate.format(tarefa.getDtFimRealizado()) + "'");
			}
			if (!tarefa.getFuncionarios().isEmpty()) {
				sb.append(" and t.id in (select ft.Tarefa_id from " + Constants.database.SCHEMA + ".Funcionario_has_Tarefa ft where ft.Funcionario_chave in ( ");
				int i = 1;
				for (Funcionario f : tarefa.getFuncionarios()) {
					sb.append("'" + f.getChave() + "'");
					if (i != tarefa.getFuncionarios().size()) {
						sb.append(",");
						i++;
					}
				}
				sb.append(" ) )");
			}
		}

		List<Object> result = super.findListResultNativeQuery(sb.toString());
		tarefas = convertObjectIntoTarefa(result);

		return tarefas;
	}

	private List<Tarefa> convertObjectIntoTarefa(List<Object> result) {
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		if (result != null && !result.isEmpty()) {
			for (Object ob : result) {
				Object[] obArray = (Object[]) ob;

				Tarefa t = new Tarefa();
				t.setId(Integer.valueOf(obArray[0].toString()));
				if (obArray[1] != null) {
					t.setNumero(Long.valueOf(obArray[1].toString()));
				}
				if (obArray[2] != null) {
					t.setAcao(Integer.valueOf(obArray[2].toString()));
				}
				if (obArray[3] != null) {
					t.setEsforco(Integer.valueOf(obArray[3].toString()));
				}
				if (obArray[4] != null) {
					t.setDescricao(obArray[4].toString());
				}
				if (obArray[5] != null) {
					t.setDtIniPlan(new Date(((Timestamp) obArray[5]).getTime()));
				}
				if (obArray[6] != null) {
					t.setDtFimPlan(new Date(((Timestamp) obArray[6]).getTime()));
				}
				tarefas.add(t);
			}
		}
		return tarefas;
	}

	public void removeAllFuncionariosFromTarefa(Tarefa tarefa) {
		String finalDelete = DELETE_FUNCIONARIO_HAS_TAREFA.replace(":tarefaId", String.valueOf(tarefa.getId()));

		super.executeNativeSql(finalDelete);
	}

	public void removeAllImpactosFromTarefa(Tarefa tarefa) {
		String finalDelete = DELETE_TAREFA_HAS_IMPACTO.replace(":tarefaId", String.valueOf(tarefa.getId()));

		super.executeNativeSql(finalDelete);
	}

	public List<Tarefa> findTarefasByImpactoId(Integer impactoId) {
		String sb = new String(FIND_TAREFAS_BY_IMPACTO);
		return findListResultNativeQuery(sb.replace(":impactoId", String.valueOf(impactoId)), Tarefa.class);
	}

}

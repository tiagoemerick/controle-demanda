package br.com.bb.controle.arh.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.StatusEnum;
import br.com.bb.controle.arh.util.Constants;

public class FuncionarioDao extends GenericDao<Funcionario> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static String FIND_FUNCIONARIO_BY_CHAVE_SENHA = "select f from Funcionario f where f.chave = :chave and f.hash = :hash";
	private static String FIND_ALL_FUNCIONARIOS_BY_STATUS = "select f from Funcionario f where f.isAtivo = :status";
	private static String FIND_FUNCIONARIOS_ATIVO = "select f from Funcionario f where f.isAtivo = :ativo ";
	private static String FIND_FUNCIONARIOS_BY_TAREFA = "select f.* from " + Constants.database.SCHEMA + ".Funcionario f inner join " + Constants.database.SCHEMA + ".Funcionario_has_Tarefa ft on ft.Funcionario_chave = f.chave where ft.Tarefa_id = :tarefaId ";
	private static String FIND_FUNCIONARIOS_BY_META = "select f.* from " + Constants.database.SCHEMA + ".Funcionario f inner join " + Constants.database.SCHEMA + ".Funcionario_has_Meta ft on ft.Funcionario_chave = f.chave where ft.Meta_id = :metaId ";
	private static String FIND_FUNCIONARIOS_META_ATENDIDA_BY_META = "select fm.atendido from " + Constants.database.SCHEMA + ".Funcionario_has_Meta fm where fm.Meta_id = :metaId and fm.Funcionario_chave = :chave";
	private static String FIND_FUNCIONARIOS_BY_INVENTARIO = "select f.* from " + Constants.database.SCHEMA + ".Funcionario f inner join " + Constants.database.SCHEMA + ".Funcionario_has_Inventario ft on ft.Funcionario_chave = f.chave where ft.Inventario_id = :inventarioId ";

	public FuncionarioDao() {
		super(Funcionario.class);
	}

	public Funcionario doLogin(Funcionario funcionario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("chave", funcionario.getChave());
		parameters.put("hash", funcionario.getHash());

		return super.findOneResult(FIND_FUNCIONARIO_BY_CHAVE_SENHA, parameters);
	}

	public List<Funcionario> findAllByStatus(StatusEnum statusEnum) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("status", statusEnum.getStatus());

		return super.findListResult(FIND_ALL_FUNCIONARIOS_BY_STATUS, parameters, 0);
	}

	public List<Funcionario> findByFilter(Funcionario funcionario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder(FIND_FUNCIONARIOS_ATIVO);

		if (funcionario != null) {
			if (funcionario.getChave() != null && !funcionario.getChave().trim().equals("")) {
				sb.append(" and f.chave like '%" + funcionario.getChave() + "%' ");
			}
			if (funcionario.getNome() != null && !funcionario.getNome().trim().equals("")) {
				sb.append(" and f.nome like '%" + funcionario.getNome() + "%' ");
			}
			if (funcionario.getEquipe() != null && funcionario.getEquipe().compareTo(0) != 0) {
				sb.append(" and f.equipe = :equipe ");
				parameters.put("equipe", funcionario.getEquipe());
			}
		}
		parameters.put("ativo", Boolean.TRUE);

		return super.findListResult(sb.toString(), parameters, 0);
	}

	public List<Funcionario> findFuncionariosByTarefaId(Integer tarefaId) {
		String sb = new String(FIND_FUNCIONARIOS_BY_TAREFA);
		return findListResultNativeQuery(sb.replace(":tarefaId", String.valueOf(tarefaId)), Funcionario.class);
	}

	public List<Funcionario> findFuncionariosByMetaId(Integer metaId) {
		String sb = new String(FIND_FUNCIONARIOS_BY_META);
		List<Funcionario> funcionarios = findListResultNativeQuery(sb.replace(":metaId", String.valueOf(metaId)), Funcionario.class);

		if (funcionarios != null && !funcionarios.isEmpty()) {
			sb = new String(FIND_FUNCIONARIOS_META_ATENDIDA_BY_META);
			List<Object> result = findListResultNativeQuery(sb.replace(":metaId", String.valueOf(metaId)).replace(":chave", "'" + funcionarios.get(0).getChave()) + "'");

			if (result != null && !result.isEmpty()) {
				for (Object ob : result) {
					Boolean atendido = Boolean.valueOf(ob.toString());
					funcionarios.get(0).setMetaAtendida(atendido);
					break;
				}
			}
		}
		return funcionarios;
	}

	public List<Funcionario> findFuncionariosByInventarioId(Integer inventarioId) {
		String sb = new String(FIND_FUNCIONARIOS_BY_INVENTARIO);
		return findListResultNativeQuery(sb.replace(":inventarioId", String.valueOf(inventarioId)), Funcionario.class);
	}

}

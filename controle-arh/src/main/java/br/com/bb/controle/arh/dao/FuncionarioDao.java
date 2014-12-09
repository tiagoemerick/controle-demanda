package br.com.bb.controle.arh.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.StatusEnum;

public class FuncionarioDao extends GenericDao<Funcionario> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String FIND_FUNCIONARIO_BY_CHAVE_SENHA = "select f from Funcionario f where f.chave = :chave and f.hash = :hash";
	private static final String FIND_ALL_FUNCIONARIOS_BY_STATUS = "select f from Funcionario f where f.isAtivo = :status";

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

}

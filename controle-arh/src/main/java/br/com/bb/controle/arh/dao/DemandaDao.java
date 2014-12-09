package br.com.bb.controle.arh.dao;

import java.io.Serializable;

import br.com.bb.controle.arh.model.Demanda;
import br.com.bb.controle.arh.util.Constants;

public class DemandaDao extends GenericDao<Demanda> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String INSERT_FUNCIONARIO_HAS_DEMANDA = "insert into " + Constants.database.SCHEMA + ".Funcionario_has_Demanda (Funcionario_chave, Demanda_numero) values (':chaveFuncionario', :numeroDemanda)";

	public DemandaDao() {
		super(Demanda.class);
	}

	public void insertFuncionarioDemanda(Long numero, String chave) {
		String finalInsert = INSERT_FUNCIONARIO_HAS_DEMANDA.replace(":chaveFuncionario", chave).replace(":numeroDemanda", String.valueOf(numero));

		super.executeNativeSql(finalInsert);
	}

}

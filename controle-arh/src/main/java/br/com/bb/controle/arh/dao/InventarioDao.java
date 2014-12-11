package br.com.bb.controle.arh.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.bb.controle.arh.model.Inventario;
import br.com.bb.controle.arh.util.Constants;

public class InventarioDao extends GenericDao<Inventario> implements Serializable {

	private static String INSERT_FUNCIONARIO_HAS_INVENTARIO = "insert into " + Constants.database.SCHEMA + ".Funcionario_has_Inventario (Funcionario_chave, Inventario_id) values (':chave', ':inventarioId')";
	private static String FIND_BY_NUM_BEM = "select i from Inventario i where i.numBem = :numBem";

	private static final long serialVersionUID = 1L;

	public InventarioDao() {
		super(Inventario.class);
	}

	public void insertFuncionarioInventario(String chave, Integer inventarioId) {
		String finalInsert = INSERT_FUNCIONARIO_HAS_INVENTARIO.replace(":chave", chave).replace(":inventarioId", String.valueOf(inventarioId));

		super.executeNativeSql(finalInsert);
	}

	public Inventario findByNumeroBem(String numBem) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numBem", numBem);

		return super.findOneResult(FIND_BY_NUM_BEM, parameters);
	}

}

package br.com.bb.controle.arh.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Inventario;
import br.com.bb.controle.arh.util.Constants;

public class InventarioDao extends GenericDao<Inventario> implements Serializable {

	private static String INSERT_FUNCIONARIO_HAS_INVENTARIO = "insert into " + Constants.database.SCHEMA + ".Funcionario_has_Inventario (Funcionario_chave, Inventario_id) values (':chave', ':inventarioId')";
	private static String FIND_BY_NUM_BEM = "select i from Inventario i where i.numBem = :numBem";
	private static String FIND_INVENTARIO = "select i.* from " + Constants.database.SCHEMA + ".Inventario i where 1 = 1 ";
	private static String DELETE_FUNCIONARIO_HAS_INVENTARIO = "delete from " + Constants.database.SCHEMA + ".Funcionario_has_Inventario where Inventario_id = :inventarioId ";

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

	public List<Inventario> findByFilter(Inventario inventario) {
		StringBuilder sb = new StringBuilder(FIND_INVENTARIO);

		if (inventario != null) {
			if (inventario.getNumBem() != null && !inventario.getNumBem().trim().equals("")) {
				sb.append(" and i.num_bem like '%" + inventario.getNumBem() + "%' ");
			}
			if (inventario.getDescricao() != null && !inventario.getDescricao().trim().equals("")) {
				sb.append(" and i.descricao like '%" + inventario.getDescricao() + "%' ");
			}
			if (!inventario.getFuncionarios().isEmpty()) {
				sb.append(" and i.id in (select fi.Inventario_id from " + Constants.database.SCHEMA + ".Funcionario_has_Inventario fi where fi.Funcionario_chave in ( ");
				int i = 1;
				for (Funcionario f : inventario.getFuncionarios()) {
					sb.append("'" + f.getChave() + "'");
					if (i != inventario.getFuncionarios().size()) {
						sb.append(",");
						i++;
					}
				}
				sb.append(" ) )");
			}
		}
		return super.findListResultNativeQuery(sb.toString(), Inventario.class);
	}

	public void removeAllInventarioFromFuncionario(Inventario inventario) {
		String finalDelete = DELETE_FUNCIONARIO_HAS_INVENTARIO.replace(":inventarioId", String.valueOf(inventario.getId()));

		super.executeNativeSql(finalDelete);
	}

}

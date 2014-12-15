package br.com.bb.controle.arh.dao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Meta;
import br.com.bb.controle.arh.util.Constants;

public class MetaDao extends GenericDao<Meta> implements Serializable {

	private static String INSERT_FUNCIONARIO_HAS_META = "insert into " + Constants.database.SCHEMA + ".Funcionario_has_Meta (Funcionario_chave, Meta_id, atendido) values (':chave', :metaId, :atendido)";
	private static String FIND_META = "select m.* from " + Constants.database.SCHEMA + ".Meta m where 1 = 1 ";
	private static String DELETE_FUNCIONARIO_HAS_META = "delete from " + Constants.database.SCHEMA + ".Funcionario_has_Meta where Meta_id = :metaId ";

	private static final long serialVersionUID = 1L;

	public MetaDao() {
		super(Meta.class);
	}

	public void insertFuncionarioMeta(String chave, Integer metaId, Boolean atendido) {
		String finalInsert = INSERT_FUNCIONARIO_HAS_META.replace(":chave", chave).replace(":metaId", String.valueOf(metaId)).replace(":atendido", String.valueOf(atendido));

		super.executeNativeSql(finalInsert);
	}

	public List<Meta> findByFilter(Meta meta, List<Funcionario> funcionariosSelecionados) {
		SimpleDateFormat sfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder sb = new StringBuilder(FIND_META);

		if (meta != null) {
			if (meta.getDescricao() != null && !meta.getDescricao().trim().equals("")) {
				sb.append(" and m.descricao like '%" + meta.getDescricao() + "%' ");
			}
			if (meta.getDtLimiteIniPesquisa() != null) {
				sb.append(" and m.dt_limite >= '" + sfDateTime.format(meta.getDtLimiteIniPesquisa()) + "'");
			}
			if (meta.getDtLimiteFimPesquisa() != null) {
				sb.append(" and m.dt_limite <= '" + sfDateTime.format(meta.getDtLimiteFimPesquisa()) + "'");
			}
		}
		if (funcionariosSelecionados != null && !funcionariosSelecionados.isEmpty()) {
			sb.append(" and m.id in (select fm.Meta_id from " + Constants.database.SCHEMA + ".Funcionario_has_Meta fm where fm.Funcionario_chave in ( ");
			int i = 1;
			for (Funcionario f : funcionariosSelecionados) {
				sb.append("'" + f.getChave() + "'");
				if (i != funcionariosSelecionados.size()) {
					sb.append(",");
					i++;
				}
			}
			sb.append(" ) )");
		}
		return super.findListResultNativeQuery(sb.toString(), Meta.class);
	}

	public void removeAllMetasFromFuncionario(Meta meta) {
		String finalDelete = DELETE_FUNCIONARIO_HAS_META.replace(":metaId", String.valueOf(meta.getId()));

		super.executeNativeSql(finalDelete);
	}

}

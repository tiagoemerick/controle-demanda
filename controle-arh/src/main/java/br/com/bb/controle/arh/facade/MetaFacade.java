package br.com.bb.controle.arh.facade;

import java.util.List;

import javax.inject.Inject;

import br.com.bb.controle.arh.dao.MetaDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Meta;
import br.com.bb.controle.arh.util.AbstractUtil;

public class MetaFacade extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	@Inject
	private MetaDao metaDao;

	@Transactional(roolBack = true)
	public void cadastrar(Meta meta, List<Funcionario> funcionariosSelecionados) throws Exception {
		try {
			metaDao.saveAndFlush(meta);
			vincularFuncionarioMeta(meta, funcionariosSelecionados);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar meta. Tente novamente.", e);
		}
	}

	private void vincularFuncionarioMeta(Meta meta, List<Funcionario> funcionariosSelecionados) {
		if (funcionariosSelecionados != null) {
			for (Funcionario funcionario : funcionariosSelecionados) {
				this.metaDao.insertFuncionarioMeta(funcionario.getChave(), meta.getId(), Boolean.FALSE);
			}
		}
	}

}

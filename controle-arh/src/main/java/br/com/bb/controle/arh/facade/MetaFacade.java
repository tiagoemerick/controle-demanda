package br.com.bb.controle.arh.facade;

import java.util.ArrayList;
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
				this.metaDao.insertFuncionarioMeta(funcionario.getChave(), meta.getId(), meta.getAtendido());
			}
		}
	}

	public List<Meta> buscarPorCriterios(Meta meta, List<Funcionario> funcionariosSelecionados) throws Exception {
		List<Meta> metas = new ArrayList<Meta>();
		try {
			metas = metaDao.findByFilter(meta, funcionariosSelecionados);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao pesquisar metas por critérios. Tente novamente.", e);
		}
		return metas;
	}

	@Transactional(roolBack = true)
	public void atualizar(Meta meta, List<Funcionario> funcionariosSelecionados) throws Exception {
		try {
			metaDao.update(meta);
			atualizarFuncionarioMeta(meta, funcionariosSelecionados);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao atualizar meta. Tente novamente.", e);
		}
	}

	private void atualizarFuncionarioMeta(Meta meta, List<Funcionario> funcionariosSelecionados) {
		removerFuncionarioMeta(meta);
		vincularFuncionarioMeta(meta, funcionariosSelecionados);
	}

	private void removerFuncionarioMeta(Meta meta) {
		this.metaDao.removeAllMetasFromFuncionario(meta);
	}

	@Transactional(roolBack = true)
	public void excluir(Meta meta) throws Exception {
		try {
			removerFuncionarioMeta(meta);
			metaDao.delete(meta.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao excluir meta. Tente novamente.", e);
		}
	}

}

package br.com.bb.controle.arh.facade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.bb.controle.arh.dao.ImpactoDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Impacto;
import br.com.bb.controle.arh.util.AbstractUtil;

public class ImpactoFacade extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	@Inject
	private ImpactoDao impactoDao;

	@Transactional(roolBack = true)
	public void cadastrar(Impacto impacto) throws Exception {
		try {
			impactoDao.saveAndFlush(impacto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar impacto. Tente novamente.", e);
		}
	}

	public List<Impacto> buscarPorCriterios(Impacto impacto) throws Exception {
		List<Impacto> impactos = new ArrayList<Impacto>();
		try {
			impactos = impactoDao.findByFilter(impacto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao pesquisar impactos por critérios. Tente novamente.", e);
		}
		return impactos;
	}

	@Transactional(roolBack = true)
	public void excluir(Impacto impacto) throws Exception {
		try {
			removerTarefaImpacto(impacto);
			impactoDao.delete(impacto.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao excluir impacto. Tente novamente.", e);
		}
	}

	private void removerTarefaImpacto(Impacto impacto) {
		this.impactoDao.removeAllImpactosFromTarefa(impacto);
	}

	@Transactional(roolBack = true)
	public void atualizar(Impacto impacto) throws Exception {
		try {
			atualizarTarefaImpacto(impacto);
			impactoDao.update(impacto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao atualizar impacto. Tente novamente.", e);
		}
	}

	private void atualizarTarefaImpacto(Impacto impacto) {
		removerTarefaImpacto(impacto);
	}

}

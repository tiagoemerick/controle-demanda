package br.com.bb.controle.arh.facade;

import java.util.List;

import javax.inject.Inject;

import br.com.bb.controle.arh.dao.ImpactoDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Tarefa;
import br.com.bb.controle.arh.model.Impacto;
import br.com.bb.controle.arh.util.AbstractUtil;

public class ImpactoFacade extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	@Inject
	private ImpactoDao impactoDao;

	@Transactional(roolBack = true)
	public void cadastrar(Impacto impacto, List<Tarefa> tarefasSelecionadas) throws Exception {
		try {
			impactoDao.saveAndFlush(impacto);
			vincularTarefas(impacto, tarefasSelecionadas);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar impacto. Tente novamente.", e);
		}
	}

	private void vincularTarefas(Impacto impacto, List<Tarefa> tarefasSelecionadas) {
		if (tarefasSelecionadas != null) {
			for (Tarefa dem : tarefasSelecionadas) {
				impactoDao.vincularTarefasImpacto(impacto.getId(), dem.getId(), dem.getAcao());
			}
		}
	}

}

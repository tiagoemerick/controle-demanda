package br.com.bb.controle.arh.facade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.bb.controle.arh.dao.TarefaDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Tarefa;
import br.com.bb.controle.arh.util.AbstractUtil;

public class TarefaFacade extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	@Inject
	private TarefaDao tarefaDao;

	@Transactional(roolBack = true)
	public void cadastrar(Tarefa tarefa) throws Exception {
		validarCadastro(tarefa);
		try {
			tarefaDao.saveAndFlush(tarefa);
			insertFuncionarioTarefa(tarefa);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar tarefa. Tente novamente.", e);
		}
	}

	public List<Tarefa> buscarPorNumeroTemAcao(Tarefa tarefa) throws Exception {
		List<Tarefa> tafefas = new ArrayList<Tarefa>();
		try {
			tafefas = tarefaDao.findByNumeroHasAcao(tarefa.getNumero());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao pesquisar tarefa. Tente novamente.", e);
		}
		return tafefas;
	}

	private void insertFuncionarioTarefa(Tarefa tarefa) {
		for (Funcionario f : tarefa.getFuncionarios()) {
			this.tarefaDao.insertFuncionarioTarefa(tarefa.getId(), f.getChave());
		}
	}

	/**
	 * Não é permitido ter acao repitida para a tarefa
	 * 
	 * @param tarefa
	 * @throws Exception
	 */
	private void validarAcaoTarefa(Tarefa tarefa) throws Exception {
		if (tarefa.getNumero() != null && tarefa.getNumero().compareTo(0l) != 0) {
			if (tarefa.getAcao() != null && tarefa.getAcao().compareTo(0) != 0) {
				Tarefa demAux = tarefaDao.findByNumeroEAcao(tarefa.getNumero(), tarefa.getAcao());
				if (demAux != null) {
					throw new Exception("Já existe uma tarefa com esta demanda e esta ação.");
				}
			}
		}
	}

	private void validarCadastro(Tarefa tarefa) throws Exception {
		if (tarefa == null) {
			throw new IllegalArgumentException("Tarefa inválida. Tente novamente.");
		}
		if (tarefa.getDtIni() != null && tarefa.getDtFim() != null) {
			if (tarefa.getDtIni().after(tarefa.getDtFim())) {
				throw new Exception("A data final não pode ser anterior a data final.");
			}
		}
		validarAcaoTarefa(tarefa);
	}

	public List<Tarefa> buscarPorCriterios(Tarefa tarefa) throws Exception {
		List<Tarefa> tafefas = new ArrayList<Tarefa>();
		try {
			tafefas = tarefaDao.findByFilter(tarefa);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao pesquisar tarefas por critérios. Tente novamente.", e);
		}
		return tafefas;
	}

}
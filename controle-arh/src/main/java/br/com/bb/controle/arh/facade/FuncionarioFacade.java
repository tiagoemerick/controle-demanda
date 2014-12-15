package br.com.bb.controle.arh.facade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import br.com.bb.controle.arh.dao.FuncionarioDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.StatusEnum;
import br.com.bb.controle.arh.model.Tarefa;
import br.com.bb.controle.arh.util.AbstractUtil;
import br.com.bb.controle.arh.util.CryptUtil;

public class FuncionarioFacade extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioDao funcionarioDao;

	public boolean login(Funcionario funcionario) {
		validateLogin(funcionario);

		funcionario.setHash(CryptUtil.crypt(funcionario.getHash()));
		funcionario = funcionarioDao.doLogin(funcionario);
		if (funcionario != null) {
			setFuncionarioLogadoSessao(funcionario);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public List<Funcionario> findAllAtivos() {
		return findAllByStatus(StatusEnum.ATIVO);
	}

	private List<Funcionario> findAllByStatus(StatusEnum statusEnum) {
		List<Funcionario> funcionarios = funcionarioDao.findAllByStatus(statusEnum);
		return funcionarios;
	}

	public List<Funcionario> findAll() {
		List<Funcionario> funcionarios = funcionarioDao.findAll();
		return funcionarios;
	}

	@Transactional(roolBack = true)
	public void cadastrar(Funcionario funcionario) throws Exception {
		validarCadastro(funcionario);

		funcionario.setHash(CryptUtil.crypt(Funcionario.SENHA_DEFAULT));
		funcionario.setChave(funcionario.getChave().toUpperCase());
		funcionario.setTrocarSenha(Boolean.TRUE);
		funcionario.setIsAtivo(Boolean.TRUE);
		try {
			funcionarioDao.save(funcionario);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar funcionário. Tente novamente.", e);
		}
	}

	@Transactional(roolBack = true)
	public void resetarSenha(Funcionario funcionario) throws Exception {
		Funcionario funcAux = funcionarioDao.find(funcionario.getChave());

		funcAux.setHash(CryptUtil.crypt(Funcionario.SENHA_DEFAULT));
		funcAux.setTrocarSenha(Boolean.TRUE);
		try {
			funcionarioDao.save(funcAux);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao redefinir senha. Tente novamente.", e);
		}
	}

	@Transactional(roolBack = true)
	public void inativar(Funcionario funcionario) throws Exception {
		Funcionario funcAux = funcionarioDao.find(funcionario.getChave());

		funcAux.setIsAtivo(Boolean.FALSE);
		try {
			funcionarioDao.save(funcAux);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao inativar funcionário. Tente novamente.", e);
		}
	}

	private void validarCadastro(Funcionario funcionario) throws Exception {
		try {
			Funcionario funcAux = funcionarioDao.findReferenceOnly(funcionario.getChave());
			if (funcAux != null && funcAux.getChave() != null && !funcAux.getChave().trim().equals("")) {
				throw new Exception("Funcionário com a chave " + funcAux.getChave() + " já existe.");
			}
		} catch (EntityNotFoundException e) {
			System.out.println("Cadastro pode continuar. Chave não existe.");
		}
	}

	@Transactional(roolBack = true)
	public void alterarSenha(Funcionario funcionario) throws Exception {
		String chaveLogged = getFuncionarioLogado().getChave();

		Funcionario funcAux = funcionarioDao.find(chaveLogged);

		funcAux.setHash(CryptUtil.crypt(funcionario.getHash()));
		funcAux.setTrocarSenha(Boolean.FALSE);
		try {
			funcionarioDao.update(funcAux);
			setFuncionarioLogadoSessao(funcAux);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar funcionário. Tente novamente.", e);
		}
	}

	private void validateLogin(Funcionario funcionario) {
		if (funcionario == null || (funcionario.getChave() == null || funcionario.getChave().trim().equals("")) || (funcionario.getHash() == null || funcionario.getHash().trim().equals(""))) {
			throw new IllegalArgumentException("Funcionário inválido. Tente novamente.");
		}
	}

	public List<Funcionario> buscarPorCriterios(Funcionario funcionario) throws Exception {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		try {
			funcionarios = funcionarioDao.findByFilter(funcionario);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao pesquisar funcionários por critérios. Tente novamente.", e);
		}
		return funcionarios;
	}

	@Transactional(roolBack = true)
	public void reativar(Funcionario funcionario) throws Exception {
		Funcionario funcAux = funcionarioDao.find(funcionario.getChave());

		funcAux.setIsAtivo(Boolean.TRUE);
		try {
			funcionarioDao.save(funcAux);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao reativar funcionário. Tente novamente.", e);
		}
	}

	@Transactional(roolBack = true)
	public void atualizar(Funcionario funcionario) throws Exception {
		if (funcionario.getNovaSenha() != null && !funcionario.getNovaSenha().trim().equals("")) {
			if (!funcionario.getNovaSenha().equals(funcionario.getHash())) {
				funcionario.setHash(CryptUtil.crypt(funcionario.getNovaSenha()));
			}
		}
		try {
			funcionarioDao.update(funcionario);

			if (funcionario.getChave().equals(getFuncionarioLogado().getChave())) {
				setFuncionarioLogadoSessao(funcionario);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao atualizar funcionário. Tente novamente.", e);
		}
	}

	public List<Funcionario> buscarFuncionariosPorTarefa(Tarefa tarefa) throws Exception {
		if (tarefa == null || tarefa.getId() == null || tarefa.getId().compareTo(0) == 0) {
			throw new IllegalArgumentException("Tarefa inválida.");
		}
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		try {
			funcionarios = funcionarioDao.findFuncionariosByTarefaId(tarefa.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao buscar funcionários por tarefa. Tente novamente.", e);
		}
		return funcionarios;
	}

}

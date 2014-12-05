package br.com.bb.controle.arh.facade;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import br.com.bb.controle.arh.dao.FuncionarioDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.util.AbstractUtil;
import br.com.bb.controle.arh.util.Constants;
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
			putObjectInSession(Constants.session.FUNCIONARIO_LOGADO, funcionario);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public List<Funcionario> findAll() {
		List<Funcionario> funcionarios = funcionarioDao.findAll();
		return funcionarios;
	}

	@Transactional(roolBack = true)
	public void cadastrar(Funcionario funcionario) throws Exception {
		validarCadastro(funcionario);

		funcionario.setHash(CryptUtil.crypt(Funcionario.SENHA_DEFAULT));
		funcionario.setTrocarSenha(Boolean.TRUE);
		try {
			funcionarioDao.save(funcionario);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar funcionário. Tente novamente.", e);
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
		String chaveLogged = ((Funcionario) getObjectInSession(Constants.session.FUNCIONARIO_LOGADO)).getChave();

		Funcionario funcAux = funcionarioDao.find(chaveLogged);

		funcAux.setHash(CryptUtil.crypt(funcionario.getHash()));
		funcAux.setTrocarSenha(Boolean.FALSE);
		try {
			funcionarioDao.update(funcAux);
			putObjectInSession(Constants.session.FUNCIONARIO_LOGADO, funcAux);
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

}

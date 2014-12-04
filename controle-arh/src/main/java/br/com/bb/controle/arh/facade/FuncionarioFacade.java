package br.com.bb.controle.arh.facade;

import javax.inject.Inject;

import br.com.bb.controle.arh.dao.FuncionarioDao;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.util.AbstractUtil;
import br.com.bb.controle.arh.util.Constants;
import br.com.bb.controle.arh.util.CryptUtil;

public class FuncionarioFacade extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioDao funcionarioDao;

	public boolean login(Funcionario funcionario) {
		validate(funcionario);

		funcionario.setHash(CryptUtil.crypt(funcionario.getHash()));
		funcionario = funcionarioDao.doLogin(funcionario);
		if (funcionario != null) {
			putObjectInSession(Constants.session.FUNCIONARIO_LOGADO, funcionario);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private void validate(Funcionario funcionario) {
		if (funcionario == null || (funcionario.getChave() == null || funcionario.getChave().trim().equals("")) || (funcionario.getHash() == null || funcionario.getHash().trim().equals(""))) {
			throw new IllegalArgumentException("Funcionário inválido. Tente novamente.");
		}
	}

}

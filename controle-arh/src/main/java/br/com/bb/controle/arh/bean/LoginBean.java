package br.com.bb.controle.arh.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.FuncionarioFacade;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.util.Constants;

@Named
@RequestScoped
public class LoginBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	private Funcionario funcionario;

	@PostConstruct
	public void init() {
		this.funcionario = new Funcionario();
	}

	public String login() {
		try {
			boolean isLogado = funcionarioFacade.login(funcionario);
			if (isLogado) {
				Funcionario logado = (Funcionario) getObjectInSession(Constants.session.FUNCIONARIO_LOGADO);
				StringBuilder sb = new StringBuilder("Bem vindo, ");
				sb.append(logado.getNome());
				sb.append(" (");
				sb.append(logado.getChave());
				sb.append(")");
				displayInfoMessageToUser(sb.toString());

				return Constants.pages.HOME;
			} else {
				displayInfoMessageToUser("Usuário não encontrado. Chave e/ou senha inválidas.");
			}
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String logout() {
		invalidateSession();
		return Constants.pages.LOGIN + "?faces-redirect=true";
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

}

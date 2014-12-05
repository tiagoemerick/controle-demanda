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
public class FuncionarioBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	private Funcionario funcionario;

	@PostConstruct
	public void init() {
		this.funcionario = new Funcionario();
	}

	public String cadastrar() {
		try {
			funcionarioFacade.cadastrar(funcionario);
			displayInfoMessageToUser("Funcionário cadastrado com sucesso!");
			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String alterarSenha() {
		try {
			funcionarioFacade.alterarSenha(funcionario);
			displayInfoMessageToUser("Senha alterada com sucesso!");

			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String cancelar() {
		return Constants.pages.HOME + "?faces-redirect=true";
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

}

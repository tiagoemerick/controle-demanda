package br.com.bb.controle.arh.bean;

import java.util.ArrayList;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.FuncionarioFacade;
import br.com.bb.controle.arh.infra.DataModel;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.util.Constants;

@Named
@ConversationScoped
public class FuncionarioBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	@Inject
	private DataModel<Funcionario> dataModel;

	@Inject
	private Funcionario funcionario;

	public String init() {
		super.beginNewConversation();

		this.funcionario = new Funcionario();

		return Constants.funcionarioPages.CADASTRAR_FUNCIONARIO;
	}

	public String initVisualizarPerfilLogado() {
		super.beginNewConversation();

		this.funcionario = getFuncionarioLogado();

		return Constants.funcionarioPages.VISUALIZAR_PERFIL;
	}

	public String initVisualizarPerfil(Funcionario funcionario) {
		super.beginNewConversation();

		this.funcionario = funcionario;

		return Constants.funcionarioPages.VISUALIZAR_PERFIL;
	}

	public String initPesquisar() {
		super.beginNewConversation();

		this.dataModel.setList(new ArrayList<Funcionario>());
		pesquisar();

		return Constants.funcionarioPages.PESQUISAR_FUNCIONARIO;
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

	public String atualizar() {
		try {
			funcionarioFacade.atualizar(funcionario);
			displayInfoMessageToUser("Funcionário atualizado com sucesso!");
			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void pesquisar() {
		dataModel.setList(funcionarioFacade.findAll());
	}

	public void resetarSenha(Funcionario funcionario) {
		try {
			funcionarioFacade.resetarSenha(funcionario);
			displayInfoMessageToUser("Senha redefinida com sucesso!");
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
	}

	public void reativar(Funcionario funcionario) {
		try {
			funcionarioFacade.reativar(funcionario);
			displayInfoMessageToUser("Funcionário reativado com sucesso!");
			funcionario.setIsAtivo(Boolean.TRUE);
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
	}

	public void inativar(Funcionario funcionario) {
		try {
			funcionarioFacade.inativar(funcionario);
			displayInfoMessageToUser("Funcionário invativado com sucesso!");
			funcionario.setIsAtivo(Boolean.FALSE);
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
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

	public DataModel<Funcionario> getDataModel() {
		return dataModel;
	}

}
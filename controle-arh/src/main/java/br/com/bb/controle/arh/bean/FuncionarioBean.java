package br.com.bb.controle.arh.bean;

import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.bb.controle.arh.facade.FuncionarioFacade;
import br.com.bb.controle.arh.infra.DataModel;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.util.Constants;

@Named
@RequestScoped
public class FuncionarioBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	@New
	@Inject
	private DataModel<Funcionario> dataModel;

	@Inject
	private Funcionario funcionarioSelecionado;

	private Funcionario funcionario;

	public String init() {
		this.funcionario = new Funcionario();

		return Constants.funcionarioPages.CADASTRAR_FUNCIONARIO;
	}

	public String initPesquisar() {
		this.dataModel.setList(new ArrayList<Funcionario>());

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

	public String teste() {
		dataModel.setList(funcionarioFacade.findAll());
		return null;
	}

	public void selecionarFuncionario(SelectEvent event) {
		funcionarioSelecionado = (Funcionario) event.getObject();
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

	public Funcionario getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

}
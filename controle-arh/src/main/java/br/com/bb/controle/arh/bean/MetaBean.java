package br.com.bb.controle.arh.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.FuncionarioFacade;
import br.com.bb.controle.arh.infra.DataModel;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Meta;
import br.com.bb.controle.arh.util.Constants;

@Named
@ConversationScoped
public class MetaBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	@Inject
	private Meta meta;

	@Inject
	private Funcionario funcionario;

	@Inject
	private DataModel<Funcionario> funcionariosBusca;

	private List<Funcionario> funcionariosSelecionados;

	public String init() {
		super.beginNewConversation();

		this.meta = new Meta();
		this.funcionario = new Funcionario();
		this.funcionariosBusca = new DataModel<Funcionario>();
		this.funcionariosBusca.setList(new ArrayList<Funcionario>());
		this.funcionariosSelecionados = new ArrayList<Funcionario>();

		return Constants.metaPages.CADASTRAR_META;
	}

	public String cadastrar() {
		try {
			if (!funcionariosSelecionados.isEmpty()) {
			}
			// tarefaFacade.cadastrar(tarefa);
			displayInfoMessageToUser("Meta cadastrada com sucesso!");
			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void pesquisarFuncionarios() {
		if (validarFiltro()) {
			try {
				List<Funcionario> funcionarios = funcionarioFacade.buscarPorCriterios(funcionario);
				this.funcionariosBusca.setList(funcionarios != null ? funcionarios : new ArrayList<Funcionario>());
			} catch (Exception e) {
				displayErrorMessageToUser(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void vincularFuncionarios(Funcionario funcionario) {
		if (!funcionariosSelecionados.contains(funcionario)) {
			funcionariosSelecionados.add(funcionario);
		} else {
			displayErrorMessageToUser("Funcionário já vinculado");
		}
	}

	public void removerFuncionario(Funcionario funcionario) {
		funcionariosSelecionados.remove(funcionario);
	}

	private boolean validarFiltro() {
		if (funcionario != null) {
			if ((funcionario.getChave() == null || funcionario.getChave().trim().equals("")) && (funcionario.getNome() == null || funcionario.getNome().trim().equals(""))) {
				displayErrorMessageToUser("Pelo menos um filtro deve ser preenchido para a pesquisa de funcionários.");
				return false;
			}
		}
		return true;
	}

	public String cancelar() {
		return Constants.pages.HOME + "?faces-redirect=true";
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public DataModel<Funcionario> getFuncionariosBusca() {
		return funcionariosBusca;
	}

	public void setFuncionariosBusca(DataModel<Funcionario> funcionariosBusca) {
		this.funcionariosBusca = funcionariosBusca;
	}

	public List<Funcionario> getFuncionariosSelecionados() {
		return funcionariosSelecionados;
	}

	public void setFuncionariosSelecionados(List<Funcionario> funcionariosSelecionados) {
		this.funcionariosSelecionados = funcionariosSelecionados;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
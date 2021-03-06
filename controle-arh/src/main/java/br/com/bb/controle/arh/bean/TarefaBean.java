package br.com.bb.controle.arh.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.FuncionarioFacade;
import br.com.bb.controle.arh.facade.TarefaFacade;
import br.com.bb.controle.arh.infra.DataModel;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Tarefa;
import br.com.bb.controle.arh.util.Constants;

@Named
@ConversationScoped
public class TarefaBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private TarefaFacade tarefaFacade;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	@Inject
	private Tarefa tarefa;

	@Inject
	private Funcionario funcionario;

	@Inject
	private DataModel<Funcionario> funcionariosBusca;

	@Inject
	private DataModel<Tarefa> tarefasBusca;

	private List<Funcionario> funcionariosSelecionados;

	private String numeroAcaoPesquisaTopo;

	public String init() {
		super.beginNewConversation();

		this.tarefa = new Tarefa();
		this.funcionario = new Funcionario();
		this.funcionariosBusca = new DataModel<Funcionario>();
		this.funcionariosBusca.setList(new ArrayList<Funcionario>());
		this.funcionariosSelecionados = new ArrayList<Funcionario>();

		return Constants.tarefaPages.CADASTRAR_TAREFA;
	}

	public String initEditar(Tarefa tarefa) {
		this.tarefa = tarefa;

		this.funcionario = new Funcionario();
		this.funcionariosBusca = new DataModel<Funcionario>();
		this.funcionariosBusca.setList(new ArrayList<Funcionario>());
		this.numeroAcaoPesquisaTopo = new String();

		this.funcionariosSelecionados = buscarFuncionariosTarefa();

		return Constants.tarefaPages.CADASTRAR_TAREFA;
	}

	private List<Funcionario> buscarFuncionariosTarefa() {
		List<Funcionario> funcionariosTarefa = new ArrayList<Funcionario>();
		if (this.tarefa != null && this.tarefa.getId() != null) {
			try {
				funcionariosTarefa = this.funcionarioFacade.buscarFuncionariosPorTarefa(this.tarefa);
			} catch (Exception e) {
				displayErrorMessageToUser(e.getMessage());
				e.printStackTrace();
			}
		}
		return funcionariosTarefa;
	}

	public String initPesquisar() {
		this.beginNewConversation();

		this.tarefa = new Tarefa();
		this.funcionario = new Funcionario();
		this.funcionariosBusca = new DataModel<Funcionario>();
		this.funcionariosBusca.setList(new ArrayList<Funcionario>());
		this.funcionariosSelecionados = new ArrayList<Funcionario>();
		this.tarefasBusca = new DataModel<Tarefa>();
		this.tarefasBusca.setList(new ArrayList<Tarefa>());

		return Constants.tarefaPages.PESQUISAR_TAREFA;
	}

	public String cadastrar() {
		try {
			if (!funcionariosSelecionados.isEmpty()) {
				tarefa.setFuncionarios(funcionariosSelecionados);
			}
			tarefaFacade.cadastrar(tarefa);
			displayInfoMessageToUser("Tarefa cadastrada com sucesso!");
			return Constants.pages.HOME + "?faces-redirect=true";
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public String atualizar() {
		try {
			tarefa.setFuncionarios(funcionariosSelecionados);
			tarefaFacade.atualizar(tarefa);
			displayInfoMessageToUser("Tarefa atualizada com sucesso!");
			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void excluir(Tarefa tarefa) {
		try {
			Tarefa tAux = new Tarefa();
			tAux.setId(tarefa.getId());

			tarefaFacade.excluir(tarefa);

			tarefasBusca.removeFromList(tAux);
			displayInfoMessageToUser("Tarefa excluída com sucesso!");
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
	}

	public void pesquisar() {
		if (validarPesquisa()) {
			try {
				if (!funcionariosSelecionados.isEmpty()) {
					this.tarefa.setFuncionarios(funcionariosSelecionados);
				}
				List<Tarefa> tarefas = tarefaFacade.buscarPorCriterios(tarefa);
				if (tarefas != null) {
					this.tarefasBusca.setList(tarefas);
				}
			} catch (Exception e) {
				displayErrorMessageToUser(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public String pesquisarTopo() {
		try {
			Tarefa tAux = new Tarefa();
			tAux.setAcao(Integer.valueOf(numeroAcaoPesquisaTopo));
			Tarefa tarAcao = tarefaFacade.buscarPorAcao(tAux);
			if (tarAcao != null && tarAcao.getId() != null) {
				super.beginNewConversation();
				return initEditar(tarAcao);
			}
			displayInfoMessageToUser("Nenhuma tarefa encontrada por este número de ação.");
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private boolean validarPesquisa() {
		boolean valido = false;
		String msgErrov = "Pelo menos um filtro deve ser preenchido para a pesquisa de tarefas.";
		if (tarefa != null) {
			if (tarefa.getNumero() != null && tarefa.getNumero().compareTo(0l) != 0) {
				valido = true;
			}
			if (tarefa.getAcao() != null && tarefa.getAcao().compareTo(0) != 0) {
				valido = true;
			}
			if (tarefa.getDescricao() != null && !tarefa.getDescricao().trim().equals("")) {
				valido = true;
			}
			if (!funcionariosSelecionados.isEmpty()) {
				valido = true;
			}
			if (tarefa.getDtIniPlan() != null || tarefa.getDtFimPlan() != null) {
				valido = true;
			}
			if (tarefa.getDtIniRealizado() != null || tarefa.getDtFimRealizado() != null) {
				valido = true;
			}
		}
		if (!valido) {
			displayErrorMessageToUser(msgErrov);
		}
		return valido;
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

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
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

	public DataModel<Tarefa> getTarefasBusca() {
		return tarefasBusca;
	}

	public void setTarefasBusca(DataModel<Tarefa> tarefasBusca) {
		this.tarefasBusca = tarefasBusca;
	}

	public String getNumeroAcaoPesquisaTopo() {
		return numeroAcaoPesquisaTopo;
	}

	public void setNumeroAcaoPesquisaTopo(String numeroAcaoPesquisaTopo) {
		this.numeroAcaoPesquisaTopo = numeroAcaoPesquisaTopo;
	}

}
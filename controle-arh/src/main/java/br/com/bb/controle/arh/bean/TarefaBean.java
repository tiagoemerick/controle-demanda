package br.com.bb.controle.arh.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.bb.controle.arh.facade.FuncionarioFacade;
import br.com.bb.controle.arh.facade.TarefaFacade;
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

	private DualListModel<Funcionario> funcionarios;
	private List<Funcionario> funcionariosSelecionados;

	public String init() {
		super.beginNewConversation();

		this.tarefa = new Tarefa();
		this.funcionariosSelecionados = new ArrayList<Funcionario>();

		this.funcionarios = new DualListModel<Funcionario>(this.funcionarioFacade.findAllAtivos(), funcionariosSelecionados);

		return Constants.tarefaPages.CADASTRAR_TAREFA;
	}

	public String cadastrar() {
		try {
			if (!funcionariosSelecionados.isEmpty()) {
				tarefa.setFuncionarios(funcionariosSelecionados);
			}
			tarefaFacade.cadastrar(tarefa);
			displayInfoMessageToUser("Tarefa cadastrada com sucesso!");
			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void onTransfer(TransferEvent event) {
		for (Object item : event.getItems()) {
			Funcionario f = (Funcionario) item;
			if (!funcionariosSelecionados.contains(f)) {
				funcionariosSelecionados.add(f);
			} else {
				funcionariosSelecionados.remove(f);
			}
		}
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

	public DualListModel<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(DualListModel<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

}
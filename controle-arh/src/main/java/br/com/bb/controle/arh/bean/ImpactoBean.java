package br.com.bb.controle.arh.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.ImpactoFacade;
import br.com.bb.controle.arh.facade.TarefaFacade;
import br.com.bb.controle.arh.infra.DataModel;
import br.com.bb.controle.arh.model.Impacto;
import br.com.bb.controle.arh.model.Tarefa;
import br.com.bb.controle.arh.util.Constants;

@Named
@ConversationScoped
public class ImpactoBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private TarefaFacade tarefaFacade;

	@Inject
	private ImpactoFacade impactoFacade;

	@Inject
	private Impacto impacto;

	@Inject
	private Tarefa tarefa;

	@Inject
	private DataModel<Tarefa> tarefasBusca;

	private List<Tarefa> tarefasSelecionadas;

	public String init() {
		super.beginNewConversation();

		this.impacto = new Impacto();
		this.tarefa = new Tarefa();
		this.tarefasBusca = new DataModel<Tarefa>();
		this.tarefasBusca.setList(new ArrayList<Tarefa>());
		this.tarefasSelecionadas = new ArrayList<Tarefa>();

		return Constants.impactoPages.CADASTRAR_IMPACTO;
	}

	public String cadastrar() {
		try {
			if (!tarefasSelecionadas.isEmpty()) {
				impacto.setTarefas(tarefasSelecionadas);
			}
			impactoFacade.cadastrar(impacto);
			displayInfoMessageToUser("Impacto cadastrado com sucesso!");
			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void pesquisarTarefas() {
		if (validarFiltro()) {
			try {
				List<Tarefa> tarefas = tarefaFacade.buscarPorCriteriosComponente(tarefa);
				this.tarefasBusca.setList(tarefas != null ? tarefas : new ArrayList<Tarefa>());
			} catch (Exception e) {
				displayErrorMessageToUser(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void vincularTarefas(Tarefa tarefa) {
		if (!tarefasSelecionadas.contains(tarefa)) {
			tarefasSelecionadas.add(tarefa);
		} else {
			displayErrorMessageToUser("Tarefa já vinculada");
		}
	}

	public void removerTarefa(Tarefa tarefa) {
		tarefasSelecionadas.remove(tarefa);
	}

	private boolean validarFiltro() {
		if (tarefa != null) {
			if ((tarefa.getNumero() == null || tarefa.getNumero().compareTo(0l) == 0) && (tarefa.getAcao() == null || tarefa.getAcao().compareTo(0) == 0) && (tarefa.getDescricao() == null || tarefa.getDescricao().trim().equals(""))) {
				displayErrorMessageToUser("Pelo menos um filtro deve ser preenchido para a pesquisa de tarefas.");
				return false;
			}
		}
		return true;
	}

	public String cancelar() {
		return Constants.pages.HOME + "?faces-redirect=true";
	}

	public Impacto getImpacto() {
		return impacto;
	}

	public void setImpacto(Impacto impacto) {
		this.impacto = impacto;
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public DataModel<Tarefa> getTarefasBusca() {
		return tarefasBusca;
	}

	public void setTarefasBusca(DataModel<Tarefa> tarefasBusca) {
		this.tarefasBusca = tarefasBusca;
	}

	public List<Tarefa> getTarefasSelecionadas() {
		return tarefasSelecionadas;
	}

	public void setTarefasSelecionadas(List<Tarefa> tarefasSelecionadas) {
		this.tarefasSelecionadas = tarefasSelecionadas;
	}

}
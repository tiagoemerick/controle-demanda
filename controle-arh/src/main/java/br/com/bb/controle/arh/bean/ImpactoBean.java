package br.com.bb.controle.arh.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.ImpactoFacade;
import br.com.bb.controle.arh.facade.TarefaFacade;
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

	private List<Tarefa> tarefasSelecionadas;
	private List<Tarefa> tarefasDuplicadasAcao;

	public String init() {
		super.beginNewConversation();

		this.impacto = new Impacto();
		this.tarefa = new Tarefa();
		this.tarefasSelecionadas = new ArrayList<Tarefa>();
		this.tarefasDuplicadasAcao = new ArrayList<Tarefa>();

		return Constants.impactoPages.CADASTRAR_IMPACTO;
	}

	public String cadastrar() {
		try {
			impactoFacade.cadastrar(impacto, tarefasSelecionadas);
			displayInfoMessageToUser("Impacto cadastrado com sucesso!");
			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void removerTarefa(Tarefa tarefa) {
		tarefasSelecionadas.remove(tarefa);
	}

	public void incluirTarefaAcao(Tarefa tarefa) {
		if (!tarefasSelecionadas.contains(tarefa)) {
			tarefasSelecionadas.add(tarefa);
		} else {
			tarefasSelecionadas.remove(tarefa);
		}
	}

	public void incluirTarefa() {
		this.tarefasDuplicadasAcao = new ArrayList<Tarefa>();
		if (tarefa.getNumero() != null && tarefa.getNumero().compareTo(0l) != 0) {
			try {
				List<Tarefa> tarefas = tarefaFacade.buscarPorNumeroTemAcao(tarefa);
				if (tarefas != null && !tarefas.isEmpty()) {
					if (tarefas.size() > 1) {
						tarefasDuplicadasAcao.addAll(tarefas);
					} else {
						if (!tarefasSelecionadas.contains(tarefas.get(0))) {
							tarefasSelecionadas.add(tarefas.get(0));
							tarefa = new Tarefa();
						} else {
							displayErrorMessageToUser("Tarefa já incluída.");
						}
					}
				} else {
					displayErrorMessageToUser("Nenhuma tarefa encontrada com este número ou a tarefa não possui ações.");
				}
			} catch (Exception e) {
				displayErrorMessageToUser(e.getMessage());
				e.printStackTrace();
			}
		} else {
			displayErrorMessageToUser("Número da tarefa obrigatório.");
		}
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

	public List<Tarefa> getTarefasSelecionadas() {
		return tarefasSelecionadas;
	}

	public void setTarefasSelecionadas(List<Tarefa> tarefasSelecionadas) {
		this.tarefasSelecionadas = tarefasSelecionadas;
	}

	public List<Tarefa> getTarefasDuplicadasAcao() {
		return tarefasDuplicadasAcao;
	}

	public void setTarefasDuplicadasAcao(List<Tarefa> tarefasDuplicadasAcao) {
		this.tarefasDuplicadasAcao = tarefasDuplicadasAcao;
	}

}
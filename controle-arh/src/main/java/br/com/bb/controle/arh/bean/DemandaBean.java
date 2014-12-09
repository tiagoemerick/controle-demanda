package br.com.bb.controle.arh.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.bb.controle.arh.facade.DemandaFacade;
import br.com.bb.controle.arh.facade.FuncionarioFacade;
import br.com.bb.controle.arh.model.Demanda;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.util.Constants;

@Named
@ConversationScoped
public class DemandaBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private DemandaFacade demandaFacade;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	@Inject
	private Demanda demanda;

	private DualListModel<Funcionario> funcionarios;
	private List<Funcionario> funcionariosSelecionados;

	public String init() {
		super.beginNewConversation();

		this.demanda = new Demanda();
		this.funcionariosSelecionados = new ArrayList<Funcionario>();

		this.funcionarios = new DualListModel<Funcionario>(this.funcionarioFacade.findAllAtivos(), funcionariosSelecionados);

		return Constants.demandaPages.CADASTRAR_DEMANDA;
	}

	public String cadastrar() {
		try {
			if (!funcionariosSelecionados.isEmpty()) {
				demanda.setFuncionarios(funcionariosSelecionados);
			}
			demandaFacade.cadastrar(demanda);
			displayInfoMessageToUser("Demanda cadastrada com sucesso!");
			return Constants.demandaPages.DETALHE_DEMANDA;
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

	public Demanda getDemanda() {
		return demanda;
	}

	public void setDemanda(Demanda demanda) {
		this.demanda = demanda;
	}

	public DualListModel<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(DualListModel<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

}
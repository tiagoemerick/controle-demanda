package br.com.bb.controle.arh.bean;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.DemandaFacade;
import br.com.bb.controle.arh.model.Demanda;
import br.com.bb.controle.arh.util.Constants;

@Named
@ConversationScoped
public class DemandaBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private DemandaFacade demandaFacade;

	@Inject
	private Demanda demanda;

	public String init() {
		super.beginNewConversation();
		
		this.demanda = new Demanda();

		return Constants.demandaPages.CADASTRAR_DEMANDA;
	}

	public String cadastrar() {
		try {
			demandaFacade.cadastrar(demanda);
			displayInfoMessageToUser("Demanda cadastrada com sucesso!");
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

	public Demanda getDemanda() {
		return demanda;
	}

	public void setDemanda(Demanda demanda) {
		this.demanda = demanda;
	}

}
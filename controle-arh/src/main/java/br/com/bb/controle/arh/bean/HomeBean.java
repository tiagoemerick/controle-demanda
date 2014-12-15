package br.com.bb.controle.arh.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.TarefaFacade;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Tarefa;
import br.com.bb.controle.arh.util.Constants;

@Named
@RequestScoped
public class HomeBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private TarefaFacade tarefaFacade;

	public String calcularDemandasPendentes() {
		String qtdeDemandasPendentes = (String) getObjectFromSession(Constants.session.QTDE_DEMANDAS_PENDENTES);
		if (qtdeDemandasPendentes == null || qtdeDemandasPendentes.trim().equals("")) {
			Funcionario fLogado = getFuncionarioLogado();
			List<Tarefa> tarefasPendentes;
			try {
				tarefasPendentes = tarefaFacade.pesquisarTarefasPendentesFuncionario(fLogado);
				if (tarefasPendentes != null) {
					qtdeDemandasPendentes = String.valueOf(tarefasPendentes.size());
				}
			} catch (Exception e) {
				qtdeDemandasPendentes = null;
				displayErrorMessageToUser(e.getMessage());
				e.printStackTrace();
			}
			if (qtdeDemandasPendentes != null) {
				putObjectInSession(Constants.session.QTDE_DEMANDAS_PENDENTES, qtdeDemandasPendentes);
			}
		}
		return String.valueOf(qtdeDemandasPendentes != null ? qtdeDemandasPendentes : "0");
	}

}
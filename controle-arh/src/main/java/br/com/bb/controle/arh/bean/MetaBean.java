package br.com.bb.controle.arh.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.FuncionarioFacade;
import br.com.bb.controle.arh.facade.MetaFacade;
import br.com.bb.controle.arh.infra.DataModel;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Meta;
import br.com.bb.controle.arh.util.Constants;

@Named
@ConversationScoped
public class MetaBean extends AbstractBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private MetaFacade metaFacade;

	@Inject
	private FuncionarioFacade funcionarioFacade;

	@Inject
	private Meta meta;

	@Inject
	private Funcionario funcionario;

	@Inject
	private DataModel<Funcionario> funcionariosBusca;

	@Inject
	private DataModel<Meta> metasBusca;

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

	public String initEditar(Meta meta) {
		this.meta = meta;

		this.funcionario = new Funcionario();
		this.funcionariosBusca = new DataModel<Funcionario>();
		this.funcionariosBusca.setList(new ArrayList<Funcionario>());

		this.funcionariosSelecionados = buscarFuncionariosMeta();

		return Constants.metaPages.CADASTRAR_META;
	}

	private List<Funcionario> buscarFuncionariosMeta() {
		List<Funcionario> funcionariosMeta = new ArrayList<Funcionario>();
		if (this.meta != null && this.meta.getId() != null) {
			try {
				funcionariosMeta = this.funcionarioFacade.buscarFuncionariosPorMeta(this.meta);
				if (funcionariosMeta != null && !funcionariosMeta.isEmpty()) {
					this.meta.setAtendido(funcionariosMeta.get(0).getMetaAtendida());
				}
			} catch (Exception e) {
				displayErrorMessageToUser(e.getMessage());
				e.printStackTrace();
			}
		}
		return funcionariosMeta;
	}

	public String initPesquisar() {
		super.beginNewConversation();

		this.meta = new Meta();
		this.funcionario = new Funcionario();
		this.funcionariosBusca = new DataModel<Funcionario>();
		this.funcionariosBusca.setList(new ArrayList<Funcionario>());
		this.funcionariosSelecionados = new ArrayList<Funcionario>();
		this.metasBusca = new DataModel<Meta>();
		this.metasBusca.setList(new ArrayList<Meta>());

		return Constants.metaPages.PESQUISAR_META;
	}

	public String cadastrar() {
		try {
			metaFacade.cadastrar(meta, funcionariosSelecionados);
			displayInfoMessageToUser("Meta cadastrada com sucesso!");
			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void excluir(Meta meta) {
		try {
			Meta mAux = new Meta();
			mAux.setId(meta.getId());

			metaFacade.excluir(meta);

			metasBusca.removeFromList(mAux);
			displayInfoMessageToUser("Meta excluída com sucesso!");
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
	}

	public String atualizar() {
		try {
			metaFacade.atualizar(this.meta, funcionariosSelecionados);
			displayInfoMessageToUser("Meta atualizada com sucesso!");
			return Constants.pages.HOME;
		} catch (Exception e) {
			displayErrorMessageToUser(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public void pesquisar() {
		if (validarPesquisa()) {
			try {
				List<Meta> metas = metaFacade.buscarPorCriterios(meta, funcionariosSelecionados);
				if (metas != null) {
					this.metasBusca.setList(metas);
				}
			} catch (Exception e) {
				displayErrorMessageToUser(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private boolean validarPesquisa() {
		boolean valido = false;
		String msgErrov = "Pelo menos um filtro deve ser preenchido para a pesquisa de metas.";
		if (meta != null) {
			if (meta.getDescricao() != null && !meta.getDescricao().trim().equals("")) {
				valido = true;
			}
			if (!funcionariosSelecionados.isEmpty()) {
				valido = true;
			}
			if (meta.getDtLimiteIniPesquisa() != null || meta.getDtLimiteFimPesquisa() != null) {
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

	public DataModel<Meta> getMetasBusca() {
		return metasBusca;
	}

	public void setMetasBusca(DataModel<Meta> metasBusca) {
		this.metasBusca = metasBusca;
	}

}
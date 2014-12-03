package br.com.bb.controle.arh.bean;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bb.controle.arh.facade.DemandaFacade;

@Named
@RequestScoped
public class FuncionarioBean {

	private String teste;

	@Inject
	private DemandaFacade demandaFacade;

	public void gogo() {
		System.out.println("teste");
		demandaFacade.saveDemanda();
	}

	public void find() {
		demandaFacade.lista();
	}

	public String getTeste() {
		setTeste("foi la");
		return teste;
	}

	public void setTeste(String teste) {
		this.teste = teste;
	}

}

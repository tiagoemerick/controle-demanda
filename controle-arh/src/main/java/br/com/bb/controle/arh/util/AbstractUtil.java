package br.com.bb.controle.arh.util;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.bb.controle.arh.model.Funcionario;

public class AbstractUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public AbstractUtil() {
		super();
	}

	public void putObjectInSession(String name, Object object) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().setAttribute(name, object);
	}

	public Object getObjectInSession(String name) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getSession().getAttribute(name);
	}

	/**
	 * Retorna o funcionario logado
	 * 
	 * @return funcionario logado ou null
	 */
	public Funcionario getFuncionarioLogado() {
		Funcionario f = (Funcionario) getObjectInSession(Constants.session.FUNCIONARIO_LOGADO);
		return f;
	}

	public void setFuncionarioLogadoSessao(Funcionario funcionario) {
		putObjectInSession(Constants.session.FUNCIONARIO_LOGADO, funcionario);
	}

	public void invalidateSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().invalidate();
	}

}

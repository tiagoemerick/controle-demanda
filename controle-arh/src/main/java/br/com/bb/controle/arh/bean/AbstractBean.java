package br.com.bb.controle.arh.bean;

import javax.enterprise.context.Conversation;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import br.com.bb.controle.arh.util.AbstractUtil;
import br.com.bb.controle.arh.util.JSFMessageUtil;

public class AbstractBean extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	private static final String KEEP_DIALOG_OPENED = "KEEP_DIALOG_OPENED";

	public AbstractBean() {
		super();
	}
	
	private final int MINUTOS20 = 1200000;

	@Inject
	private Conversation conversation;
	
	protected void beginNewConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
		conversation.begin();
		conversation.setTimeout(MINUTOS20);
	}

	/**
	 * Se houver uma conversation ativa, usa a mesa, caso contrario cria nova.
	 */
	protected void joinConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
		} else {
			conversation.begin(conversation.getId());
		}
		conversation.setTimeout(MINUTOS20);
	}

	protected void endConversation() {
		conversation.end();
	}

	protected void displayErrorMessageToUser(String message) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendErrorMessageToUser(message);
	}

	protected void displayInfoMessageToUser(String message) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendInfoMessageToUser(message);
	}

	protected void closeDialog() {
		getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, false);
	}

	protected void keepDialogOpen() {
		getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, true);
	}

	protected RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}

}

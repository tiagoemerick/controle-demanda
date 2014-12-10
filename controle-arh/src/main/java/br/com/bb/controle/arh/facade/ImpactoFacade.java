package br.com.bb.controle.arh.facade;

import javax.inject.Inject;

import br.com.bb.controle.arh.dao.ImpactoDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Impacto;
import br.com.bb.controle.arh.util.AbstractUtil;

public class ImpactoFacade extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	@Inject
	private ImpactoDao impactoDao;

	@Transactional(roolBack = true)
	public void cadastrar(Impacto impacto) throws Exception {
		try {
			impactoDao.saveAndFlush(impacto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar impacto. Tente novamente.", e);
		}
	}

}

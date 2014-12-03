package br.com.bb.controle.arh.facade;

import java.util.Date;

import javax.inject.Inject;

import br.com.bb.controle.arh.dao.DemandaDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Demanda;

public class DemandaFacade {

	@Inject
	private DemandaDao demandaDao;

	@Transactional(roolBack = true)
	public void saveDemanda() {
		Demanda demanda = new Demanda();
		demanda.setAcao(1);
		demanda.setDescricao("des");
		demanda.setDtFim(new Date());
		demanda.setDtIni(new Date());
		demanda.setEsforco(1);
		demanda.setNumero(1);
		demanda.setFuncionarios(null);
		demanda.setImpactos(null);

		demandaDao.save(demanda);
	}

	public void lista() {
		Demanda f = demandaDao.findReferenceOnly(1);
		System.out.println(f.getDescricao());
	}
}

package br.com.bb.controle.arh.facade;

import java.util.Calendar;
import java.util.Random;

import javax.inject.Inject;

import br.com.bb.controle.arh.dao.DemandaDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Demanda;
import br.com.bb.controle.arh.util.AbstractUtil;

public class DemandaFacade extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	@Inject
	private DemandaDao demandaDao;

	public void lista() {
		Demanda f = demandaDao.findReferenceOnly(1);
		System.out.println(f.getDescricao());
	}

	@Transactional(roolBack = true)
	public void cadastrar(Demanda demanda) throws Exception {
		validarCadastro(demanda);
		try {
			validarNumeroDemanda(demanda);
			demandaDao.save(demanda);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar demanda. Tente novamente.", e);
		}
	}

	private synchronized void validarNumeroDemanda(Demanda demanda) {
		String numeroDemandaString = null;
		Demanda demAux = null;
		do {
			numeroDemandaString = gerarNumeroDemanda();
			demAux = demandaDao.find(Long.valueOf(numeroDemandaString));
		} while (demAux != null);

		demanda.setNumero(Long.valueOf(numeroDemandaString));
	}

	private synchronized String gerarNumeroDemanda() {
		StringBuffer sb = new StringBuffer();
		Calendar c = Calendar.getInstance();
		Random r = new Random();

		int numero = r.nextInt(100) + 100;
		String s = String.valueOf(c.get(Calendar.YEAR));
		sb.append(s.substring(2, 4));
		sb.append(c.get(Calendar.DAY_OF_MONTH));
		sb.append(c.get(Calendar.DAY_OF_YEAR));
		sb.append(numero);

		return sb.toString();
	}

	private void validarCadastro(Demanda demanda) throws Exception {
		if (demanda == null) {
			throw new IllegalArgumentException("Demanda inválida. Tente novamente.");
		}
		if (demanda.getDtIni() != null && demanda.getDtFim() != null) {
			if (demanda.getDtIni().after(demanda.getDtFim())) {
				throw new Exception("A data final não pode ser anterior a data final.");
			}
		}
	}

}

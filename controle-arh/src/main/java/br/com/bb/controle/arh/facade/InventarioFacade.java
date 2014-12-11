package br.com.bb.controle.arh.facade;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;

import br.com.bb.controle.arh.dao.InventarioDao;
import br.com.bb.controle.arh.infra.interceptors.Transactional;
import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.model.Inventario;
import br.com.bb.controle.arh.util.AbstractUtil;

public class InventarioFacade extends AbstractUtil {

	private static final long serialVersionUID = 1L;

	@Inject
	private InventarioDao inventarioDao;

	@Transactional(roolBack = true)
	public void cadastrar(Inventario inventario, List<Funcionario> funcionariosSelecionados) throws Exception {
		validarCadastro(inventario);
		try {
			inventarioDao.saveAndFlush(inventario);
			vincularFuncionarioInventario(inventario, funcionariosSelecionados);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao salvar meta. Tente novamente.", e);
		}
	}

	private void validarCadastro(Inventario inventario) throws Exception {
		try {
			Inventario invent = inventarioDao.findByNumeroBem(inventario.getNumBem());
			if (invent != null && invent.getNumBem() != null && !invent.getNumBem().trim().equals("")) {
				throw new Exception("Inventário com número de bem: " + invent.getNumBem() + " já existe.");
			}
		} catch (EntityNotFoundException e) {
			System.out.println("Cadastro pode continuar. Inventário não existe.");
		}
	}

	private void vincularFuncionarioInventario(Inventario inventario, List<Funcionario> funcionariosSelecionados) {
		if (funcionariosSelecionados != null) {
			for (Funcionario funcionario : funcionariosSelecionados) {
				this.inventarioDao.insertFuncionarioInventario(funcionario.getChave(), inventario.getId());
			}
		}
	}

}

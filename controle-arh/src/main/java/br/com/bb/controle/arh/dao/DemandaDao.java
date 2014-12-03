package br.com.bb.controle.arh.dao;

import java.io.Serializable;

import br.com.bb.controle.arh.model.Demanda;

public class DemandaDao extends GenericDao<Demanda> implements Serializable {

	private static final long serialVersionUID = 1L;

	public DemandaDao() {
		super(Demanda.class);
	}

}

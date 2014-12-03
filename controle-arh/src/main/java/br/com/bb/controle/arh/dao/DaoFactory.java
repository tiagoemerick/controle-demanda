package br.com.bb.controle.arh.dao;

import java.lang.reflect.ParameterizedType;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bb.controle.arh.model.IEntity;

public class DaoFactory {

	@Inject
	private EntityManager em;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Produces
	public GenericDao<IEntity> getGenericDao(final InjectionPoint injectionPoint) {
		ParameterizedType parameterizedType = (ParameterizedType) injectionPoint.getType();
		Class classe = (Class) parameterizedType.getActualTypeArguments()[0];
		return new GenericDao<IEntity>(classe, em);

	}

}

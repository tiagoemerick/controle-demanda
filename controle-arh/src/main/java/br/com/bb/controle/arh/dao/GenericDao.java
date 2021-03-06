package br.com.bb.controle.arh.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public class GenericDao<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	private Class<T> entityClass;

	public GenericDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public GenericDao(Class<T> entityClass, EntityManager em) {
		this.entityClass = entityClass;
		this.em = em;
	}

	/*
	 * public void beginTransaction() { // em = emf.createEntityManager();
	 * 
	 * em.getTransaction().begin(); }
	 * 
	 * public void commit() { em.getTransaction().commit(); }
	 * 
	 * public void rollback() { em.getTransaction().rollback(); }
	 * 
	 * public void closeTransaction() { em.close(); }
	 * 
	 * public void commitAndCloseTransaction() { commit(); closeTransaction(); }
	 * 
	 * public void flush() { em.flush(); }
	 * 
	 * public void joinTransaction() { // em = emf.createEntityManager();
	 * em.joinTransaction(); }
	 */

	private void flush() {
		em.flush();
	}

	public void save(T entity) {
		em.persist(entity);
	}

	public T saveAndFlush(T entity) {
		em.persist(entity);
		flush();
		return entity;
	}

	public void delete(Object id) {
		T entityToBeRemoved = em.getReference(entityClass, id);
		em.remove(entityToBeRemoved);
	}

	public T update(T entity) {
		return em.merge(entity);
	}

	public T find(Object entityID) {
		return em.find(entityClass, entityID);
	}

	public T find(Class<T> clazz, Object entityID) {
		return em.find(clazz, entityID);
	}

	public T findReferenceOnly(Object entityID) {
		return em.getReference(entityClass, entityID);
	}

	protected void executeNativeSql(String nativeQuery) {
		try {
			Query query = em.createNativeQuery(nativeQuery);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println("Erro no executeNativeSql() \ne.getMessage(): " + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> findAll() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected T findOneResult(String stringQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = em.createQuery(stringQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			System.out.println("Nenhum resultado encontrado para a query: " + stringQuery);
		} catch (Exception e) {
			System.out.println("Erro na query: " + stringQuery + "\ne.getMessage(): " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findListResult(String stringQuery, Map<String, Object> parameters, int maxResult) {
		List<T> result = null;

		try {
			Query query = em.createQuery(stringQuery);

			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			if (maxResult != 0) {
				query.setMaxResults(maxResult);
			}
			result = (List<T>) query.getResultList();

		} catch (NoResultException e) {
			System.out.println("Nenhum resultado encontrado para a lista query: " + stringQuery);
		} catch (Exception e) {
			System.out.println("Erro na lista query: " + stringQuery + "\ne.getMessage(): " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<Object> findListResultNativeQuery(String stringNativeQuery) {
		List<Object> result = null;

		try {
			Query query = em.createNativeQuery(stringNativeQuery);
			result = (List<Object>) query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Nenhum resultado encontrado para a lista native query: " + stringNativeQuery);
		} catch (Exception e) {
			System.out.println("Erro na lista native query: " + stringNativeQuery + "\ne.getMessage(): " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<T> findListResultNativeQuery(String stringNativeQuery, Class<T> resultClass) {
		List<T> result = null;

		try {
			Query query = em.createNativeQuery(stringNativeQuery, resultClass);
			result = (List<T>) query.getResultList();
		} catch (NoResultException e) {
			System.out.println("Nenhum resultado encontrado para a lista native query: " + stringNativeQuery);
		} catch (Exception e) {
			System.out.println("Erro na lista native query: " + stringNativeQuery + "\ne.getMessage(): " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

}

package br.com.bb.controle.arh.infra.interceptors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Interceptor
@Transactional
public class ManageTransaction {

	@Inject
	private EntityManager em;

	@AroundInvoke
	public Object manageTransaction(InvocationContext context) throws Exception {
		try {
			em.getTransaction().begin();

			Object methodResult = context.proceed();
			em.getTransaction().commit();

			return methodResult;
		} catch (Exception ex) {
			Method method = context.getMethod();
			for (Annotation ann : method.getDeclaredAnnotations()) {
				if (ann instanceof Transactional) {
					Transactional tr = (Transactional) ann;
					if (tr.roolBack()) {
						if (em.getTransaction() != null && em.getTransaction().isActive()) {
							em.getTransaction().rollback();
						}
					}
					break;
				}
			}
			throw ex;
		} finally {
			em.close();
		}
	}
}

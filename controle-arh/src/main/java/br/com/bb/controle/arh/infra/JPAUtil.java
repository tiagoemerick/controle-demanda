package br.com.bb.controle.arh.infra;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.bb.controle.arh.util.Constants;

public class JPAUtil {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(Constants.database.PERSISTENCE_UNIT_NAME);

	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public void close(@Disposes EntityManager em) {
		if (em.isOpen()) {
			em.close();
		}
	}

}

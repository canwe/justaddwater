package com.example.justaddwater.model;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.wicket.model.LoadableDetachableModel;

public class EntityManagerLoadableDetachableModel extends LoadableDetachableModel<EntityManager> {

	private static final Logger log = Logger.getLogger(EntityManagerLoadableDetachableModel.class.getName());
	
	@Inject
	EntityManagerFactory emf;
	
	@Inject
	transient EntityManager em;
	
	public EntityManagerLoadableDetachableModel() {
		super();
	}

	@Override
	protected EntityManager load() {
		log.info("EntityManagerLoadableDetachableModel load");
		if (em != null) {
			em.close();
		}
	  return em = emf.createEntityManager();
	}

	@Override
	public void setObject(EntityManager object) {
		throw new UnsupportedOperationException("Model " + getClass() + " does not support setObject(Object)");
	}

	@Override
	public void detach() {
		log.info("EntityManagerLoadableDetachableModel detach");
		if (em != null & em.isOpen()) {
			em.close();
		}
		super.detach();
	}

}

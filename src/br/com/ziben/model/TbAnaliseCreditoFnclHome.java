package br.com.ziben.model;

// Generated Oct 30, 2013 4:54:18 PM by Hibernate Tools 4.0.0

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

/**
 * Home object for domain model class TbAnaliseCreditoFncl.
 * @see br.com.ziben.model.TbAnaliseCreditoFncl
 * @author Hibernate Tools
 */
@Stateless
public class TbAnaliseCreditoFnclHome {

	private static final Logger log = Logger.getLogger(TbAnaliseCreditoFnclHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public TbAnaliseCreditoFnclHome(EntityManager em) {
		entityManager = em;
	}

	public void persist(TbAnaliseCreditoFncl transientInstance) {
		log.debug("persisting TbAnaliseCreditoFncl instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TbAnaliseCreditoFncl persistentInstance) {
		log.debug("removing TbAnaliseCreditoFncl instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TbAnaliseCreditoFncl merge(TbAnaliseCreditoFncl detachedInstance) {
		log.debug("merging TbAnaliseCreditoFncl instance");
		try {
			TbAnaliseCreditoFncl result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TbAnaliseCreditoFncl findById(TbAnaliseCreditoFnclId id) {
		log.debug("getting TbAnaliseCreditoFncl instance with id: " + id);
		try {
			TbAnaliseCreditoFncl instance = entityManager.find(TbAnaliseCreditoFncl.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}

package br.com.ziben.model;

// Generated Oct 30, 2013 4:54:18 PM by Hibernate Tools 4.0.0

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

/**
 * Home object for domain model class TbAnaliseCreditoComplFncl.
 * @see br.com.ziben.model.TbAnaliseCreditoComplFncl
 * @author Hibernate Tools
 */
@Stateless
public class TbAnaliseCreditoComplFnclHome {

	private static final Logger log = Logger.getLogger(TbAnaliseCreditoComplFnclHome.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	public TbAnaliseCreditoComplFnclHome(EntityManager em) {
		entityManager = em;
	}

	public void persist(TbAnaliseCreditoComplFncl transientInstance) {
		log.debug("persisting TbAnaliseCreditoComplFncl instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TbAnaliseCreditoComplFncl persistentInstance) {
		log.debug("removing TbAnaliseCreditoComplFncl instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TbAnaliseCreditoComplFncl merge(TbAnaliseCreditoComplFncl detachedInstance) {
		log.debug("merging TbAnaliseCreditoComplFncl instance");
		try {
			TbAnaliseCreditoComplFncl result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TbAnaliseCreditoComplFncl findById(TbAnaliseCreditoComplFnclId id) {
		log.debug("getting TbAnaliseCreditoComplFncl instance with id: " + id);
		try {
			TbAnaliseCreditoComplFncl instance = entityManager.find(TbAnaliseCreditoComplFncl.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}

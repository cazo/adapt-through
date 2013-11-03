/**
	This file is part of adapt-through.

    Adapt-through is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Adapt-through is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Adapt-through.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.ziben.model;

// Generated Nov 1, 2013 12:51:07 PM by Hibernate Tools 4.0.0

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

/**
 * Home object for domain model class TbEsteiraCredito.
 * 
 * @see br.com.ziben.model.TbEsteiraCredito
 * @author Hibernate Tools
 */
@Stateless
public class TbEsteiraCreditoHome {

	private static final Logger log = Logger.getLogger(TbEsteiraCreditoHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public TbEsteiraCreditoHome(EntityManager em) {
		entityManager = em;
	}

	public void persist(TbEsteiraCredito transientInstance) {
		log.debug("persisting TbEsteiraCredito instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TbEsteiraCredito persistentInstance) {
		log.debug("removing TbEsteiraCredito instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TbEsteiraCredito merge(TbEsteiraCredito detachedInstance) {
		log.debug("merging TbEsteiraCredito instance");
		try {
			TbEsteiraCredito result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TbEsteiraCredito findById(long id) {
		log.debug("getting TbEsteiraCredito instance with id: " + id);
		try {
			TbEsteiraCredito instance = entityManager.find(TbEsteiraCredito.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}

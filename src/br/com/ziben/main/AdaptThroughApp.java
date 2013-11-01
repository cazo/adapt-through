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

package br.com.ziben.main;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import br.com.ziben.model.ConnectionManager;
import br.com.ziben.model.TbAnaliseCreditoFncl;
import br.com.ziben.model.TbAnaliseCreditoFnclHome;
import br.com.ziben.services.client.RetailProposalRegisterClient;

public class AdaptThroughApp {

	private String event;
	private int numThreads;
	private Logger log = Logger.getLogger(this.getClass().getName());

	private boolean execute() {
		boolean result = true;

		numThreads = Integer.parseInt(System.getProperty("numThreads"));

		HashMap<Integer, RetailProposalRegisterClient> executed = new HashMap<Integer, RetailProposalRegisterClient>();

		try {
			// pool de threads
			ExecutorService es = Executors.newFixedThreadPool(numThreads);

			// lista com os futuros das execuções
			List<Future<?>> futures = new ArrayList<Future<?>>();

			// vou no banco buscar as propostas a serem processadas
			ArrayList<TbAnaliseCreditoFncl> proposalList = getProposalsForProcessing();
			markAsBeeingProcessed(proposalList);

			// loop que cria as threads de execução
			for (int i = 1; i <= numThreads; i++) {
				log.debug("Vou instanciar a classe para thread " + i);
				RetailProposalRegisterClient ind = new RetailProposalRegisterClient(event, "" + i);
				log.debug("Vou mandar rodar a thread " + i);
				Future<?> future = es.submit(ind);
				futures.add(future);
				log.debug("Jah mandei rodar a thread " + i);
				executed.put(i, ind);
			}

			// espero todas as threads me enviarem o retorno...
			for (Future<?> f : futures) {
				f.get();
			}

			// desligo a pool de threads e continuo com o resto do aplicativo...
			es.shutdown();

			// hora de pegar os dados de tempo para o log
			log.debug("-------------------------------------------------------------");

			long totalTime = 0;
			int maxTimeThread = 0;
			long maxTime = 0;
			int minTimeThread = 0;
			long minTime = Long.MAX_VALUE;

			for (int i = 1; i <= numThreads; i++) {
				long threadTime = executed.get(i).getTotalTime();
				totalTime += threadTime;
				log.debug("Tempo de execucao da thread " + i + ": " + threadTime + "ms");

				if (threadTime < minTime) {
					minTime = threadTime;
					minTimeThread = i;
				}
				if (threadTime > maxTime) {
					maxTime = threadTime;
					maxTimeThread = i;
				}
			}

			log.debug("-------------------------------------------------------------");
			log.debug("Menor tempo de execucao foi na thread " + minTimeThread + " e levou: " + minTime + "ms");
			log.debug("Maior tempo de execucao foi na thread " + maxTimeThread + " e levou: " + maxTime + "ms");
			log.debug("Tempo medio de execucao: " + totalTime / numThreads + "ms");

		} catch (InterruptedException e) {
			log.error("Erro esperando a execução das threads.", e);
		} catch (ExecutionException e) {
			log.error("Erro esperando a execução das threads.", e);
		} catch (Exception e) {
			log.error("Erro esperando a execução das threads.", e);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<TbAnaliseCreditoFncl> getProposalsForProcessing() {

		log.debug(">> AdaptThroughApp.getProposalsForProcessing()");

		List<TbAnaliseCreditoFncl> list = null;

		try {
			Session session = (Session) ConnectionManager.getEntityManager("database.property.dbzema.name").getDelegate();
			Criteria criteria = session.createCriteria(TbAnaliseCreditoFncl.class);
			criteria.add(Restrictions.isNull("status"));
			criteria.addOrder(Order.asc("dtinclusao"));
			criteria.addOrder(Order.asc("hrinclusao"));
			criteria.setMaxResults(numThreads);
			// faz consulta com os criterios
			list = criteria.list();
		} catch (Exception e) {
			log.error("Erro ao buscar as propostas na base:", e);
		} finally {
			log.debug(">> AdaptThroughApp.getProposalsForProcessing()");
		}

		return new ArrayList<TbAnaliseCreditoFncl>(list);
	}

	private void markAsBeeingProcessed(ArrayList<TbAnaliseCreditoFncl> proposals) {

		log.debug(">> AdaptThroughApp.markAsBeeingProcessed()");

		EntityManager entityManager = null;

		try {
			entityManager = ConnectionManager.getEntityManager("database.property.dbzema.name");
			entityManager.getTransaction().begin();

			for (TbAnaliseCreditoFncl tbAnaliseCreditoFncl : proposals) {
				TbAnaliseCreditoFnclHome tbAnaliseCreditoFnclDao = new TbAnaliseCreditoFnclHome(entityManager);
				tbAnaliseCreditoFncl.setStatus((short) 3);
				DateTime dtAnalise = new DateTime();
				dtAnalise.withTime(0, 0, 0, 0);
				tbAnaliseCreditoFncl.setDtanalise(dtAnalise.toDate());
				DateTime hrAnalise = new DateTime();
				hrAnalise.withDate(1900, 1, 1);
				tbAnaliseCreditoFncl.setHranalise(new Date());
				tbAnaliseCreditoFnclDao.persist(tbAnaliseCreditoFncl);
			}

			entityManager.getTransaction().commit();

		} catch (Exception e) {
			log.error("Erro ao pegar a mensagem: ", e);
		} finally {
			if (entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			log.debug("<< AdaptThroughApp.markAsBeeingProcessed()");
		}
	}

	public static void main(String args[]) {
		int returnCod = 0;
		long startTime = (new Date()).getTime();
		Logger logger = Logger.getLogger(AdaptThroughApp.class);
		try {
			if (System.getProperty("user.properties") != null) {
				Properties props = System.getProperties();

				try {
					props.load(new FileInputStream(System.getProperty("user.properties")));
				} catch (Exception safe) {
					logger.warn("Erro ao carregar as váriaveis de ambiente: ", safe);
				}
				System.setProperties(props);
			}

			logger.info("Inicio do processo...");
			AdaptThroughApp env = new AdaptThroughApp();
			env.event = "AnaliseCredito";
			env.execute();
		} catch (Exception e) {
			logger.error("Erro inesperado no processamento.", e);
		} finally {
			long endTime = (new Date()).getTime();
			logger.info("Processo finalizado em " + (endTime - startTime) + "ms...");
		}
		System.exit(returnCod);
	}

}

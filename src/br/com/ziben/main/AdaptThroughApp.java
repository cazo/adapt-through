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
import java.rmi.registry.LocateRegistry;
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

/**
 * Classe principal do programa, respnsável por buscar os dados a serem processados e chamar as threads de processamento.
 * 
 * @author Frederico Jabulka
 * 
 */
public class AdaptThroughApp {

	private int numThreads;
	private Logger log = Logger.getLogger(this.getClass().getName());

	/**
	 * Configuro variáveis essenciais para execução e inicio o RMI registry usado pelo JNDI para guardar os nomes dos EntityManagers
	 * 
	 * @return o código de saida do programa, qualquer coisa diferente de zero é um erro.
	 */
	private int configure() {
		log.debug(">> AdaptThroughApp.configure()");

		int cod = 0;
		int port = 1099;
		Properties props = System.getProperties();

		try {
			try {
				props.load(new FileInputStream(System.getProperty("user.properties")));
			} catch (Exception e) {
				log.fatal("Erro ao carregar as váriaveis de ambiente. Verifique a propriedade de ambiente \"user.properties\". Stacktrace: ", e);
				cod = 1;
			}
			System.setProperties(props);

			// Pego a porta do RMI registry...
			try {
				port = Integer.valueOf(System.getProperty("rmi.registry.port"));
			} catch (Exception e) {
				port = 1099;
				log.warn("Erro ao configurar a porta para o RMI registry, usando a porta padrão: 1099.");
			}

			// Inicio o RMI Registry...
			try {
				log.debug("Iniciando o RMI registry na porta: " + port);
				LocateRegistry.createRegistry(port);
				log.debug("RMI registry pronto.");
			} catch (Exception e) {
				log.fatal("Erro iniciando o RMI registry. Verifique se a porta " + port + " está livre. Stacktrace: ", e);
				cod = 2;
			}
		} catch (Exception e) {
			log.fatal("Erro desconhecido: ", e);
			cod = 3;
		} finally {
			log.debug("<< AdaptThroughApp.configure()");
		}

		return cod;
	}

	/**
	 * Método de execução principal da classe.<br>
	 * Uilizo ele para poder instanciar a classe e fugir dos statics que o main me forçaria a usar
	 */
	private void execute() {
		log.debug(">> AdaptThroughApp.execute()");

		try {
			// vou no banco buscar as propostas a serem processadas
			ArrayList<TbAnaliseCreditoFncl> proposalList = findAndPrepareProposals();

			// executo as threads
			HashMap<Integer, RetailProposalRegisterClient> executed = executeThreads(proposalList);

			// hora de pegar os dados de tempo para o log
			calculateAndPrintExecutionTimes(executed);

		} catch (Exception e) {
			log.error("Erro esperando a execução das threads.", e);
			e.printStackTrace();
		} finally {
			log.debug("<< AdaptThroughApp.execute()");
		}
	}

	/**
	 * Agrupei nesse método o trabalho com threads para ficar mais organizado.
	 * 
	 * @param proposalList
	 *            entidades que foram trazidas para processamento.
	 * @return HashMap com as instâncias das threads executadas.
	 */
	private HashMap<Integer, RetailProposalRegisterClient> executeThreads(ArrayList<TbAnaliseCreditoFncl> proposalList) {
		log.debug(">> AdaptThroughApp.executeThreads()");

		// pool de threads
		ExecutorService es = Executors.newFixedThreadPool(numThreads);
		// lista com os futuros das execuções
		List<Future<?>> futures = new ArrayList<Future<?>>();
		// aqui vou guardar as instancias das threads executadas para depois pegar o tempo
		HashMap<Integer, RetailProposalRegisterClient> executed = new HashMap<Integer, RetailProposalRegisterClient>();

		try {
			// loop que cria as threads de execução
			for (int i = 1; i <= numThreads; i++) {
				log.debug("Vou instanciar a classe para thread " + i);
				RetailProposalRegisterClient ind = new RetailProposalRegisterClient(proposalList.get(i - 1), "" + i);
				Future<?> future = es.submit(ind);
				futures.add(future);
				log.debug("Mandei executar a thread " + i);
				executed.put(i, ind);
			}

			// espero todas as threads me enviarem o retorno...
			for (Future<?> f : futures) {
				f.get();
			}

			// desligo a pool de threads e continuo com o resto do aplicativo...
			es.shutdown();
		} catch (InterruptedException e) {
			log.error("Erro esperando a execução das threads.", e);
			e.printStackTrace();
		} catch (ExecutionException e) {
			log.error("Erro esperando a execução das threads.", e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Erro esperando a execução das threads.", e);
			e.printStackTrace();
		} finally {
			log.debug("<< AdaptThroughApp.executeThreads()");
		}
		return executed;
	}

	/**
	 * Agrupei nesse método os métodos que utilizam o banco de dados para ficar mais organizado.
	 * 
	 * @return ArrayList com as entidades que foram trazidas para processamento.
	 */
	private ArrayList<TbAnaliseCreditoFncl> findAndPrepareProposals() {
		log.debug(">> AdaptThroughApp.findAndPrepareProposals()");

		EntityManager entityManager = null;
		ArrayList<TbAnaliseCreditoFncl> proposalList = null;

		try {
			entityManager = ConnectionManager.getEntityManager("database.property.dbzema.name");
			entityManager.getTransaction().begin();

			proposalList = findProposalsForProcessing(entityManager);
			markAsBeeingProcessed(proposalList, entityManager);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			log.error("Erro ao pegar a mensagem: ", e);
		} finally {
			if (entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			log.debug("<< AdaptThroughApp.findAndPrepareProposals()");
		}
		return proposalList;
	}

	/**
	 * Busco todas as propostas que serão processadas nesta execução.<br>
	 * A quantidade de propostas é definida pela propriedade de sistema "numThreads"
	 * 
	 * @param entityManager
	 *            EntityManager da transação que foi aberta para buscar as propostas.
	 * @return ArrayList com todas as propostas que serão processadas.
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<TbAnaliseCreditoFncl> findProposalsForProcessing(EntityManager entityManager) {
		log.debug(">> AdaptThroughApp.findProposalsForProcessing()");

		List<TbAnaliseCreditoFncl> list = null;

		try {
			Session session = (Session) entityManager.getDelegate();
			Criteria criteria = session.createCriteria(TbAnaliseCreditoFncl.class);
			// busco apenas status = null
			criteria.add(Restrictions.isNull("status"));
			// ordeno pelas mais novas
			criteria.addOrder(Order.asc("dtinclusao"));
			criteria.addOrder(Order.asc("hrinclusao"));
			// limito os resultados pela quantidade desejada
			criteria.setMaxResults(numThreads);
			// faz consulta com os criterios
			list = criteria.list();
		} catch (Exception e) {
			log.error("Erro ao buscar as propostas na base:", e);
		} finally {
			log.debug(">> AdaptThroughApp.findProposalsForProcessing()");
		}

		return new ArrayList<TbAnaliseCreditoFncl>(list);
	}

	/**
	 * Atualizo todas as propostas para satus de processamento (status=3, data e hora atuais.) nesse método.
	 * 
	 * @param proposals
	 *            ArrayList com todas as propostas que serão processadas.
	 * @param entityManager
	 *            EntityManager da transação que foi aberta para buscar as propostas.
	 */
	private void markAsBeeingProcessed(ArrayList<TbAnaliseCreditoFncl> proposals, EntityManager entityManager) {
		log.debug(">> AdaptThroughApp.markAsBeeingProcessed()");

		try {
			// vou atualizando as propostas com os dados de processamento
			for (TbAnaliseCreditoFncl tbAnaliseCreditoFncl : proposals) {
				TbAnaliseCreditoFnclHome tbAnaliseCreditoFnclDao = new TbAnaliseCreditoFnclHome(entityManager);
				tbAnaliseCreditoFncl.setStatus((short) 3);
				// Devido a forma de gravação peculiar de data tenho que zerar o horário, mantendo a data...
				DateTime dtAnalise = new DateTime();
				dtAnalise = dtAnalise.withTime(0, 0, 0, 0);
				tbAnaliseCreditoFncl.setDtanalise(dtAnalise.toDate());
				// Devido a forma de gravação peculiar de data tenho que jogar a data para 01/01/1900, mantendo o horário...
				DateTime hrAnalise = new DateTime();
				hrAnalise = hrAnalise.withDate(1900, 1, 1);
				tbAnaliseCreditoFncl.setHranalise(new Date());
				tbAnaliseCreditoFnclDao.persist(tbAnaliseCreditoFncl);
			}
		} catch (Exception e) {
			log.error("Erro ao pegar a mensagem: ", e);
		} finally {
			log.debug("<< AdaptThroughApp.markAsBeeingProcessed()");
		}
	}

	/**
	 * Calculo o tempo de execução médio, minimo e máximo e exibo todos os tempos de threads de forma relativamente amigável.
	 * 
	 * @param executed
	 *            HashMap com todas as threads executadas para que eu pegue o tempo que cada uma levou.
	 */
	private void calculateAndPrintExecutionTimes(HashMap<Integer, RetailProposalRegisterClient> executed) {

		log.debug(">> AdaptThroughApp.calculateAndPrintExecutionTimes()");

		long totalTime = 0;
		int maxTimeThread = 0;
		long maxTime = 0;
		int minTimeThread = 0;
		long minTime = Long.MAX_VALUE;

		log.debug("-------------------------------------------------------------");

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
	}

	/**
	 * Aqui começa tudo, não espero nada como argumento
	 * 
	 * @param args
	 *            nada que eu utilize...
	 */
	public static void main(String args[]) {
		long startTime = (new Date()).getTime();
		Logger logger = Logger.getLogger(AdaptThroughApp.class);
		int cod = 0;
		try {
			logger.info("Inicio do processo...");
			AdaptThroughApp app = new AdaptThroughApp();
			cod = app.configure();
			if (cod != 0)
				System.exit(cod);
			app.execute();
		} catch (Throwable e) {
			logger.fatal("Erro inesperado no processamento.", e);
			System.exit(4);
		} finally {
			long endTime = (new Date()).getTime();
			logger.info("Processo finalizado em " + (endTime - startTime) + "ms...");
		}
		System.exit(0);
	}
}

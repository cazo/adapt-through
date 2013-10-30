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

import org.apache.log4j.Logger;

public class DummyApp {

	private String event;
	private Logger log = Logger.getLogger(this.getClass().getName());

	private boolean execute() {
		boolean result = true;

		int numThreads = Integer.parseInt(System.getProperty("numThreads"));

		HashMap<Integer, RetailProposalRegisterClient> executed = new HashMap<Integer, RetailProposalRegisterClient>();

		try {
			// pool de threads
			ExecutorService es = Executors.newFixedThreadPool(numThreads);

			// lista com os futuros das execuções
			List<Future<?>> futures = new ArrayList<Future<?>>();

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
				log.info(".");
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

	public static void main(String args[]) {
		int returnCod = 0;
		long startTime = (new Date()).getTime();
		Logger logger = Logger.getLogger(DummyApp.class);
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
			DummyApp env = new DummyApp();
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

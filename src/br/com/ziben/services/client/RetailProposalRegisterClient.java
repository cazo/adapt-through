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

package br.com.ziben.services.client;

import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.persistence.EntityManager;

import org.apache.cxf.helpers.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.joda.time.DateTime;

import br.com.ziben.model.ConnectionManager;
import br.com.ziben.model.TbAnaliseCreditoComplFncl;
import br.com.ziben.model.TbAnaliseCreditoComplFnclHome;
import br.com.ziben.model.TbAnaliseCreditoComplFnclId;
import br.com.ziben.model.TbAnaliseCreditoFncl;
import br.com.ziben.model.TbAnaliseCreditoFnclHome;
import br.com.ziben.services.RetailProposalRegister;
import br.com.ziben.services.RetailProposalRegister_Service;
import br.com.ziben.services.client.response.RetailProposalRegisterResponse;

/**
 * Classe cliente para a chamar o serviço RetailProposalRegister
 * 
 * @author fcjabulka
 * 
 */
public class RetailProposalRegisterClient extends Thread {

	private Logger log;
	private URL wsdlURL;
	private String event;
	private long totalTime;
	private TbAnaliseCreditoFncl tbAnaliseCreditoFncl;

	public long getTotalTime() {
		return totalTime;
	}

	/**
	 * Construtor para executar o processo através de dados passados por arquivo de dados.
	 * 
	 * @param event
	 *            Nome da propriedade que contém o caminho para o aruivo a ser lido.
	 * @param threadName
	 *            Nome da thread para ser identificada no Log.
	 * @throws Exception
	 *             Caso ocorra algum erro durante a configuração.
	 */
	public RetailProposalRegisterClient(String event, String threadName) throws Exception {
		super();
		configure();
		this.event = event;
		this.tbAnaliseCreditoFncl = null;
		setName(threadName);
	}

	/**
	 * Construtor para executar o processo através de dados passados pelo banco de dados.
	 * 
	 * @param tbAnaliseCreditoFncl
	 *            {@link TbAnaliseCreditoFncl} com os dados do banco já preenchidos.
	 * @param threadName
	 *            Nome da thread para ser identificada no Log.
	 * @throws Exception
	 *             Caso ocorra algum erro durante a configuração.
	 */
	public RetailProposalRegisterClient(TbAnaliseCreditoFncl tbAnaliseCreditoFncl, String threadName) throws Exception {
		super();
		configure();
		this.event = null;
		this.tbAnaliseCreditoFncl = tbAnaliseCreditoFncl;
		setName(threadName);
	}

	/**
	 * Método
	 */
	@Override
	public void run() {
		log.debug(">> RetailProposalRegisterClient.run()");
		long tempoIni = (new Date()).getTime();

		if (tbAnaliseCreditoFncl == null)
			processViaFile();
		else if (event == null)
			processViaDatabase();
		else
			throw new RuntimeException("Não foi possível executar o processo pois não foram passados dados para o aplicativo.");

		long tempoFim = (new Date().getTime());
		totalTime = tempoFim - tempoIni;

		log.debug("Thread " + getName() + " foi executada em " + totalTime + "ms.");
		log.debug("<< RetailProposalRegisterClient.run()");
	}

	/**
	 * Configuro algumas variáveis necessárias para o processamento.
	 * 
	 * @throws Exception
	 *             Quando ocorre algum erro na criação da URL ou das propriedades de do arquivo de LOG.
	 */
	private void configure() throws Exception {
		log.debug(">> RetailProposalRegisterClient.configure()");
		log = Logger.getLogger("Thread." + getName());

		wsdlURL = new URL(System.getProperty("relay.url"));

		if (Boolean.valueOf(System.getProperty("newLogPerThread"))) {
			String logFileName = "./" + System.getProperty("user.log.folder") + "/Thread." + getName() + ".log";
			Properties prop = new Properties();
			prop.setProperty("log4j.logger.Thread." + getName(), "DEBUG, WORKLOG");
			prop.setProperty("log4j.appender.WORKLOG", "org.apache.log4j.RollingFileAppender");
			prop.setProperty("log4j.appender.WORKLOG.File", logFileName);
			prop.setProperty("log4j.appender.WORKLOG.layout", "org.apache.log4j.PatternLayout");
			prop.setProperty("log4j.appender.WORKLOG.layout.ConversionPattern", "%d{HH:mm:ss,SSS} %-5p [%c] (%t) %m%n");
			prop.setProperty("log4j.appender.WORKLOG.Threshold", "DEBUG");

			PropertyConfigurator.configure(prop);
		}
		log.debug("<< RetailProposalRegisterClient.configure()");
	}

	/**
	 * Processamento das propostas que foram buscadas pelo banco de dados.
	 */
	private void processViaDatabase() {
		log.debug(">> RetailProposalRegisterClient.processViaFile()");
		try {
			// O XML esté em um CLOB, preciso passa-lo para String, processo meio chatinho, mas pouco demorado...
			String strng;
			StringBuffer str = new StringBuffer();
			// Crio um BufferedReader para pegar os dados do CLOB...
			BufferedReader bufferRead = new BufferedReader(tbAnaliseCreditoFncl.getDetalhes().getCharacterStream());
			// Vou processando parte a parte, enquanto tiver itens no reader...
			while ((strng = bufferRead.readLine()) != null)
				str.append(strng);
			// Finalmente converto para String...
			String xml = str.toString();

			if (xml == null)
				throw new Exception("Não foi possível pegar o XML do banco ou o XML estava vazio.");

			// Agora sim posso executar o serviço...
			RetailProposalRegister_Service ss = new RetailProposalRegister_Service(wsdlURL, RetailProposalRegister_Service.SERVICE);
			RetailProposalRegister port = ss.getRetailProposalRegister();

			log.debug("Invoking retailProposalRegister...");
			String _retailProposalRegister_retailProposalRegisterRequest = xml;
			String _retailProposalRegister__return = port.retailProposalRegister(_retailProposalRegister_retailProposalRegisterRequest);
			log.debug("retailProposalRegister.result=" + _retailProposalRegister__return);

			// Vou gravar o resultado do processamento...
			postProcess(_retailProposalRegister__return);
		} catch (Exception e) {
			log.error("Erro durante o processamento.", e);
		} finally {
			log.debug("<< RetailProposalRegisterClient.processViaFile()");
		}
	}

	/**
	 * Processamento das propostas que foram lidas de arquivos.
	 */
	private void processViaFile() {
		log.debug(">> RetailProposalRegisterClient.processViaFile()");
		try {
			String eventProp = System.getProperty(event);

			if (eventProp == null)
				throw new Exception("Evento " + event + " nao encontrado. Verificar properties da aplicacao.");

			String xml;

			xml = FileUtils.getStringFromFile(new File(eventProp + getName() + ".xml")).trim();
			// Não sei porque esse troço ta bugando os espaços...mas então resolvo...
			xml = xml.replaceAll("  ", " ");

			if (xml == null)
				throw new Exception("Arquivo xml do evento " + event + " nao encontrado. Verificar properties da aplicacao.");

			RetailProposalRegister_Service ss = new RetailProposalRegister_Service(wsdlURL, RetailProposalRegister_Service.SERVICE);
			RetailProposalRegister port = ss.getRetailProposalRegister();

			log.debug("Invoking retailProposalRegister...");
			String _retailProposalRegister_retailProposalRegisterRequest = xml;
			String _retailProposalRegister__return = port.retailProposalRegister(_retailProposalRegister_retailProposalRegisterRequest);
			log.debug("retailProposalRegister.result=" + _retailProposalRegister__return);
		} catch (Exception e) {
			log.error("Erro durante o processamento.", e);
		} finally {
			log.debug("<< RetailProposalRegisterClient.processViaFile()");
		}
	}

	/**
	 * Método que substitui a execução da procedure abaixo:<br>
	 * 
	 * UPDATE TOTVSSQL.FINANCIAL.DBO.TB_ESTEIRA_CREDITO SET FLCONSULTA = 'S' WHERE CGCCPF = @CGCCPF
	 */
	private void postProcess(String retailProposalRegisterXML) {

		boolean error = false;
		EntityManager entityManager = null;

		try {
			if (retailProposalRegisterXML.contains("chosen as the deadlock victim")) {
				log.error("Erro no serviço: " + retailProposalRegisterXML);
				markForReprocess();
			} else {
				RetailProposalRegisterResponse retailProposalRegisterResponse = RetailProposalRegisterResponse.xmlToObject(retailProposalRegisterXML);

				entityManager = ConnectionManager.getEntityManager(System.getProperty("database.property.dbzema.name"));
				entityManager.getTransaction().begin();

				// Atualizo a tabela TB_ANALISE_CREDITO_FNCL
				TbAnaliseCreditoFnclHome tbAnaliseCreditoFnclDao = new TbAnaliseCreditoFnclHome(entityManager);
				tbAnaliseCreditoFncl.setStatus(Short.valueOf(retailProposalRegisterResponse.getAnalysisStatus()));
				tbAnaliseCreditoFnclDao.persist(tbAnaliseCreditoFncl);

				// Insiro na tabela TB_ANALISE_CREDITO_FNCL
				TbAnaliseCreditoComplFncl tbAnaliseCreditoComplFncl = new TbAnaliseCreditoComplFncl();
				TbAnaliseCreditoComplFnclHome tbAnaliseCreditoComplFnclHome = new TbAnaliseCreditoComplFnclHome(entityManager);
				TbAnaliseCreditoComplFnclId tbAnaliseCreditoComplFnclId = new TbAnaliseCreditoComplFnclId();

				tbAnaliseCreditoComplFnclId.setCodfil(tbAnaliseCreditoFncl.getId().getCodfil());
				tbAnaliseCreditoComplFnclId.setCreditApplicationCod(retailProposalRegisterResponse.getContract());
				DateTime dtAnalise = new DateTime();
				dtAnalise.withTime(0, 0, 0, 0);
				tbAnaliseCreditoComplFnclId.setDtultmov(dtAnalise.toDate());
				DateTime hrAnalise = new DateTime();
				hrAnalise.withDate(1900, 1, 1);
				tbAnaliseCreditoComplFnclId.setHrultmov(hrAnalise.toDate());
				tbAnaliseCreditoComplFnclId.setIdanalise(tbAnaliseCreditoFncl.getId().getIdanalise());
				tbAnaliseCreditoComplFnclId.setMensagem1(retailProposalRegisterResponse.getMessage1());
				tbAnaliseCreditoComplFnclId.setMensagem2(retailProposalRegisterResponse.getMessage2());
				tbAnaliseCreditoComplFnclId.setXmllog(null);
				tbAnaliseCreditoComplFncl.setId(tbAnaliseCreditoComplFnclId);
				tbAnaliseCreditoComplFnclHome.persist(tbAnaliseCreditoComplFncl);
				
				// TODO atualizar tabela da esteira de crédito

				entityManager.getTransaction().commit();
			}

		} catch (Exception e) {
			log.error("Erro ao pegar a mensagem: ", e);
			error = true;
		} finally {
			if (entityManager.getTransaction().isActive())
				entityManager.getTransaction().rollback();
			log.debug("<< AdaptThroughApp.markAsBeeingProcessed()");
		}
		
		if (error) {
			try {
				entityManager = ConnectionManager.getEntityManager("database.property.dbzema.name");
				entityManager.getTransaction().begin();
				// Atualizo a tabela TB_ANALISE_CREDITO_FNCL
				TbAnaliseCreditoFnclHome tbAnaliseCreditoFnclDao = new TbAnaliseCreditoFnclHome(entityManager);
				tbAnaliseCreditoFncl.setStatus((short) 0);
				tbAnaliseCreditoFnclDao.persist(tbAnaliseCreditoFncl);
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				log.error("Erro ao marcar status do processamento como erro: ", e);
			}
		}
	}

	/**
	 * Tenho visto alguns deadlock, quando isso acontece marco para reprocessar, já que isso é temporário...
	 */
	private void markForReprocess() {
		EntityManager entityManager = null;

		try {
			entityManager = ConnectionManager.getEntityManager("database.property.dbzema.name");
			entityManager.getTransaction().begin();
			// Atualizo a tabela TB_ANALISE_CREDITO_FNCL
			TbAnaliseCreditoFnclHome tbAnaliseCreditoFnclDao = new TbAnaliseCreditoFnclHome(entityManager);
			tbAnaliseCreditoFncl.setStatus((short) 0);
			tbAnaliseCreditoFnclDao.persist(tbAnaliseCreditoFncl);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			log.error("Erro ao marcar para o processo para reprocessamento: ", e);
		}
	}
}

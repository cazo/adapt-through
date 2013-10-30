package br.com.ziben.main;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.cxf.helpers.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.ziben.services.RetailProposalRegister;
import br.com.ziben.services.RetailProposalRegister_Service;

public class RetailProposalRegisterClient extends Thread {

	private long totalTime;
	private String event;
	private static Object syncSysOut = new Object();

	public long getTotalTime() {
		return totalTime;
	}

	public RetailProposalRegisterClient(String event, String threadName) {
		super();
		this.event = event;
		setName(threadName);
	}

	@Override
	public void run() {

		Logger log = Logger.getLogger("Thread." + getName());
		String logFileName = "./" + System.getProperty("user.log.folder") + "/Thread." + getName() + ".log";

		Properties prop = new Properties();
		prop.setProperty("log4j.logger.Thread." + getName(), "DEBUG, WORKLOG");
		prop.setProperty("log4j.appender.WORKLOG", "org.apache.log4j.RollingFileAppender");
		prop.setProperty("log4j.appender.WORKLOG.File", logFileName);
		prop.setProperty("log4j.appender.WORKLOG.layout", "org.apache.log4j.PatternLayout");
		prop.setProperty("log4j.appender.WORKLOG.layout.ConversionPattern", "%d{HH:mm:ss,SSS} %-5p [%c] (%t) %m%n");
		prop.setProperty("log4j.appender.WORKLOG.Threshold", "DEBUG");

		PropertyConfigurator.configure(prop);

		try {
			long tempoIni = (new Date()).getTime();

			// TODO isso vem do banco, tabela GESTOR_VAREJO_SERVICO pro servico RetailProposalRegister!
			// URL wsdlURL = RetailProposalRegister_Service.WSDL_LOCATION;
			URL wsdlURL = new URL(System.getProperty("relay.url"));

			String eventProp = System.getProperty(event);

			if (eventProp == null)
				throw new Exception("Evento " + event + " nao encontrado. Verificar properties da aplicacao.");

			String xml;

			xml = FileUtils.getStringFromFile(new File(eventProp + getName() + ".xml")).trim();
			xml = xml.replaceAll("  ", " ");
			log.info("----------------------------");
			log.info(xml);
			log.info("----------------------------");

			if (xml == null)
				throw new Exception("Arquivo xml do evento " + event + " nao encontrado. Verificar properties da aplicacao.");

			RetailProposalRegister_Service ss = new RetailProposalRegister_Service(wsdlURL, RetailProposalRegister_Service.SERVICE);
			RetailProposalRegister port = ss.getRetailProposalRegister();

			log.info("vai executar o serviço");
			{
				log.info("Invoking retailProposalRegister...");
				String _retailProposalRegister_retailProposalRegisterRequest = xml;
				String _retailProposalRegister__return = port.retailProposalRegister(_retailProposalRegister_retailProposalRegisterRequest);
				log.info("retailProposalRegister.result=" + _retailProposalRegister__return);
			}

			long tempoFim = (new Date().getTime());
			totalTime = tempoFim - tempoIni;
			String threadExecTime = "Thread " + getName() + " foi executada em " + totalTime + "ms.";

			synchronized (syncSysOut) {
				log.info(threadExecTime);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

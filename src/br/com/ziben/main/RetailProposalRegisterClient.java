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

import java.io.BufferedReader;
import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import org.apache.cxf.helpers.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.ziben.model.TbAnaliseCreditoFncl;
import br.com.ziben.services.RetailProposalRegister;
import br.com.ziben.services.RetailProposalRegister_Service;

public class RetailProposalRegisterClient extends Thread {

	private Logger log;
	private String event;
	private long totalTime;
	private TbAnaliseCreditoFncl tbAnaliseCreditoFncl;

	public long getTotalTime() {
		return totalTime;
	}

	public RetailProposalRegisterClient(String event, String threadName) {
		super();
		configureLogger();
		this.event = event;
		setName(threadName);
	}

	public RetailProposalRegisterClient(TbAnaliseCreditoFncl tbAnaliseCreditoFncl, String threadName) {
		super();
		configureLogger();
		this.tbAnaliseCreditoFncl = tbAnaliseCreditoFncl;
		setName(threadName);
	}

	@Override
	public void run() {
		long tempoIni = (new Date()).getTime();

		processViaDatabase();
		processViaFile();

		long tempoFim = (new Date().getTime());
		totalTime = tempoFim - tempoIni;

		log.debug("Thread " + getName() + " foi executada em " + totalTime + "ms.");
	}

	private void configureLogger() {
		log = Logger.getLogger("Thread." + getName());
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

	private void processViaDatabase() {
		try {
			URL wsdlURL = new URL(System.getProperty("relay.url"));

			StringBuffer str = new StringBuffer();

			String strng;
			BufferedReader bufferRead = new BufferedReader(tbAnaliseCreditoFncl.getDetalhes().getCharacterStream());
			while ((strng = bufferRead.readLine()) != null)
				str.append(strng);

			String xml = str.toString();

			if (xml == null)
				throw new Exception("Não foi possível pegar o XML do banco.");

			RetailProposalRegister_Service ss = new RetailProposalRegister_Service(wsdlURL, RetailProposalRegister_Service.SERVICE);
			RetailProposalRegister port = ss.getRetailProposalRegister();

			log.debug("Invoking retailProposalRegister...");
			String _retailProposalRegister_retailProposalRegisterRequest = xml;
			String _retailProposalRegister__return = port.retailProposalRegister(_retailProposalRegister_retailProposalRegisterRequest);
			log.debug("retailProposalRegister.result=" + _retailProposalRegister__return);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processViaFile() {

		try {
			URL wsdlURL = new URL(System.getProperty("relay.url"));

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
			e.printStackTrace();
		}
	}
}

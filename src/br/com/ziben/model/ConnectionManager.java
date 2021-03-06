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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

/**
 * Classe utilitaria para conexoes a entidades hiberneticas!
 * 
 * @author Claudio Cardozo, Frederico Jabulka
 * 
 */
public class ConnectionManager {

	private static HashMap<String, EntityManagerFactory> emfMap;
	private static final Object syncObj = new Object();

	/**
	 * Inicializa as configura��es estaticas do Connection Manager
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static void initEntityManager(String factoryName) {
		Logger logger = Logger.getLogger(ConnectionManager.class.getName());
		logger.debug(">>Configuration()");

		String nomeArquivo = "./" + System.getProperty(factoryName) + ".properties";
		if (nomeArquivo == null || nomeArquivo.equals("./")) {
			nomeArquivo = "./" + "hibernate.properties";
			logger.info("Setando arquivo de configuracoes " + nomeArquivo);
		}
		logger.debug(">>ConnectionManager: arquivo de configuracao: " + nomeArquivo);
		File configFile = new File(nomeArquivo);
		Properties properties = new Properties();
		try {
			// pega o arquivo de propriedades na raiz do projeto
			properties.load(new FileInputStream(configFile));
		} catch (IOException e) {
			throw new ExceptionInInitializerError("Arquivo de configuracao nao encontrado : " + nomeArquivo);
		}

		try {
			logger.info(">>ConnectionManager: Configurando o propriedades do hibernate / Entity Manager...");

			// O Persistencce Unit deve ser colocado na propriedade "persistence.unit.name" no arquivo de propriedades configurado no "persistence.configuration".
			// Isso � feito assim para que essa classe possa ser reutilizada por todos os projetos que utilizem Hibernate de forma simples, sem precisar reescrever ou
			// sobrescrever nenhuma parte do c�digo.
			emfMap.put(factoryName, Persistence.createEntityManagerFactory(properties.getProperty("persistence.unit.name"), (Map) properties));

		} catch (Exception e) {
			logger.error(">> Erro ao configurar o Entity Manager: ", e);
			// throw new ExceptionInInitializerError(t);
		} finally {
			logger.debug("<<Configuration()");
		}

	}

	/**
	 * Pega um entidade de geranciamento da JPA
	 * 
	 * @return EntityManager para o hibernate
	 */
	public static EntityManager getEntityManager(String factoryName) {
		// Garanto que o entityManager j� est� instanciado antes de retornar...
		// Aqui eu tenho que estar sincronizado, pode ser um pequeno gargalo na primeira thread que entrar, mas garando que s� perco tempo com inicializa��o uma vez.
		synchronized (syncObj) {
			if (emfMap == null) {
				emfMap = new HashMap<String, EntityManagerFactory>();
			}

			if (emfMap.get(factoryName) == null) {
				initEntityManager(factoryName);
			}
		}
		return emfMap.get(factoryName).createEntityManager();
	}
}
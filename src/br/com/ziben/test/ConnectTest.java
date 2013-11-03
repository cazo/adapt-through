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

package br.com.ziben.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.ziben.main.AdaptThroughApp;
import br.com.ziben.model.ConnectionManager;
import br.com.ziben.model.TbAnaliseCreditoFncl;
import br.com.ziben.model.TbEsteiraCredito;

public class ConnectTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Logger log = Logger.getLogger(AdaptThroughApp.class);

		// Seto as propriedades...
		if (System.getProperty("user.properties") != null) {
			Properties props = System.getProperties();

			try {
				props.load(new FileInputStream(System.getProperty("user.properties")));
			} catch (Exception safe) {
				log.warn("Erro ao carregar as váriaveis de ambiente: ", safe);
			}
			System.setProperties(props);
		}

		// Pego a porta do RMI registry...
		int port = 1099;
		try {
			port = Integer.valueOf(System.getProperty("rmi.registry.port"));
		} catch (Exception e) {
			port = 1099;
			log.warn("Erro ao configurar a porta para o RMI registry, usando a padrão: 1099.");
		}

		// Inicio o RMI Registry...
		try {
			log.debug("Iniciando o RMI registry na porta: " + port);
			LocateRegistry.createRegistry(port);
			log.debug("RMI registry pronto.");
		} catch (Exception e) {
			log.error("Exception iniciando o RMI registry: ", e);
		}

		// Hora dos testes!!!
		int i = 0;
		try {
			List<TbEsteiraCredito> listTbEsteira = null;
			Session session = (Session) ConnectionManager.getEntityManager("database.property.dbfinancial.name").getDelegate();
			Criteria criteria = session.createCriteria(TbEsteiraCredito.class);
			criteria.add(Restrictions.ne("cgccpf", 191L));
			criteria.setMaxResults(1);
			// faz consulta com os criterios
			listTbEsteira = criteria.list();
			log.debug("Voltei do select..");

			for (TbEsteiraCredito tbEsteiraCredito : listTbEsteira) {
				i++;
				log.warn("NOT REALLY A WARNING! - Retorno TbEsteiraCredito " + i + " Nome do cidadão: " + tbEsteiraCredito.getNomcli());
			}
		} catch (Exception e) {
			log.error("", e);
		}


		try {
			List<TbAnaliseCreditoFncl> listTbAnalise = null;
			Session session = (Session) ConnectionManager.getEntityManager("database.property.dbzema.name").getDelegate();
			Criteria criteria = session.createCriteria(TbAnaliseCreditoFncl.class);
			criteria.add(Restrictions.isNull("status"));
			criteria.addOrder(Order.asc("dtinclusao"));
			criteria.addOrder(Order.asc("hrinclusao"));
			criteria.setMaxResults(1);
			// faz consulta com os criterios
			listTbAnalise = criteria.list();
			log.debug("Voltei do select..");

			i = 0;
			for (TbAnaliseCreditoFncl tbAnaliseCreditoFncl : listTbAnalise) {
				StringBuffer str = new StringBuffer();
				try {
					String strng;
					BufferedReader bufferRead = new BufferedReader(tbAnaliseCreditoFncl.getDetalhes().getCharacterStream());
					while ((strng = bufferRead.readLine()) != null)
						str.append(strng);
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
				log.warn("NOT REALLY A WARNING! - Retorno TbAnaliseCreditoFncl " + i + ": " + str.toString());
			}
		} catch (Exception e) {
			log.error("", e);
		}
		
		System.exit(0);
	}
}

package br.com.ziben.test;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.ziben.main.AdaptThroughApp;
import br.com.ziben.model.ConnectionManager;
import br.com.ziben.model.TbEsteiraCredito;

public class ConnectTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// EntityManager entityManager = null;
		Logger log = Logger.getLogger(AdaptThroughApp.class);

		if (System.getProperty("user.properties") != null) {
			Properties props = System.getProperties();

			try {
				props.load(new FileInputStream(System.getProperty("user.properties")));
			} catch (Exception safe) {
				log.warn("Erro ao carregar as váriaveis de ambiente: ", safe);
			}
			System.setProperties(props);
		}
		
		/*
		 * entityManager = ConnectionManager.getEntityManager();
		 * entityManager.getTransaction().begin();
		 * TbAnaliseCreditoFncl tbAnal = new TbAnaliseCreditoFncl();
		 * TbAnaliseCreditoFnclHome tbDAO = new TbAnaliseCreditoFnclHome(entityManager);
		 * TbAnaliseCreditoFnclId id = new TbAnaliseCreditoFnclId();
		 * short cod = 672;
		 * id.setCodfil(cod);
		 * id.setIdanalise(1912);
		 * short prod = 1;
		 * id.setIdproduto(prod);
		 * tbAnal = tbDAO.findById(id);
		 * log.debug("Voltei do select..");
		 * StringBuffer str = new StringBuffer();
		 * String strng;
		 * try {
		 * BufferedReader bufferRead = new BufferedReader(tbAnal.getDetalhes().getCharacterStream());
		 * while ((strng = bufferRead.readLine()) != null)
		 * str.append(strng);
		 * } catch (Exception e) {
		 * e.printStackTrace();
		 * }
		 * log.debug(str.toString());
		 */

		List<TbEsteiraCredito> listTbEsteira = null;
		
		try {
			Session session = (Session) ConnectionManager.getEntityManager("database.property.dbfinancial.name").getDelegate();
			Criteria criteria = session.createCriteria(TbEsteiraCredito.class);
			criteria.add(Restrictions.ne("cgccpf", 191L));
			criteria.setMaxResults(50);
			// faz consulta com os criterios
			listTbEsteira = criteria.list();
			log.debug("Voltei do select..");
		} catch (Exception e) {
			log.error("", e);
		}
		
		for (TbEsteiraCredito tbEsteiraCredito : listTbEsteira) {
			log.debug(tbEsteiraCredito.getCgccpf());
		}

		/*
		List<TbAnaliseCreditoFncl> listTbAnalise = null;

		try {
			Session session = (Session) ConnectionManager.getEntityManager("database.property.dbzema.name").getDelegate();
			Criteria criteria = session.createCriteria(TbAnaliseCreditoFncl.class);
			criteria.add(Restrictions.isNull("status"));
			criteria.addOrder(Order.asc("dtinclusao"));
			criteria.addOrder(Order.asc("hrinclusao"));
			criteria.setMaxResults(50);
			// faz consulta com os criterios
			listTbAnalise = criteria.list();
			log.debug("Voltei do select..");
		} catch (Exception e) {
			log.error("", e);
		}

		int i = 0;
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
			log.debug("Retorno: " + i + " " + str.toString());
		}
		*/
	}
}

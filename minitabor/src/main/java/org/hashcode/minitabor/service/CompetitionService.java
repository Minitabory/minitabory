package org.hashcode.minitabor.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hashcode.minitabor.model.Competition;
import org.hashcode.minitabor.model.CompetitionResult;
import org.hashcode.minitabor.util.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

/**
 * Služba pro práci s přihláškami
 * 
 * @author Zigi
 *
 */
public class CompetitionService {
	private static final Logger LOG = Logger.getLogger(CompetitionService.class);

	public static Competition parseRequest(HttpServletRequest req, CompetitionResult result) {
		try {
			Competition competition = new Competition();
			competition.setFirstName(req.getParameter("wdform_19_element3"));
			competition.setLastName(req.getParameter("wdform_20_element3"));
			competition.setBorn(Util.parseDate(req.getParameter("wdform_8_element3")));
			competition.setStreet(req.getParameter("wdform_10_street13"));
			competition.setCity(req.getParameter("wdform_12_city3"));
			competition.setZip(req.getParameter("wdform_14_postal3"));
			competition.setEmail(req.getParameter("wdform_16_element3"));
			competition.setMobile(req.getParameter("wdform_17_element3"));
			competition.setTroubles(req.getParameter("wdform_18_element3"));
			return competition;
		} catch (Exception e) {
			LOG.error("Chyba při načtení informací z requestu", e);
			result.setError("Chybně vyplněný formulář");
		}
		return null;
	}

	/**
	 * Vložení přihlášky do DB
	 * 
	 * @param comp
	 * @return
	 * @throws Exception
	 */
	public static boolean put(Competition comp) throws Exception {
		Entity competitionEntity = new Entity("Competition", comp.getUuid());
		competitionEntity.setProperty("firstName", comp.getFirstName());
		competitionEntity.setProperty("lastName", comp.getLastName());
		competitionEntity.setProperty("email", comp.getEmail());
		competitionEntity.setProperty("born", comp.getBorn());
		competitionEntity.setProperty("city", comp.getCity());
		competitionEntity.setProperty("created", comp.getCreated());
		competitionEntity.setProperty("mobile", comp.getMobile());
		competitionEntity.setProperty("street", comp.getStreet());
		competitionEntity.setProperty("troubles", comp.getTroubles());
		competitionEntity.setProperty("zip", comp.getZip());
		competitionEntity.setProperty("uniqueId", comp.getUniqueId());

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(competitionEntity);

		// načteme uložený objekt
		Key competitionKey = KeyFactory.createKey("Competition", comp.getUuid());
		Entity created = datastore.get(competitionKey);
		return created != null ? true : false;
	}

	/**
	 * Zjištění, zda již bylo dítě registrované
	 * 
	 * @param comp
	 * @return
	 * @throws Exception
	 */
	public static boolean isExist(Competition comp) throws Exception {
		// filtr
		Filter uniqueId = new FilterPredicate("uniqueId", FilterOperator.EQUAL, comp.getUniqueId());

		// dotaz
		Query q = new Query("Competition").setFilter(uniqueId);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		Entity result = pq.asSingleEntity();

		return result != null ? true : false;
	}

	public static boolean isFull(Integer maxCount, Date bornEnd) throws Exception {
		Filter filterBorn = new FilterPredicate("born", FilterOperator.GREATER_THAN, bornEnd);

		Query q = new Query("Competition").setFilter(filterBorn);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		FetchOptions option = FetchOptions.Builder.withChunkSize(maxCount);
		int count = pq.countEntities(option);

		return count < maxCount ? false : true;
	}

	/**
	 * Porovnává instance objektů
	 * 
	 * @param comp1
	 * @param comp2
	 * @return
	 */
	public static boolean isSame(Competition comp1, Entity comp2) {
		if (comp1 != null && comp2 != null) {
			String uuid1 = comp1.getUuid();
			String uuid2 = comp2.getKey().getName();
			if (uuid1 != null && uuid2 != null)
				return uuid1.equals(uuid2);
		}
		return false;
	}
}

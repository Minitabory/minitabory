package org.hashcode.minitabor.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hashcode.minitabor.model.Competition;
import org.hashcode.minitabor.model.CompetitionResult;
import org.hashcode.minitabor.service.CompetitionService;

import com.google.appengine.repackaged.com.google.gson.Gson;

public class CompetitionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1975550687450260105L;
	private static final Logger LOG = Logger.getLogger(CompetitionServlet.class);
	private static final int MAX_COUNT = 30;
	private static final Date BORN_END = new GregorianCalendar(2003, 7, 13).getTime();

	@Override

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/xml");
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "GET,POST");
		resp.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

		CompetitionResult result = new CompetitionResult();
		result.setRedirect("http://minitabor.info/prihlaseni-neuspesne/");

		try {
			Competition comp = CompetitionService.parseRequest(req, result);

			if (comp != null) {
				boolean isExist = CompetitionService.isExist(comp);

				// dítě ještě nebylo přihlášené
				if (isExist == false) {

					// je překročen maximální počet dětí?
					boolean isFull = CompetitionService.isFull(MAX_COUNT, BORN_END);

					// ještě je místo
					if (isFull == false) {

						// přesměrování po úspěšném uložení
						if (CompetitionService.put(comp) == true) {

							result.setError(null);
							result.setRedirect("http://minitabor.info/prihlaseni-uspesne/");
						}

						// uložení se nezdařilo
						else {
							LOG.error("Uložení přihlášky se nepodařilo");
							result.setError("Přihlášení se nezdařilo");
						}
					}

					// už není místo pro přihlášení
					else {
						LOG.error("Bylo dosaženo maximálního limitu dětí.");
						result.setError(
								"Bylo dosaženo maximálního limitu dětí, ale nezoufejte. Pokud se uvolní místo, budeme Vás informovat.");
						result.setRedirect("http://minitabor.info/mame-plno/");
						result.setEnabled(false);
					}
				}

				// Dítě již bylo přihlášené
				else {
					LOG.error("Dítě již bylo přihlášeno");
					result.setError("Vaše dítě již bylo přihlášeno");
					result.setRedirect(null);
				}
			}

		} catch (Exception e) {
			LOG.error("Chyba při zpracování služby", e);
		}

		try {
			// vracíme JSON odpověď
			Gson gson = new Gson();
			String json = gson.toJson(result);

			resp.getWriter().append(json);
		} catch (Exception ex) {
			LOG.error("Přihláška byla uložena, ale stránku se nepodařilo přesměrovat", ex);
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/xml");
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "GET,POST");
		resp.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

		CompetitionResult result = new CompetitionResult();

		try {
			boolean isFull = CompetitionService.isFull(MAX_COUNT, BORN_END);

			// ještě je místo
			if (isFull)
				result.setEnabled(false);
		} catch (Exception e) {
			LOG.error("Chyba při zpracování služby", e);
		}

		try {
			// vracíme JSON odpověď
			Gson gson = new Gson();
			String json = gson.toJson(result);

			resp.getWriter().append(json);
		} catch (Exception ex) {
			LOG.error("Přihláška byla uložena, ale stránku se nepodařilo přesměrovat", ex);
		}
	}
}
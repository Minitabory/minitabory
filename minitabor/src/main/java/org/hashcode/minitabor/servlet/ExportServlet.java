package org.hashcode.minitabor.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hashcode.minitabor.service.CompetitionService;

public class ExportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5954409080429181216L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Access-Control-Allow-Methods", "GET,POST");
		resp.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		resp.setHeader("Content-Disposition", "attachment; filename=\"competitions.csv\"");
		resp.setContentType("text/csv");

		try {
			OutputStream outputStream = resp.getOutputStream();
			String csv = CompetitionService.toCsv(CompetitionService.find());
			outputStream.write(csv.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {

		}

	}
}
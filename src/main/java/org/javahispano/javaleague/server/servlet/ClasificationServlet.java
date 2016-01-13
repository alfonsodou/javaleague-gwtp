/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.dao.JourneyDao;
import org.javahispano.javaleague.server.dao.LeagueDao;

/**
 * @author alfonso
 *
 */
public class ClasificationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7232321012075894427L;
	private final Logger logger;
	private final LeagueDao leagueDao;
	private final JourneyDao journeyDao;

	@Inject
	ClasificationServlet(Logger logger, LeagueDao leagueDao,
			JourneyDao journeyDao) {
		this.logger = logger;
		this.leagueDao = leagueDao;
		this.journeyDao = journeyDao;
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	}
}

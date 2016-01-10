/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;

import org.javahispano.javaleague.server.dao.LeagueDao;

/**
 * @author alfonso
 *
 */
public class TournamentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5084046081204808866L;

	private final Logger logger;
	private final LeagueDao leagueDao;
	
	@Inject
	TournamentServlet(Logger logger, LeagueDao leagueDao) {
		this.logger = logger;
		this.leagueDao = leagueDao;
	}
}

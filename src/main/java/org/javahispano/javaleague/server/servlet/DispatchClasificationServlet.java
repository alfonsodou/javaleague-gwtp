/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.dao.LeagueDao;
import org.javahispano.javaleague.server.dao.domain.Journey;
import org.javahispano.javaleague.server.dao.domain.League;
import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.parameters.LeagueParameters;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * @author alfonso
 *
 */
public class DispatchClasificationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final LeagueDao leagueDao;
	private final Logger logger;

	private static Queue queue = QueueFactory.getDefaultQueue();

	@Inject
	DispatchClasificationServlet(LeagueDao leagueDao, Logger logger) {
		this.leagueDao = leagueDao;
		this.logger = logger;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.warning("**** LeagueID: " + LeagueParameters.getLeagueId());
		League league = leagueDao.get(LeagueParameters.getLeagueId());

		if (league.getRound() < league.getRoundMax()) {
			int round = league.getRound() + 1;
			Journey journey = Deref.deref(league.getJourneys().get(round));
			logger.info("**** Jornada: " + round + " :: Fecha: "
					+ journey.getDate());
			if (journey.getDate().compareTo(new Date()) < 0) {
				queue.add(TaskOptions.Builder.withUrl("/clasificationServlet")
						.param("round", String.valueOf(round)));
				logger.info("**** Creada tarea actualización clasificación ronda: "
						+ round);
			} else {
				logger.info("**** La fecha actual es menor que la fecha de la jornada. No se ejecuta la actualización de la clasificación");
			}
		} else {
			logger.info("**** Ya se ejecutó la última jornada de liga. No es necesario actualizar la clasificación");
		}

	}
}

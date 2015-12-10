/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.dao.MatchDao;
import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.shared.parameters.MatchParameters;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * @author alfonso
 *
 */
@Singleton
public class DispatchMatchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2547251632796479943L;
	private final Logger logger;
	private final MatchDao matchDao;
	private static Queue queue = QueueFactory.getDefaultQueue();
	
	@Inject
	DispatchMatchServlet(Logger logger, MatchDao matchDao) {
		this.logger = logger;
		this.matchDao = matchDao;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Match> matchs = matchDao.getMatchsForPlay();
		for(Match m : matchs) {
			queue.add(TaskOptions.Builder.withUrl("/playMatchServlet").param(
					"matchID", m.getId().toString()));
			
			m.setState(MatchParameters.getMATCHSTATE_QUEUE());
			matchDao.put(m);
			
			logger.info("Match: " + m.getId() + " on queue");
		}
	}
}

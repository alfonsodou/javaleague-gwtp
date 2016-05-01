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

import org.javahispano.javaleague.server.dao.FinalMatchDao;
import org.javahispano.javaleague.server.dao.domain.FinalMatch;
import org.javahispano.javaleague.server.dao.domain.FinalMatchType;

/**
 * @author alfonso
 *
 */
public class FinalMatchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger;
	private final FinalMatchDao finalMatchDao;

	@Inject
	FinalMatchServlet(Logger logger, FinalMatchDao finalMatchDao) {
		this.logger = logger;
		this.finalMatchDao = finalMatchDao;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Creamos los octavos
		for (int i = 0; i < 8; i++) {
			FinalMatch finalMatch = new FinalMatch(null,
					FinalMatchType.OCTAVOS, i);
			finalMatchDao.put(finalMatch);
		}
		
		// Creamos los cuartos
		for(int i = 0; i < 4; i++) {
			FinalMatch finalMatch = new FinalMatch(null,
					FinalMatchType.CUARTOS, i);
			finalMatchDao.put(finalMatch);
		}

		// Creamos las semifinales
		for(int i = 0; i < 2; i++) {
			FinalMatch finalMatch = new FinalMatch(null,
					FinalMatchType.SEMIFINAL, i);
			finalMatchDao.put(finalMatch);
		}
		
		FinalMatch finalMatch = new FinalMatch(null, FinalMatchType.TERCERPUESTO, 0);
		finalMatchDao.put(finalMatch);
		
		finalMatch = new FinalMatch(null, FinalMatchType.FINAL, 0);
		finalMatchDao.put(finalMatch);
	}

}

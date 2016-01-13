/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;

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

	@Inject
	ClasificationServlet(Logger logger) {
		this.logger = logger;
	}
}

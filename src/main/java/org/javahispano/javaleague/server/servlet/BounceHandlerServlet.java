/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

/**
 * @author alfonso
 *
 */
import java.io.IOException;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.mail.BounceNotification;
import com.google.appengine.api.mail.BounceNotificationParser;

public class BounceHandlerServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(BounceHandlerServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		BounceNotification bounce;
		try {
			bounce = BounceNotificationParser.parse(req);
			log.warning(bounce.getOriginal().getText());
			log.warning(bounce.getNotification().getText());
		} catch (MessagingException e) {
			log.warning(e.getMessage());
		}
	}
}

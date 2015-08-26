/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.server.utils.ServletUtils;

/**
 * @author alfonso
 *
 */
@Singleton
public class AuthenticateUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9166307455281960113L;
	
	private final Logger logger;
	private final UserDao userDao;
	
	@Inject
	AuthenticateUserServlet(Logger logger, UserDao userDao) {
		this.logger = logger;
		this.userDao = userDao;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		String token = req.getParameter("token");
		User user = userDao.findByEmail(email);
		
		if (user != null) {
			if (token.equals(user.getToken())) {
				user.setActive(true);
				
				userDao.put(user);
				
				resp.sendRedirect(ServletUtils.getBaseUrl() + "#confirmRegister");
			}
		}
		
	}

}

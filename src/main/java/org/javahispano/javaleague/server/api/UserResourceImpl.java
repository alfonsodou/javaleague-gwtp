/**
 * 
 */
package org.javahispano.javaleague.server.api;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.shared.api.UserResource;

/**
 * @author alfonso
 *
 */
public class UserResourceImpl implements UserResource {
    private final Logger logger;
    private final UserDao userDao;
    
    @Inject
    UserResourceImpl(Logger logger,
    				UserDao userDao) {
    	this.logger = logger;
    	this.userDao = userDao;
    }

	@Override
	public void authenticate(String token, String email) {
		User user = userDao.findByEmail(email);
		if (user != null) {
			if (token.equals(user.getToken())) {
				user.setActive(true);
				
				userDao.put(user);
			}
		}
	}

}

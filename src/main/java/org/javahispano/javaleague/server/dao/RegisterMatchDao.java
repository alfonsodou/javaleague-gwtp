/**
 * 
 */
package org.javahispano.javaleague.server.dao;

import org.javahispano.javaleague.server.dao.domain.RegisterMatch;
import org.javahispano.javaleague.server.dao.domain.User;

import com.googlecode.objectify.Ref;

/**
 * @author adou
 *
 */
public class RegisterMatchDao extends BaseDao<RegisterMatch> {

	public RegisterMatchDao() {
		super(RegisterMatch.class);
	}

	public RegisterMatch findByUser(User user) {
		return ofy().load().type(RegisterMatch.class)
				.filter("user", Ref.create(user)).first().now();
	}

	public RegisterMatch findByMatch() {
		return ofy().load().type(RegisterMatch.class).filter("match", null)
				.first().now();
	}
}

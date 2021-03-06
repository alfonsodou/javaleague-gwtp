/**
 * 
 */
package org.javahispano.javaleague.server.dao;

import org.javahispano.javaleague.server.dao.domain.Journey;

/**
 * @author alfonso
 *
 */
public class JourneyDao extends BaseDao<Journey> {
	public JourneyDao() {
		super(Journey.class);
	}

	public Journey findByRound(int round) {
		return ofy().load().type(Journey.class).filter("round", round).first()
				.now();
	}
}

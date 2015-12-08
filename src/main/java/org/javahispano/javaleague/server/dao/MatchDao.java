/**
 * 
 */
package org.javahispano.javaleague.server.dao;

import java.util.List;

import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.server.dao.domain.MatchState;

/**
 * @author alfonso
 *
 */
public class MatchDao extends BaseDao<Match> {

	public MatchDao() {
		super(Match.class);
	}

	public List<Match> findByUserIdHome(Long userId) {
		return ofy().load().type(Match.class).filter("userIdHome", userId)
				.list();
	}

	public List<Match> findByUserIdAway(Long userId) {
		return ofy().load().type(Match.class).filter("userIdAway", userId)
				.list();
	}

	public List<Match> findByRound(int round) {
		return ofy().load().type(Match.class).filter("round", round).list();
	}

	public List<Match> getMatchsForPlay() {
		return ofy().load().type(Match.class).filter("state", MatchState.WAIT)
				.filter("isFriendly", true).list();
	}
}

/**
 * 
 */
package org.javahispano.javaleague.server.dao;

import java.util.Collections;
import java.util.List;

import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.shared.parameters.MatchParameters;

import com.googlecode.objectify.Ref;

/**
 * @author alfonso
 *
 */
public class MatchDao extends BaseDao<Match> {

	public MatchDao() {
		super(Match.class);
	}

	public List<Match> findByUserHome(User user) {
		return ofy().load().type(Match.class)
				.filter("userHome", Ref.create(user)).list();
	}

	public List<Match> findByUserAway(User user) {
		return ofy().load().type(Match.class)
				.filter("userAway", Ref.create(user)).list();
	}
	
	public List<Match> findByUser(User user) {
		List<Match> listMatch = findByUserHome(user);
		listMatch.addAll(findByUserAway(user));
		Collections.sort(listMatch, Match.Comparators.DATE);
		return listMatch;
	}

	public List<Match> findByRound(int round) {
		return ofy().load().type(Match.class).filter("round", round).list();
	}

	public List<Match> getMatchsForPlay() {
		return ofy().load().type(Match.class)
				.filter("state", MatchParameters.getMATCHSTATE_WAIT())
				.filter("isFriendly", true).list();
	}
}

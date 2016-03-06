/**
 * 
 */
package org.javahispano.javaleague.server.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.shared.parameters.MatchParameters;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.googlecode.objectify.Ref;

/**
 * @author alfonso
 *
 */
public class MatchDao extends BaseDao<Match> {

	public MatchDao() {
		super(Match.class);
	}

	public List<Match> findByUserHome(User user, boolean isFriendly) {
		return ofy().load().type(Match.class)
				.filter("userHome", Ref.create(user))
				.filter("isFriendly", isFriendly).list();
	}

	public List<Match> findByUserAway(User user, boolean isFriendly) {
		return ofy().load().type(Match.class)
				.filter("userAway", Ref.create(user))
				.filter("isFriendly", isFriendly).list();
	}

	public List<Match> findByUser(User user, boolean isFriendly) {
		List<Match> listMatch = findByUserHome(user, isFriendly);
		listMatch.addAll(findByUserAway(user, isFriendly));
		Collections.sort(listMatch, Match.Comparators.DATE);
		return listMatch;
	}

	public List<Match> findByRound(int round) {
		return ofy().load().type(Match.class).filter("round", round).list();
	}

	public List<Match> getMatchsForPlay(Date date) {
		return ofy().load().type(Match.class)
				.filter("state", MatchParameters.getMATCHSTATE_WAIT())
				.filter("date <", date).list();
	}
}

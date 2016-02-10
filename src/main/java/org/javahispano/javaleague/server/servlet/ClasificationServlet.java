/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javahispano.javaleague.server.dao.ClasificationDao;
import org.javahispano.javaleague.server.dao.JourneyDao;
import org.javahispano.javaleague.server.dao.LeagueDao;
import org.javahispano.javaleague.server.dao.domain.Clasification;
import org.javahispano.javaleague.server.dao.domain.Journey;
import org.javahispano.javaleague.server.dao.domain.League;
import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.parameters.LeagueParameters;

import com.googlecode.objectify.Ref;

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
	private final LeagueDao leagueDao;
	private final JourneyDao journeyDao;
	private final ClasificationDao clasificationDao;

	@Inject
	ClasificationServlet(Logger logger, LeagueDao leagueDao,
			JourneyDao journeyDao, ClasificationDao clasificationDao) {
		this.logger = logger;
		this.leagueDao = leagueDao;
		this.journeyDao = journeyDao;
		this.clasificationDao = clasificationDao;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int round = Integer
				.parseInt(req.getParameter("round").replace("_", ""));
		League league = leagueDao.get(LeagueParameters.getLeagueId());

		if (round > 1) {
			Journey journeyAnt = journeyDao.findByRound(round - 1);
			Journey journey = journeyDao.findByRound(round);

			if (journey.getClasifications().size() > 0) {
				clasificationDao
						.delete(Deref.deref(journey.getClasifications()));
				journey.setClasification(new ArrayList<Ref<Clasification>>());
			}
			List<Clasification> list = new ArrayList<Clasification>();
			for (Ref<Clasification> c : journeyAnt.getClasifications()) {
				Clasification clasificationAnt = Deref.deref(c);
				Clasification clasification = new Clasification();
				clasification.setTeam(clasificationAnt.getTeam());
				clasification.setMatchs(clasificationAnt.getMatchs() + 1);
				Match m = getMatch(journey.getMatchs(), clasification.getTeam());
				if (m != null) {
					if (m.getUserAway().getId() == Deref.deref(
							clasification.getTeam()).getId()) {
						clasification.setGoalsAgainst(clasificationAnt
								.getGoalsAgainst()
								+ m.getProperties().getGoalsHome());
						clasification.setMyGoals(clasificationAnt.getMyGoals()
								+ m.getProperties().getGoalsAway());
						if (m.getProperties().getGoalsAway() > m
								.getProperties().getGoalsHome()) {
							clasification.setPoints(clasificationAnt
									.getPoints() + 3);
							clasification
									.setWins(clasificationAnt.getWins() + 1);
						} else if (m.getProperties().getGoalsAway() == m
								.getProperties().getGoalsHome()) {
							clasification.setPoints(clasificationAnt
									.getPoints() + 1);
							clasification
									.setTied(clasificationAnt.getTied() + 1);
						} else {
							clasification.setPoints(clasificationAnt
									.getPoints());
							clasification
									.setLost(clasificationAnt.getLost() + 1);
						}
					} else {
						clasification.setGoalsAgainst(clasificationAnt
								.getGoalsAgainst()
								+ m.getProperties().getGoalsAway());
						clasification.setMyGoals(clasificationAnt.getMyGoals()
								+ m.getProperties().getGoalsHome());
						if (m.getProperties().getGoalsHome() > m
								.getProperties().getGoalsAway()) {
							clasification.setPoints(clasificationAnt
									.getPoints() + 3);
							clasification
									.setWins(clasificationAnt.getWins() + 1);
						} else if (m.getProperties().getGoalsAway() == m
								.getProperties().getGoalsHome()) {
							clasification.setPoints(clasificationAnt
									.getPoints() + 1);
							clasification
									.setTied(clasificationAnt.getTied() + 1);
						} else {
							clasification.setPoints(clasificationAnt
									.getPoints());
							clasification
									.setLost(clasificationAnt.getLost() + 1);
						}
					}
				}
				clasificationDao.put(clasification);
				list.add(clasification);
			}
			Collections.sort(list, Clasification.Comparators.POINTS);
			List<Ref<Clasification>> listRef = new ArrayList<Ref<Clasification>>();
			for (Clasification c : list) {
				listRef.add(Ref.create(c));
			}
			journey.setClasification(listRef);
			journeyDao.put(journey);
		} else {
			Journey journey = journeyDao.findByRound(round);

			if (journey.getClasifications().size() > 0) {
				clasificationDao
						.delete(Deref.deref(journey.getClasifications()));
				journey.setClasification(new ArrayList<Ref<Clasification>>());
			}

			List<Clasification> list = new ArrayList<Clasification>();

			for (Ref<Match> m : journey.getMatchs()) {
				Match match = Deref.deref(m);
				Clasification c1 = new Clasification();
				Clasification c2 = new Clasification();
				c1.setTeam(Ref.create(match.getUserHome()));
				c2.setTeam(Ref.create(match.getUserAway()));
				c1.setMyGoals(match.getProperties().getGoalsHome());
				c1.setGoalsAgainst(match.getProperties().getGoalsAway());
				c2.setMyGoals(match.getProperties().getGoalsAway());
				c2.setGoalsAgainst(match.getProperties().getGoalsHome());
				c1.setMatchs(1);
				c2.setMatchs(1);
				if (match.getProperties().getGoalsHome() > match
						.getProperties().getGoalsAway()) {
					c1.setPoints(3);
					c1.setWins(1);
					c2.setPoints(0);
					c2.setLost(1);
				} else if (match.getProperties().getGoalsHome() < match
						.getProperties().getGoalsAway()) {
					c1.setPoints(0);
					c1.setLost(1);
					c2.setPoints(3);
					c2.setWins(1);
				} else {
					c1.setPoints(1);
					c1.setTied(1);
					c2.setPoints(1);
					c2.setTied(1);
				}

				clasificationDao.put(c1);
				clasificationDao.put(c2);
				list.add(c1);
				list.add(c2);
			}
			Collections.sort(list, Clasification.Comparators.POINTS);
			List<Ref<Clasification>> listRef = new ArrayList<Ref<Clasification>>();
			for (Clasification c : list) {
				listRef.add(Ref.create(c));
			}
			journey.setClasification(listRef);
			journeyDao.put(journey);
		}

		league.setRound(round);
		leagueDao.put(league);
	}

	private Match getMatch(List<Ref<Match>> listMatchs, Ref<User> refUser) {
		User user = Deref.deref(refUser);
		for (Ref<Match> m : listMatchs) {
			Match match = Deref.deref(m);

			if ((match.getUserAway().getId() == user.getId())
					|| (match.getUserHome().getId() == user.getId())) {
				return match;
			}
		}

		return null;
	}
}

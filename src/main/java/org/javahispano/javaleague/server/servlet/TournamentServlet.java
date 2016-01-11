/**
 * 
 */
package org.javahispano.javaleague.server.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
import org.javahispano.javaleague.server.dao.MatchDao;
import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.domain.Journey;
import org.javahispano.javaleague.server.dao.domain.League;
import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.server.dao.domain.User;

import com.googlecode.objectify.Ref;

/**
 * @author alfonso
 *
 */
public class TournamentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5084046081204808866L;

	private final Logger logger;
	private final LeagueDao leagueDao;
	private final ClasificationDao clasificationDao;
	private final JourneyDao journeyDao;
	private final UserDao userDao;
	private final MatchDao matchDao;

	@Inject
	TournamentServlet(Logger logger, LeagueDao leagueDao,
			ClasificationDao clasificationDao, JourneyDao journeyDao,
			UserDao userDao, MatchDao matchDao) {
		this.logger = logger;
		this.leagueDao = leagueDao;
		this.clasificationDao = clasificationDao;
		this.journeyDao = journeyDao;
		this.userDao = userDao;
		this.matchDao = matchDao;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");

		if (action.equals("add")) {
			League league = new League();
			league.setDescription("javaleague 2016");
			leagueDao.put(league);
			List<User> usersTacticOK = userDao.getUsersTacticOk();
			if (usersTacticOK != null) {
				createCalendarLeague(league, new Date(), usersTacticOK, 1);
			} else {
				logger.warning("TournamentServlet: add: No hay usuarios con táctica OK!");
			}
		}
	}

	public League createCalendarLeague(League league, Date init, List<User> users, int vueltas) {
		int n = users.size();
		int[][][] temp = crearLiguilla(n);

		int home, away;
		boolean swap;
		int partidos = n * (n - 1) / 2;
		int fechas = partidos / (n / 2);
		int partidosPorFecha = partidos / fechas;
		Match match;
		Date start = init;

		logger.warning("Equipos: " + n);
		logger.warning("Total partidos: " + partidos);
		logger.warning("Total fechas: " + fechas);
		logger.warning("Partidos por fecha: " + partidosPorFecha);

		league.setMatchs(partidos);
		league.setRoundMax(fechas);

		for (int f = 0; f < vueltas; f++) {
			if (f % 2 == 0) { // es par
				swap = true;
			} else {
				swap = false;
			}
			
			for (int round = 0; round < fechas; round++) {
				logger.warning("Fecha: " + round);
				
				Journey journey = new Journey();
				journey.setDate(start);
				journey.setRound(round + 1);

				for (int m = 0; m < partidosPorFecha; m++) {
					logger.warning("Fecha: " + round + " :: Partido: " + m);

					match = new Match();
					match.setFriendly(false);
					match.setDate(start);

					int found[] = new int[] { temp[round][m][0],
							temp[round][m][1] };

					if (swap) {
						home = found[1];
						away = found[0];
					} else {
						home = found[0];
						away = found[1];
					}

					logger.warning("Home: " + users.get(home).getTeamName());
					logger.warning("Away: " + users.get(away).getTeamName());

					match.setUserAway(users.get(away));
					match.setUserHome(users.get(home));
					match = matchDao.put(match);
					
					journey.getMatchs().add(Ref.create(match));
				}
				journeyDao.put(journey);
				league.getJourneys().add(Ref.create(journey));
				start = getNextDate(start, 1);
			}
		}

		league = leagueDao.put(league);

		return league;
	}

	/**
	 * Metodo practico para crear liguillas todos contra todos, se debe indicar
	 * 'n' como la cantidad de equipos. Retorna un array donde: el primer indice
	 * representa la fecha el segundo representa el partido de la fecha y el
	 * tercer indice 0: equipo local y 1: la visita
	 * 
	 * Nota: No son partidos de ida y vuelta
	 * 
	 * @param n
	 * @return
	 */
	public static int[][][] crearLiguilla(int n) {
		boolean impar = n % 2 == 1;
		if (impar) {
			n++;
		}
		int ppf = n / 2;// partidos por fecha
		int p = n * (n - 1) / 2;// partidos total
		int f = p / ppf;// fechas
		int tmp[][][] = new int[f][impar ? ppf - 1 : ppf][2];
		int subTmp[][] = new int[ppf][2];
		int[][] camino = new int[2 * ppf - 1][2];
		int x = 1;
		int y = 0;
		boolean avanza = true;
		for (int i = 0; i < camino.length; i++) {
			camino[i][0] = x;
			camino[i][1] = y;
			if (avanza) {
				x++;
				if (x >= ppf) {
					x--;
					y++;
					avanza = false;
				}
			} else {
				x--;
			}
		}
		for (int i = 0; i < f; i++) {
			for (int j = 0; j < ppf; j++) {
				subTmp[j][0] = -1;
				subTmp[j][1] = -1;
			}
			subTmp[0][0] = 0;
			for (int j = 0; j < camino.length; j++) {
				x = camino[j][0];
				y = camino[j][1];
				subTmp[x][y] = (j + i) % (n - 1) + 1;
			}
			int k = 0;
			for (int j = 0; j < ppf; j++) {
				if (!impar
						|| (impar && subTmp[j][0] != n - 1 && subTmp[j][1] != n - 1)) {
					if (((subTmp[j][0] + subTmp[j][1]) % 2) == 0) {
						int temp = subTmp[j][0];
						subTmp[j][0] = subTmp[j][1];
						subTmp[j][1] = temp;
					}
					int l = subTmp[j][0];
					int v = subTmp[j][1];
					boolean invertir = false;
					if (l >= v) {
						invertir = (l + v) % 2 == 0;
					} else {
						invertir = (l + v + 1) % 2 == 0;
					}
					if (invertir) {
						tmp[i][k][0] = v;
						tmp[i][k][1] = l;
					} else {
						tmp[i][k][0] = l;
						tmp[i][k][1] = v;
					}
					k++;
				}
			}
		}
		return tmp;
	}

	/**
	 * Agrega o quita minutos a una fecha dada. Para quitar minutos hay que
	 * sumarle valores negativos.
	 * 
	 * @param date
	 * @param minutes
	 * @return
	 */
	private static Date addMinutesToDate(Date date, int minutes) {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarDate.add(Calendar.MINUTE, minutes);
		return calendarDate.getTime();
	}

	private static Date getNextDate(Date date, int day) {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(date);
		calendarDate.add(Calendar.MINUTE, 1440 * day);
		return calendarDate.getTime();
	}

}

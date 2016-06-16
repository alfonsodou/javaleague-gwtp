/**
 * 
 */
package org.javahispano.javaleague.shared.parameters;


/**
 * @author alfonso
 *
 */
public class LeagueParameters {
	private static Long leagueId = 4590253763461120L;

	/**
	 * @return the leagueId
	 */
	public static Long getLeagueId() {
		/*LeagueDao leagueDao = new LeagueDao();
		List<League> league = leagueDao.getAll();*/
		
		return leagueId;
	}

}

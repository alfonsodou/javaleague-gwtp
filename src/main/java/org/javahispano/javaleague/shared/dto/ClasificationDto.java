/**
 * 
 */
package org.javahispano.javaleague.shared.dto;

/**
 * @author alfonso
 *
 */
public class ClasificationDto extends BaseEntity {
	private int points;
	private int myGoals;
	private int goalsAgainst;
	private int round;
	private UserDto team;
	private int matchs;
	private int wins;
	private int tied;
	private int lost;

	
	public ClasificationDto() {
		this.points = 0;
		this.myGoals = 0;
		this.goalsAgainst = 0;
		this.round = 0;
		this.team = null;
		this.matchs = 0;
		this.wins = 0;
		this.tied = 0;
		this.lost = 0;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the myGoals
	 */
	public int getMyGoals() {
		return myGoals;
	}

	/**
	 * @param myGoals the myGoals to set
	 */
	public void setMyGoals(int myGoals) {
		this.myGoals = myGoals;
	}

	/**
	 * @return the goalsAgainst
	 */
	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	/**
	 * @param goalsAgainst the goalsAgainst to set
	 */
	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	/**
	 * @return the round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * @param round the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 * @return the team
	 */
	public UserDto getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(UserDto team) {
		this.team = team;
	}

	/**
	 * @return the matchs
	 */
	public int getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(int matchs) {
		this.matchs = matchs;
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * @param wins the wins to set
	 */
	public void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 * @return the tied
	 */
	public int getTied() {
		return tied;
	}

	/**
	 * @param tied the tied to set
	 */
	public void setTied(int tied) {
		this.tied = tied;
	}

	/**
	 * @return the lost
	 */
	public int getLost() {
		return lost;
	}

	/**
	 * @param lost the lost to set
	 */
	public void setLost(int lost) {
		this.lost = lost;
	}

	
}

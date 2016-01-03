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
	
	public ClasificationDto() {
		this.points = 0;
		this.myGoals = 0;
		this.goalsAgainst = 0;
		this.round = 0;
		this.team = null;
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
	
}

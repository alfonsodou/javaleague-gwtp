/**
 * 
 */
package org.javahispano.javaleague.shared.dto;

import java.util.List;

/**
 * @author adou
 *
 */
public class LeagueDto extends BaseEntity {
	private String description;
	private int roundMax;
	private int round;
	
	private List<List<MatchDto>> matchs;
	
	public LeagueDto() {
		this.description = "";
	}

	public LeagueDto(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the roundMax
	 */
	public int getRoundMax() {
		return roundMax;
	}

	/**
	 * @param roundMax the roundMax to set
	 */
	public void setRoundMax(int roundMax) {
		this.roundMax = roundMax;
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
	 * @return the matchs
	 */
	public List<List<MatchDto>> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(List<List<MatchDto>> matchs) {
		this.matchs = matchs;
	}
	
	
}

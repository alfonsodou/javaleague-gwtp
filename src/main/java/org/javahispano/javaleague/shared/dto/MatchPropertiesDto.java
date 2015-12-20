/**
 * 
 */
package org.javahispano.javaleague.shared.dto;

/**
 * @author adou
 *
 */
public class MatchPropertiesDto extends BaseEntity {
	private int goalsHome;
	private int goalsAway;
	private double posessionHome;
	private int round;
	private String result;
	
	public MatchPropertiesDto() {
		this.goalsHome = 0;
		this.goalsAway = 0;
		this.posessionHome = 0;
		this.round = 0;
		this.result = "";
	}

	public int getGoalsHome() {
		return goalsHome;
	}

	public void setGoalsHome(int goalsHome) {
		this.goalsHome = goalsHome;
	}

	public int getGoalsAway() {
		return goalsAway;
	}

	public void setGoalsAway(int goalsAway) {
		this.goalsAway = goalsAway;
	}

	public double getPosessionHome() {
		return posessionHome;
	}

	public void setPosessionHome(double posessionHome) {
		this.posessionHome = posessionHome;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	
}

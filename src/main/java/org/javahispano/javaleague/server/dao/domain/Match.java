/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import java.util.Date;

import org.javahispano.javaleague.shared.dto.BaseEntity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * @author alfonso
 *
 */

@Index
@Entity
public class Match extends BaseEntity {
	private Long userIdHome;
	private Long userIdAway;
	private boolean isFriendly;
	private Date date;
	private int goalsHome;
	private int goalsAway;
	private double posessionHome;
	private int round;
	private String teamNameHome;
	private String teamNameAway;
	private String userNameHome;
	private String userNameAway;
	
	public Match() {
		
	}

	/**
	 * @return the userIdHome
	 */
	public Long getUserIdHome() {
		return userIdHome;
	}

	/**
	 * @param userIdHome the userIdHome to set
	 */
	public void setUserIdHome(Long userIdHome) {
		this.userIdHome = userIdHome;
	}

	/**
	 * @return the userIdAway
	 */
	public Long getUserIdAway() {
		return userIdAway;
	}

	/**
	 * @param userIdAway the userIdAway to set
	 */
	public void setUserIdAway(Long userIdAway) {
		this.userIdAway = userIdAway;
	}

	/**
	 * @return the isFriendly
	 */
	public boolean isFriendly() {
		return isFriendly;
	}

	/**
	 * @param isFriendly the isFriendly to set
	 */
	public void setFriendly(boolean isFriendly) {
		this.isFriendly = isFriendly;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the goalsHome
	 */
	public int getGoalsHome() {
		return goalsHome;
	}

	/**
	 * @param goalsHome the goalsHome to set
	 */
	public void setGoalsHome(int goalsHome) {
		this.goalsHome = goalsHome;
	}

	/**
	 * @return the goalsAway
	 */
	public int getGoalsAway() {
		return goalsAway;
	}

	/**
	 * @param goalsAway the goalsAway to set
	 */
	public void setGoalsAway(int goalsAway) {
		this.goalsAway = goalsAway;
	}

	/**
	 * @return the posessionHome
	 */
	public double getPosessionHome() {
		return posessionHome;
	}

	/**
	 * @param posessionHome the posessionHome to set
	 */
	public void setPosessionHome(double posessionHome) {
		this.posessionHome = posessionHome;
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

	public String getTeamNameHome() {
		return teamNameHome;
	}

	public void setTeamNameHome(String teamNameHome) {
		this.teamNameHome = teamNameHome;
	}

	public String getTeamNameAway() {
		return teamNameAway;
	}

	public void setTeamNameAway(String teamNameAway) {
		this.teamNameAway = teamNameAway;
	}

	public String getUserNameHome() {
		return userNameHome;
	}

	public void setUserNameHome(String userNameHome) {
		this.userNameHome = userNameHome;
	}

	public String getUserNameAway() {
		return userNameAway;
	}

	public void setUserNameAway(String userNameAway) {
		this.userNameAway = userNameAway;
	}

}

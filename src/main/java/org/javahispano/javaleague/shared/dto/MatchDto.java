/**
 * 
 */
package org.javahispano.javaleague.shared.dto;

import java.util.Date;

import org.javahispano.javaleague.shared.parameters.MatchParameters;

/**
 * @author alfonso
 *
 */
public class MatchDto extends BaseEntity {
	private boolean isFriendly;
	private Date date;
	private Integer state;
	private UserDto userHome;
	private UserDto userAway;
	private MatchPropertiesDto matchPropertiesDto;
	
	public MatchDto() {
		this.isFriendly = false;
		this.date = new Date();
		this.state = MatchParameters.getMATCHSTATE_WAIT();
	}
	
	public MatchDto(boolean isFriendly, Date date) {
		this.isFriendly = isFriendly;
		this.date = date;
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
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	public UserDto getUserHome() {
		return userHome;
	}

	public void setUserHome(UserDto userHome) {
		this.userHome = userHome;
	}

	public UserDto getUserAway() {
		return userAway;
	}

	public void setUserAway(UserDto userAway) {
		this.userAway = userAway;
	}

	public MatchPropertiesDto getMatchPropertiesDto() {
		return matchPropertiesDto;
	}

	public void setMatchPropertiesDto(MatchPropertiesDto matchPropertiesDto) {
		this.matchPropertiesDto = matchPropertiesDto;
	}

	
}

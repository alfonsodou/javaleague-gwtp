/**
 * 
 */
package org.javahispano.javaleague.shared.dto;

import java.util.Date;

/**
 * @author alfonso
 *
 */
public class MatchDto extends BaseEntity {
	private boolean isFriendly;
	private Date date;
	
	public MatchDto() {
		this.isFriendly = false;
		this.date = new Date();
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

	
}

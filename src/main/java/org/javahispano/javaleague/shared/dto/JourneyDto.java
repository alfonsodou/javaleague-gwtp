/**
 * 
 */
package org.javahispano.javaleague.shared.dto;

import java.util.Date;
import java.util.List;

/**
 * @author alfonso
 *
 */
public class JourneyDto extends BaseEntity {
	private int round;
	private List<MatchDto> matchs;
	private List<ClasificationDto> clasifications;
	private Date date;
	
	public JourneyDto() {
		this.round = 0;
		this.matchs = null;
		this.clasifications = null;
		this.date = new Date();
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
	public List<MatchDto> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(List<MatchDto> matchs) {
		this.matchs = matchs;
	}

	/**
	 * @return the clasification
	 */
	public List<ClasificationDto> getClasifications() {
		return clasifications;
	}

	/**
	 * @param clasification the clasification to set
	 */
	public void setClasification(List<ClasificationDto> clasifications) {
		this.clasifications = clasifications;
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

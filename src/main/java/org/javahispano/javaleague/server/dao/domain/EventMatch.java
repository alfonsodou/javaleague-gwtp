/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import org.javahispano.javaleague.shared.dto.BaseEntity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * @author alfonso
 *
 */
@Index
@Entity
public class EventMatch extends BaseEntity {
	private Long idMatch;
	private int timeStamp;

	public EventMatch() {
		
	}

	/**
	 * @return the idMatch
	 */
	public Long getIdMatch() {
		return idMatch;
	}

	/**
	 * @param idMatch the idMatch to set
	 */
	public void setIdMatch(Long idMatch) {
		this.idMatch = idMatch;
	}

	/**
	 * @return the timeStamp
	 */
	public int getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}

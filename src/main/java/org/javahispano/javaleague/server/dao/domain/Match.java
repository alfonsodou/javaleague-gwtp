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
	
	public Match() {
		
	}

}

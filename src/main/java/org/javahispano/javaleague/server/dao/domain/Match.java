/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import java.util.Comparator;
import java.util.Date;

import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dto.BaseEntity;
import org.javahispano.javaleague.shared.parameters.MatchParameters;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

/**
 * @author alfonso
 *
 */

@Index
@Entity
public class Match extends BaseEntity implements Comparable<Match> {
	@Load
	private Ref<League> league;
	@Load
	private Ref<User> userHome;
	@Load
	private Ref<User> userAway;
	private boolean isFriendly;
	private Date date;
	@Load
	private Ref<MatchProperties> properties;
	private Integer state;

	public Match() {
		this.isFriendly = false;
		this.date = new Date();
		this.league = null;
		this.userAway = null;
		this.userHome = null;
		this.properties = null;
		this.state = MatchParameters.getMATCHSTATE_WAIT();
	}

	public Match(boolean isFriendly, Date date) {
		this.isFriendly = isFriendly;
		this.date = date;
		this.userAway = null;
		this.userHome = null;
		this.properties = null;
	}

	public League getLeague() {
		return Deref.deref(league);
	}

	public void setLeague(League league) {
		if (league != null) {
			this.league = Ref.create(league);
		} else {
			this.league = null;
		}
	}
	
	public MatchProperties getProperties() {
		return Deref.deref(properties);
	}

	public void setProperties(MatchProperties matchProperties) {
		if (matchProperties != null) {
			this.properties = Ref.create(matchProperties);
		} else {
			this.properties = null;
		}
	}
	
	public User getUserHome() {
		return Deref.deref(userHome);
	}

	public void setUserHome(User userHome) {
		if (userHome != null) {
			this.userHome = Ref.create(userHome);
		} else {
			this.userHome = null;
		}
	}

	public User getUserAway() {
		return Deref.deref(userAway);
	}

	public void setUserAway(User userAway) {
		if (userAway != null) {
			this.userAway = Ref.create(userAway);
		} else {
			this.userAway = null;
		}
	}

	public boolean isFriendly() {
		return isFriendly;
	}

	public void setFriendly(boolean isFriendly) {
		this.isFriendly = isFriendly;
	}

	public Date getDate() {
		return date;
	}

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

	@Override
	public int compareTo(Match o) {
		return Comparators.DATE.compare(this, o);
	}
	
	public static class Comparators {
		public static Comparator<Match> DATE = new Comparator<Match>() {
			@Override
			public int compare(Match m1, Match m2) {
				return m1.getDate().compareTo(m2.getDate());
			}
		};
	}

}

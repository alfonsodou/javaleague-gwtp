/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import java.util.Date;

import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dto.BaseEntity;

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
public class RegisterMatch extends BaseEntity {
	@Load
	private Ref<User> user;
	private Date date;
	private Ref<Match> match;

	public RegisterMatch() {

	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return Deref.deref(user);
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		if (user != null) {
			this.user = Ref.create(user);
		} else {
			this.user = null;
		}
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the match
	 */
	public Match getMatch() {
		return Deref.deref(match);
	}

	/**
	 * @param match
	 *            the match to set
	 */
	public void setMatch(Match match) {
		if (match != null) {
			this.match = Ref.create(match);
		} else {
			this.match = null;
		}
	}
}

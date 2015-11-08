/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

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
public class UserProperties extends BaseEntity {
	@Load
	private Ref<User> user;

	private int numGoalsScored;
	private int numGoalsAgainst;
	private int numMatchsWins;
	private int numMatchsLost;
	private int numMatchsTied;
	private double possession;

	public UserProperties() {
		this.numGoalsAgainst = 0;
		this.numGoalsScored = 0;
		this.numMatchsLost = 0;
		this.numMatchsTied = 0;
		this.numMatchsWins = 0;
		this.possession = 0d;
	}

	public UserProperties(int numGoalsAgainst, int numGoalsScored,
			int numMatchsWins, int numMatchsLost, int numMatchsTied,
			double possession) {
		this.numGoalsAgainst = numGoalsAgainst;
		this.numGoalsScored = numGoalsScored;
		this.numMatchsLost = numMatchsLost;
		this.numMatchsTied = numMatchsTied;
		this.numMatchsWins = numMatchsWins;
		this.possession = possession;
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
	 * @return the numGoalsScored
	 */
	public int getNumGoalsScored() {
		return numGoalsScored;
	}

	/**
	 * @param numGoalsScored
	 *            the numGoalsScored to set
	 */
	public void setNumGoalsScored(int numGoalsScored) {
		this.numGoalsScored = numGoalsScored;
	}

	/**
	 * @return the numGoalsAgainst
	 */
	public int getNumGoalsAgainst() {
		return numGoalsAgainst;
	}

	/**
	 * @param numGoalsAgainst
	 *            the numGoalsAgainst to set
	 */
	public void setNumGoalsAgainst(int numGoalsAgainst) {
		this.numGoalsAgainst = numGoalsAgainst;
	}

	/**
	 * @return the numMatchsWins
	 */
	public int getNumMatchsWins() {
		return numMatchsWins;
	}

	/**
	 * @param numMatchsWins
	 *            the numMatchsWins to set
	 */
	public void setNumMatchsWins(int numMatchsWins) {
		this.numMatchsWins = numMatchsWins;
	}

	/**
	 * @return the numMatchsLost
	 */
	public int getNumMatchsLost() {
		return numMatchsLost;
	}

	/**
	 * @param numMatchsLost
	 *            the numMatchsLost to set
	 */
	public void setNumMatchsLost(int numMatchsLost) {
		this.numMatchsLost = numMatchsLost;
	}

	/**
	 * @return the numMatchsTied
	 */
	public int getNumMatchsTied() {
		return numMatchsTied;
	}

	/**
	 * @param numMatchsTied
	 *            the numMatchsTied to set
	 */
	public void setNumMatchsTied(int numMatchsTied) {
		this.numMatchsTied = numMatchsTied;
	}

	/**
	 * @return the possession
	 */
	public double getPossession() {
		return possession;
	}

	/**
	 * @param possession
	 *            the possession to set
	 */
	public void setPossession(double possession) {
		this.possession = possession;
	}

}

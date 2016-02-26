/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import java.util.Comparator;

import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dto.BaseEntity;
import org.javahispano.javaleague.shared.dto.ClasificationDto;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

/**
 * @author adou
 *
 */
@Index
@Entity
public class Clasification extends BaseEntity implements
		Comparable<Clasification> {
	private int points;
	private int myGoals;
	private int goalsAgainst;
	private int round;
	private int matchs;
	private int wins;
	private int tied;
	private int lost;
	private int position;

	@Load
	private Ref<User> team;

	public Clasification() {
		this.points = 0;
		this.myGoals = 0;
		this.goalsAgainst = 0;
		this.round = 0;
		this.matchs = 0;
		this.wins = 0;
		this.tied = 0;
		this.lost = 0;
		this.position = 0;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getMyGoals() {
		return myGoals;
	}

	public void setMyGoals(int myGoals) {
		this.myGoals = myGoals;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public Ref<User> getTeam() {
		return team;
	}

	public void setTeam(Ref<User> team) {
		this.team = team;
	}
	
	/**
	 * @return the matchs
	 */
	public int getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(int matchs) {
		this.matchs = matchs;
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * @param wins the wins to set
	 */
	public void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 * @return the tied
	 */
	public int getTied() {
		return tied;
	}

	/**
	 * @param tied the tied to set
	 */
	public void setTied(int tied) {
		this.tied = tied;
	}

	/**
	 * @return the lost
	 */
	public int getLost() {
		return lost;
	}

	/**
	 * @param lost the lost to set
	 */
	public void setLost(int lost) {
		this.lost = lost;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public static ClasificationDto createDto(Clasification clasification) {
		if (clasification == null) {
			return null;
		}

		ClasificationDto clasificationDto = new ClasificationDto();
		clasificationDto.setGoalsAgainst(clasification.getGoalsAgainst());
		clasificationDto.setMyGoals(clasification.getMyGoals());
		clasificationDto.setPoints(clasification.getPoints());
		clasificationDto.setRound(clasification.getRound());
		clasificationDto.setTeam(User.createDto(Deref.deref(clasification
				.getTeam())));
		clasificationDto.setMatchs(clasification.getMatchs());
		clasificationDto.setWins(clasification.getWins());
		clasificationDto.setTied(clasification.getTied());
		clasificationDto.setLost(clasification.getLost());
		clasificationDto.setPosition(clasification.getPosition());

		return clasificationDto;
	}

	@Override
	public int compareTo(Clasification c) {
		return Comparators.POINTS.compare(this, c);
	}

	public static class Comparators {
		public static Comparator<Clasification> ROUND = new Comparator<Clasification>() {
			@Override
			public int compare(Clasification c1, Clasification c2) {
				return c1.getRound() - c2.getRound();
			}
		};

		public static Comparator<Clasification> POINTS = new Comparator<Clasification>() {
			@Override
			public int compare(Clasification c1, Clasification c2) {
				if (c1.getPoints() > c2.getPoints())
					return -1;
				else if (c1.getPoints() < c2.getPoints()) 
					return 1;
				else {
					return 0;
				}
			}
		};

	}

}

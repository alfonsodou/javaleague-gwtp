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

	@Load
	private Ref<User> team;

	public Clasification() {
		this.points = 0;
		this.myGoals = 0;
		this.goalsAgainst = 0;
		this.round = 0;
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

		return clasificationDto;
	}

	@Override
	public int compareTo(Clasification c) {
		return Comparators.ROUND.compare(this, c);
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
				return c1.getPoints() - c2.getPoints();
			}
		};

	}

}

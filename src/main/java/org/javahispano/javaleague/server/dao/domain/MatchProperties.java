/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dto.BaseEntity;
import org.javahispano.javaleague.shared.dto.MatchPropertiesDto;

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
public class MatchProperties extends BaseEntity {
	private int goalsHome;
	private int goalsAway;
	private double posessionHome;
	private int round;
	private String result;

	@Load
	private Ref<Match> match;

	public MatchProperties() {
		this.goalsAway = 0;
		this.goalsHome = 0;
		this.posessionHome = 0d;
		this.round = 0;
		this.result = "";
	}

	public MatchProperties(int goalsHome, int goalsAway, double posessionHome,
			int round, String result) {
		this.goalsHome = goalsHome;
		this.goalsAway = goalsAway;
		this.posessionHome = posessionHome;
		this.round = round;
		this.result = result;
	}

	public int getGoalsHome() {
		return goalsHome;
	}

	public void setGoalsHome(int goalsHome) {
		this.goalsHome = goalsHome;
	}

	public int getGoalsAway() {
		return goalsAway;
	}

	public void setGoalsAway(int goalsAway) {
		this.goalsAway = goalsAway;
	}

	public double getPosessionHome() {
		return posessionHome;
	}

	public void setPosessionHome(double posessionHome) {
		this.posessionHome = posessionHome;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Match getMatch() {
		return Deref.deref(match);
	}

	public void setMatch(Match match) {
		if (match != null) {
			this.match = Ref.create(match);
		} else {
			this.match = null;
		}
	}

	public static MatchPropertiesDto createDto(MatchProperties matchProperties) {
		if (matchProperties == null) {
			return null;
		}
		
		MatchPropertiesDto matchPropertiesDto = new MatchPropertiesDto();
		matchPropertiesDto.setId(matchProperties.getId());
		matchPropertiesDto.setGoalsAway(matchProperties.getGoalsAway());
		matchPropertiesDto.setGoalsHome(matchProperties.getGoalsHome());
		matchPropertiesDto.setPosessionHome(matchProperties.getPosessionHome());
		matchPropertiesDto.setResult(matchProperties.getResult());
		matchPropertiesDto.setRound(matchProperties.getRound());
		
		return matchPropertiesDto;
	}
}

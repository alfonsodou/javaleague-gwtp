/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import java.util.ArrayList;
import java.util.List;

import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dto.BaseEntity;
import org.javahispano.javaleague.shared.dto.JourneyDto;
import org.javahispano.javaleague.shared.dto.LeagueDto;
import org.javahispano.javaleague.shared.dto.MatchDto;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
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
public class League extends BaseEntity {
	private String description;
	private int roundMax;
	private int round;
	@Load
	private List<Ref<Journey>> journeys;
	private int matchs;
	
	public League() {
		this.description = "";
		this.roundMax = 0;
		this.round = 0;
		this.journeys = null;
		this.matchs = 0;
	}

	public League(String description, int roundMax, int round) {
		this.description = description;
		this.roundMax = roundMax;
		this.round = round;
		this.journeys = null;
		this.matchs = 0;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the roundMax
	 */
	public int getRoundMax() {
		return roundMax;
	}

	/**
	 * @param roundMax
	 *            the roundMax to set
	 */
	public void setRoundMax(int roundMax) {
		this.roundMax = roundMax;
	}

	/**
	 * @return the round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * @param round
	 *            the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 * @return the journeys
	 */
	public List<Ref<Journey>> getJourneys() {
		return journeys;
	}

	/**
	 * @param journeys
	 *            the journeys to set
	 */
	public void setJourneys(List<Ref<Journey>> journeys) {
		this.journeys = journeys;
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

	public static LeagueDto createDto(League league) {
		if (league == null) {
			return null;
		}

		LeagueDto leagueDto = new LeagueDto();
		leagueDto.setId(league.getId());
		leagueDto.setRoundMax(league.getRoundMax());
		leagueDto.setRound(league.getRound());
		List<Journey> journeys = Deref.deref(league.getJourneys());
		List<JourneyDto> journeyDtoList = new ArrayList<JourneyDto>();
		for (Journey journey : journeys) {
			JourneyDto journeyDto = Journey.createDto(journey);
			journeyDtoList.add(journeyDto);
		}
		leagueDto.setJourneys(journeyDtoList);

		return leagueDto;
	}

}

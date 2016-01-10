/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dto.BaseEntity;
import org.javahispano.javaleague.shared.dto.ClasificationDto;
import org.javahispano.javaleague.shared.dto.JourneyDto;
import org.javahispano.javaleague.shared.dto.MatchDto;

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
public class Journey extends BaseEntity {
	private int round;
	@Load
	private List<Ref<Match>> matchs;
	@Load
	private List<Ref<Clasification>> clasifications;
	private Date date;

	public Journey() {
		this.round = 0;
		this.date = new Date();
		this.matchs = null;
		this.clasifications = null;
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
	 * @return the matchs
	 */
	public List<Ref<Match>> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs
	 *            the matchs to set
	 */
	public void setMatchs(List<Ref<Match>> matchs) {
		this.matchs = matchs;
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
	 * @return the clasification
	 */
	public List<Ref<Clasification>> getClasifications() {
		return clasifications;
	}

	/**
	 * @param clasification
	 *            the clasification to set
	 */
	public void setClasification(List<Ref<Clasification>> clasifications) {
		this.clasifications = clasifications;
	}

	public static JourneyDto createDto(Journey journey) {
		if (journey == null) {
			return null;
		}

		JourneyDto journeyDto = new JourneyDto();
		List<Clasification> clasifications = Deref.deref(journey
				.getClasifications());
		List<ClasificationDto> clasificationsDto = new ArrayList<ClasificationDto>();
		for (Clasification clasification : clasifications) {
			clasificationsDto.add(Clasification.createDto(clasification));
		}
		journeyDto.setClasification(clasificationsDto);
		journeyDto.setDate(journey.getDate());
		journeyDto.setRound(journey.getRound());
		List<Match> matchs = Deref.deref(journey.getMatchs());
		List<MatchDto> matchsDto = new ArrayList<MatchDto>();
		for (Match match : matchs) {
			matchsDto.add(Match.createDto(match));
		}
		journeyDto.setMatchs(matchsDto);

		return journeyDto;
	}
}

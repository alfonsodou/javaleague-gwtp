/**
 * 
 */
package org.javahispano.javaleague.server.dao.domain;

import java.util.ArrayList;
import java.util.List;

import org.javahispano.javaleague.server.dao.objectify.Deref;
import org.javahispano.javaleague.shared.dto.BaseEntity;
import org.javahispano.javaleague.shared.dto.FinalMatchDto;
import org.javahispano.javaleague.shared.dto.FinalMatchType;

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
public class FinalMatch extends BaseEntity {
	@Load
	private Ref<Match> match;
	private FinalMatchType type;
	private Integer order;

	public FinalMatch() {
		this.match = null;
	}

	public FinalMatch(Match m, FinalMatchType type, Integer order) {
		if (m != null) {
			this.match = Ref.create(m);
		} else {
			this.match = null;
		}
		this.type = type;
		this.order = order;
	}

	/**
	 * @return the match
	 */
	public Ref<Match> getMatch() {
		return match;
	}

	/**
	 * @param match
	 *            the match to set
	 */
	public void setMatch(Ref<Match> match) {
		this.match = match;
	}

	/**
	 * @return the type
	 */
	public FinalMatchType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(FinalMatchType type) {
		this.type = type;
	}

	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	public static FinalMatchDto createDto(FinalMatch finalMatch) {
		if (finalMatch == null) {
			return null;
		}

		FinalMatchDto finalMatchDto = new FinalMatchDto();
		finalMatchDto.setId(finalMatch.getId());
		finalMatchDto.setMatchDto(Match.createDto(Deref.deref(finalMatch
				.getMatch())));
		finalMatchDto.setFinalMatchType(finalMatch.getType());
		finalMatchDto.setOrder(finalMatch.getOrder());
		
		return finalMatchDto;
	}
	
	public static List<FinalMatchDto> createList(List<FinalMatch> listFinalMatch) {
		List<FinalMatchDto> listFinalMatchDto = new ArrayList<FinalMatchDto>();
		for(FinalMatch finalMatch : listFinalMatch) {
			FinalMatchDto finalMatchDto = FinalMatch.createDto(finalMatch);
			listFinalMatchDto.add(finalMatchDto);
		}
		return listFinalMatchDto;
	}
}

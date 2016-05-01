/**
 * 
 */
package org.javahispano.javaleague.shared.dto;

import org.javahispano.javaleague.server.dao.domain.FinalMatchType;

/**
 * @author alfonso
 *
 */
public class FinalMatchDto extends BaseEntity {
	private MatchDto matchDto;
	private FinalMatchType finalMatchType;
	private Integer order;

	public FinalMatchDto() {
		this.order = 0;
	}

	public FinalMatchDto(MatchDto matchDto, FinalMatchType finalMatchType,
			Integer order) {
		this.matchDto = matchDto;
		this.finalMatchType = finalMatchType;
		this.order = order;
	}

	/**
	 * @return the matchDto
	 */
	public MatchDto getMatchDto() {
		return matchDto;
	}

	/**
	 * @param matchDto
	 *            the matchDto to set
	 */
	public void setMatchDto(MatchDto matchDto) {
		this.matchDto = matchDto;
	}

	/**
	 * @return the finalMatchType
	 */
	public FinalMatchType getFinalMatchType() {
		return finalMatchType;
	}

	/**
	 * @param finalMatchType
	 *            the finalMatchType to set
	 */
	public void setFinalMatchType(FinalMatchType finalMatchType) {
		this.finalMatchType = finalMatchType;
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

}

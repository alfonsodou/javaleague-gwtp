/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.LeagueDao;
import org.javahispano.javaleague.server.dao.domain.League;
import org.javahispano.javaleague.shared.dto.LeagueDto;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author alfonso
 *
 */
public class ListMatchLeagueAction extends ActionImpl<ListMatchLeagueResult> {
	private Long leagueId;
	private LeagueDto leagueDto;
	private LeagueDao leagueDao = ;

	protected
	ListMatchLeagueAction() {
		this.leagueDao = leagueDao;
		this.leagueDto = null;
		this.leagueId = 0L;
	}
	
	public ListMatchLeagueAction(Long leagueId) {
		
	}

	/**
	 * @return the leagueDto
	 */
	public LeagueDto getLeagueDto() {
		if (leagueDto == null) {
			if (leagueId != 0L) {
				this.leagueDto = League.createDto(leagueDao.get(leagueId));
				return leagueDto;
			} else {
				return null;
			}
		} else {
			return this.leagueDto;
		}

	}

	/**
	 * @param leagueDto
	 *            the leagueDto to set
	 */
	public void setLeagueDto(LeagueDto leagueDto) {
		this.leagueDto = leagueDto;
	}

	/**
	 * @return the leagueId
	 */
	public Long getLeagueId() {
		return leagueId;
	}

	/**
	 * @param leagueId
	 *            the leagueId to set
	 */
	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}

	@Override
	public boolean isSecured() {
		return false;
	}
}

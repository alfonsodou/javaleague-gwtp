/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import org.javahispano.javaleague.shared.dto.LeagueDto;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author alfonso
 *
 */
public class ListMatchLeagueAction extends ActionImpl<ListMatchLeagueResult> {
	private Long leagueId;
	private LeagueDto leagueDto;
	
	protected ListMatchLeagueAction() {
		
	}
	
	public ListMatchLeagueAction(Long leagueId) {
		this.leagueId = leagueId;
	}
	
	/**
	 * @return the leagueDto
	 */
	public LeagueDto getLeagueDto() {
		return leagueDto;
	}

	/**
	 * @param leagueDto the leagueDto to set
	 */
	public void setLeagueDto(LeagueDto leagueDto) {
		this.leagueDto = leagueDto;
	}

	@Override
	public boolean isSecured() {
		return false;
	}
}

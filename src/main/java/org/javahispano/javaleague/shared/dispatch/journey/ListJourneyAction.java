/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.journey;

import java.util.Date;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author adou
 *
 */
public class ListJourneyAction extends ActionImpl<ListJourneyResult> {
	private Long leagueId;
	
	protected ListJourneyAction() {
		
	}
	
	public ListJourneyAction(Long leagueId) {
		this.leagueId = leagueId;
	}

	public Long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}
	
	@Override
	public boolean isSecured() {
		return false;
	}

}

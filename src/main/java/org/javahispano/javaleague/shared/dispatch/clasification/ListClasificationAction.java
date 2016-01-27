/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.clasification;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author adou
 *
 */
public class ListClasificationAction extends ActionImpl<ListClasificationResult> {
	private Long leagueId;
	
	protected ListClasificationAction() {
		
	}

	public ListClasificationAction(Long leagueId) {
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

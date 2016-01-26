/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author alfonso
 *
 */
public class ListMatchLeagueAction extends ActionImpl<ListMatchLeagueResult> {
	private Long leagueId;


	protected
	ListMatchLeagueAction() {
	}
	
	public ListMatchLeagueAction(Long leagueId) {
		this.leagueId = leagueId;
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

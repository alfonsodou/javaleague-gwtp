/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import java.util.Date;
import java.util.List;

import org.javahispano.javaleague.shared.dto.MatchDto;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author alfonso
 *
 */
public class ListMatchLeagueResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5959006921745163781L;

	private List<MatchDto> matchs;
	private Date serverDate;
	
	protected ListMatchLeagueResult() {
		
	}
	
	public ListMatchLeagueResult(List<MatchDto> matchs, Date serverDate) {
		this.matchs = matchs;
		this.serverDate = serverDate;
	}

	/**
	 * @return the matchs
	 */
	public List<MatchDto> getMatchs() {
		return matchs;
	}

	/**
	 * @param matchs the matchs to set
	 */
	public void setMatchs(List<MatchDto> matchs) {
		this.matchs = matchs;
	}

	public Date getServerDate() {
		return serverDate;
	}

	public void setServerDate(Date serverDate) {
		this.serverDate = serverDate;
	}
}

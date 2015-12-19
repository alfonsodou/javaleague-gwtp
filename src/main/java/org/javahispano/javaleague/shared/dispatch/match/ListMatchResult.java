/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import java.util.List;

import org.javahispano.javaleague.shared.dto.MatchDto;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author alfonso
 *
 */
public class ListMatchResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6525432800065446229L;
	private List<MatchDto> matchs;
	
	protected ListMatchResult() {
		
	}

	public ListMatchResult(List<MatchDto> matchs) {
		this.matchs = matchs;
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
}

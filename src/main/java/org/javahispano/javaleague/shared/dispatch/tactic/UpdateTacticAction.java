/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.tactic;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author alfonso
 *
 */
public class UpdateTacticAction extends ActionImpl<UpdateTacticResult> {
	private String teamName;
	
	protected UpdateTacticAction() {
		
	}
	
	public UpdateTacticAction(String teamName) {
		this.teamName = teamName;
	}

	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}

	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
}

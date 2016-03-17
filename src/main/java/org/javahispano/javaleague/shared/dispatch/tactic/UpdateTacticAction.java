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
	private Long id;
	private String teamName;
	
	protected UpdateTacticAction() {
		
	}
	
	public UpdateTacticAction(Long id, String teamName) {
		this.id = id;
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

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean isSecured() {
		return false;
	}
}

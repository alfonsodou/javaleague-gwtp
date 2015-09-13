/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.tactic;

import org.javahispano.javaleague.shared.dto.CurrentUserDto;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author alfonso
 *
 */
public class UpdateTacticResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8457840932729350020L;
	private CurrentUserDto currentUserDto;
	
	protected UpdateTacticResult() {
		
	}

	public UpdateTacticResult(CurrentUserDto currentUserDto) {
		this.currentUserDto = currentUserDto;
	}

	/**
	 * @return the currentUserDto
	 */
	public CurrentUserDto getCurrentUserDto() {
		return currentUserDto;
	}

	/**
	 * @param currentUserDto the currentUserDto to set
	 */
	public void setCurrentUserDto(CurrentUserDto currentUserDto) {
		this.currentUserDto = currentUserDto;
	}
	
}

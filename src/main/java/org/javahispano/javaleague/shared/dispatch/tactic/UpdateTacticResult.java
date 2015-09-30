/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.tactic;

import org.javahispano.javaleague.shared.dto.UserDto;

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
	private UserDto userDto;
	
	protected UpdateTacticResult() {
		
	}

	public UpdateTacticResult(UserDto userDto) {
		this.userDto = userDto;
	}

	/**
	 * @return the userDto
	 */
	public UserDto getUserDto() {
		return userDto;
	}

	/**
	 * @param userDto the userDto to set
	 */
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}


}

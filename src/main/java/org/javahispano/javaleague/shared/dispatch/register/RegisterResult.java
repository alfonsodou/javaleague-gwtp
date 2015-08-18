/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.register;

import org.javahispano.javaleague.shared.dto.UserDto;

import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * @author alfonso
 *
 */
public class RegisterResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1719583673287215760L;
	private UserDto userDto;
	
	protected RegisterResult() {
		
	}
	
	public RegisterResult(UserDto userDto) {
		this.userDto = userDto;
	}
	
	public UserDto getUserDto() {
		return userDto;
	}
}

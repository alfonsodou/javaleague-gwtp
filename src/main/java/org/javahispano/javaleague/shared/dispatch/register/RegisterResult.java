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
	private boolean status;
	
	protected RegisterResult() {
		
	}
	
	public RegisterResult(UserDto userDto) {
		this.userDto = userDto;
	}
	
	public RegisterResult(UserDto userDto, boolean status) {
		this.userDto = userDto;
		this.status = status;
	}
	
	public UserDto getUserDto() {
		return userDto;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}

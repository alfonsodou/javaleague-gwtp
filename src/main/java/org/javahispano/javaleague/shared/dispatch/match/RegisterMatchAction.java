/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import org.javahispano.javaleague.shared.dto.UserDto;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author adou
 *
 */
public class RegisterMatchAction extends ActionImpl<RegisterMatchResult> {
	private UserDto userDto;

	protected RegisterMatchAction() {

	}

	public RegisterMatchAction(UserDto userDto) {
		this.userDto = userDto;
	}
	
	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	@Override
	public boolean isSecured() {
		return false;
	}
}

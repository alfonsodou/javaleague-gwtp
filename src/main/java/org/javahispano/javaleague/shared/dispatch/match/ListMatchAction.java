/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.match;

import org.javahispano.javaleague.shared.dto.UserDto;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author alfonso
 *
 */
public class ListMatchAction extends ActionImpl<ListMatchResult> {
	private UserDto userDto;
	
	protected ListMatchAction() {
		
	}
	
	public ListMatchAction(UserDto userDto) {
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

	@Override
	public boolean isSecured() {
		return false;
	}
}

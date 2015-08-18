/**
 * 
 */
package org.javahispano.javaleague.shared.dispatch.register;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * @author alfonso
 *
 */
public class RegisterAction extends ActionImpl<RegisterResult> {
	private String email;
	private String userName;
	private String password;
	
	protected RegisterAction() {
		
	}
	
	public RegisterAction(String email, String userName, String password) {
		this.email = email;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

    @Override
    public boolean isSecured() {
        return false;
    }
}

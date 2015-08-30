/*
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.javahispano.javaleague.shared.dispatch.login;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

public class LogInAction extends ActionImpl<LogInResult> {
	private ActionType actionType;
	private String password;
	private String email;
	private String loggedInCookie;

	protected LogInAction() {
	}

	public LogInAction(String email, String password) {
		actionType = ActionType.VIA_CREDENTIALS;
		this.password = password;
		this.email = email;
	}

	public LogInAction(String loggedInCookie) {
		actionType = ActionType.VIA_COOKIE;
		this.loggedInCookie = loggedInCookie;
	}

	@Override
	public boolean isSecured() {
		return false;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public String getPassword() {
		return password;
	}

	public String getLoggedInCookie() {
		return loggedInCookie;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

}

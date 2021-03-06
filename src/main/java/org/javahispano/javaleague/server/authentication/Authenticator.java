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

package org.javahispano.javaleague.server.authentication;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import org.javahispano.javaleague.server.dao.UserDao;
import org.javahispano.javaleague.server.dao.UserSessionDao;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.shared.dto.CurrentUserDto;
import org.javahispano.javaleague.shared.dto.UserDto;

public class Authenticator {
	private final UserDao userDao;
	private final Provider<HttpSession> sessionProvider;
	private final PasswordSecurity passwordSecurity;
	private final CurrentUserDtoProvider currentUserDtoProvider;
	private final UserSessionDao userSessionDao;

	@Inject
	Authenticator(UserDao userDao, Provider<HttpSession> sessionProvider,
			PasswordSecurity passwordSecurity,
			CurrentUserDtoProvider currentUserDtoProvider,
			UserSessionDao userSessionDao) {
		this.userDao = userDao;
		this.sessionProvider = sessionProvider;
		this.passwordSecurity = passwordSecurity;
		this.currentUserDtoProvider = currentUserDtoProvider;
		this.userSessionDao = userSessionDao;
	}

	public UserDto authenticateCredentials(String email, String password) {
		try {
			User user = userDao.findByEmail(email);

			if ((user != null)
					&& (user.isActive())
					&& (passwordSecurity
							.check(password, user.getHashPassword()))) {
				UserDto userDto = User.createDto(user);
				persistHttpSessionCookie(userDto);

				return userDto;
			} else {
				throw new AuthenticationException();
			}
		} catch (Exception e) {
			throw new AuthenticationException();
		}
	}

	public UserDto authenticatCookie(String loggedInCookie)
			throws AuthenticationException {
		UserDto userDto = userSessionDao.getUserFromCookie(loggedInCookie);

		if (userDto == null) {
			throw new AuthenticationException();
		} else {
			persistHttpSessionCookie(userDto);
		}

		return userDto;
	}

	public void logout() {
		removeCurrentUserLoginCookie();

		HttpSession httpSession = sessionProvider.get();
		httpSession.invalidate();
	}

	/**
	 * Session support has to be enabled in the appengine-web.xml.
	 */
	private void persistHttpSessionCookie(UserDto user) {
		HttpSession session = sessionProvider.get();
		session.setAttribute(SecurityParameters.getUserSessionKey(),
				user.getId());
	}

	public Boolean isUserLoggedIn() {
		HttpSession session = sessionProvider.get();
		Long userId = (Long) session.getAttribute(SecurityParameters
				.getUserSessionKey());

		return userId != null;
	}

	public Long getUserSessionKey() {
		HttpSession session = sessionProvider.get();

		return (Long) session.getAttribute(SecurityParameters
				.getUserSessionKey());
	}

	private void removeCurrentUserLoginCookie() {
		CurrentUserDto currentUserDto = currentUserDtoProvider.get();
		UserDto userDto = currentUserDto.getUser();
		userSessionDao.removeLoggedInCookie(userDto);
	}
}

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

package org.javahispano.javaleague.client.application.login;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.NewCookie;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.event.ActionBarVisibilityEvent;
import org.javahispano.javaleague.client.application.event.DisplayMessageEvent;
import org.javahispano.javaleague.client.application.event.UserLoginEvent;
import org.javahispano.javaleague.client.application.widget.message.Message;
import org.javahispano.javaleague.client.application.widget.message.MessageStyle;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.place.ParameterTokens;
import org.javahispano.javaleague.client.resources.LoginMessages;
import org.javahispano.javaleague.client.security.CurrentUser;
import org.javahispano.javaleague.shared.api.ApiParameters;
import org.javahispano.javaleague.shared.api.SessionResource;
import org.javahispano.javaleague.shared.dispatch.login.ActionType;
import org.javahispano.javaleague.shared.dispatch.login.LogInAction;
import org.javahispano.javaleague.shared.dispatch.login.LogInResult;
import org.javahispano.javaleague.shared.dispatch.register.RegisterAction;
import org.javahispano.javaleague.shared.dispatch.register.RegisterResult;
import org.javahispano.javaleague.shared.dto.CurrentUserDto;
import org.javahispano.javaleague.shared.dto.UserDto;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest.Builder;

public class LoginPresenter extends
		Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> implements
		LoginUiHandlers {

	interface MyView extends View, HasUiHandlers<LoginUiHandlers> {
		void setLoginButtonEnabled(boolean enabled);
		void setFormLoginReset();
		void setFormRegisterReset();
	}

	@ProxyStandard
	@NameToken(NameTokens.LOGIN)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	private static final Logger LOGGER = Logger.getLogger(LoginPresenter.class
			.getName());
	private static final int REMEMBER_ME_DAYS = 14;

	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;
	private final ResourceDelegate<SessionResource> sessionResource;
	private final CurrentUser currentUser;
	private final LoginMessages messages;

	@Inject
	LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			PlaceManager placeManager, DispatchAsync dispatcher,
			ResourceDelegate<SessionResource> sessionResource,
			CurrentUser currentUser, LoginMessages messages) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		this.sessionResource = sessionResource;
		this.currentUser = currentUser;
		this.messages = messages;

		getView().setUiHandlers(this);
	}

	@Override
	public void login(String email, String password) {
		LogInAction logInAction = new LogInAction(email, password);
		callServerLoginAction(logInAction);
	}

	@Override
	protected void onReveal() {
		ActionBarVisibilityEvent.fire(this, false);

		if (!Strings.isNullOrEmpty(getLoggedInCookie())) {
			tryLoggingInWithCookieFirst();
		}
	}

	private void callServerLoginAction(LogInAction logInAction) {
		dispatcher.execute(logInAction, new AsyncCallback<LogInResult>() {
			@Override
			public void onFailure(Throwable e) {
				DisplayMessageEvent.fire(LoginPresenter.this, new Message(
						messages.unableToContactServer(), MessageStyle.ERROR));

				LOGGER.log(
						Level.SEVERE,
						"callServerLoginAction(): Server failed to process login call.",
						e);
			}

			@Override
			public void onSuccess(LogInResult result) {
				if (result.getCurrentUserDto().isLoggedIn()) {
					setLoggedInCookie(result.getLoggedInCookie());
				}

				if (result.getActionType() == ActionType.VIA_COOKIE) {
					onLoginCallSucceededForCookie(result.getCurrentUserDto());
				} else {
					onLoginCallSucceeded(result.getCurrentUserDto());
				}
				
				getView().setFormLoginReset();
			}
		});
	}

	private void onLoginCallSucceededForCookie(CurrentUserDto currentUserDto) {
		getView().setLoginButtonEnabled(true);

		if (currentUserDto.isLoggedIn()) {
			onLoginCallSucceeded(currentUserDto);
		}
	}

	private void onLoginCallSucceeded(CurrentUserDto currentUserDto) {
		if (currentUserDto.isLoggedIn()) {
			currentUser.fromCurrentUserDto(currentUserDto);

			redirectToLoggedOnPage();

			UserLoginEvent.fire(this);
			DisplayMessageEvent.fire(this,
					new Message(messages.onSuccessfulLogin(),
							MessageStyle.SUCCESS));
		} else {
			DisplayMessageEvent.fire(this,
					new Message(messages.invalidEmailOrPassword(),
							MessageStyle.ERROR));
		}
	}

	private void redirectToLoggedOnPage() {
		String token = placeManager.getCurrentPlaceRequest().getParameter(
				ParameterTokens.REDIRECT, NameTokens.getOnLoginDefaultPage());
		PlaceRequest placeRequest = new Builder().nameToken(token).build();

		placeManager.revealPlace(placeRequest);
	}

	private void setLoggedInCookie(String value) {
		String path = "/";
		String domain = getDomain();
		int maxAge = REMEMBER_ME_DAYS * 24 * 60 * 60 * 1000;
		boolean secure = false;

		NewCookie newCookie = new NewCookie(ApiParameters.LOGIN_COOKIE, value,
				path, domain, "", maxAge, secure);
		sessionResource.withoutCallback().rememberMe(newCookie);

		LOGGER.info("LoginPresenter.setLoggedInCookie() Set client cookie="
				+ value);
	}

	private String getDomain() {
		String domain = GWT.getHostPageBaseURL().replaceAll(".*//", "")
				.replaceAll("/", "").replaceAll(":.*", "");

		return "localhost".equalsIgnoreCase(domain) ? null : domain;
	}

	private void tryLoggingInWithCookieFirst() {
		getView().setLoginButtonEnabled(false);
		LogInAction logInAction = new LogInAction(getLoggedInCookie());
		callServerLoginAction(logInAction);
	}

	private String getLoggedInCookie() {
		return Cookies.getCookie(ApiParameters.LOGIN_COOKIE);
	}

	@Override
	public void registerUser(String username, String password, String email) {
		RegisterAction registerAction = new RegisterAction(username, password,
				email);
		callServerRegisterAction(registerAction);
	}

	private void callServerRegisterAction(RegisterAction registerAction) {
		dispatcher.execute(registerAction, new AsyncCallback<RegisterResult>() {
			@Override
			public void onFailure(Throwable e) {
				DisplayMessageEvent.fire(LoginPresenter.this, new Message(
						messages.unableToContactServer(), MessageStyle.ERROR));

				LOGGER.log(
						Level.SEVERE,
						"callServerRegisterAction(): Server failed to process login call.",
						e);
			}

			@Override
			public void onSuccess(RegisterResult result) {
				if (result.isStatus()) {
					onRegisterCallSucceededOK(result.getUserDto());
					getView().setFormRegisterReset();
				} else {
					onRegisterCallSucceededKO(result.getUserDto());
				}
			}
		});
	}
	
	private void onRegisterCallSucceededOK(UserDto userDto) {
		DisplayMessageEvent.fire(LoginPresenter.this,
				new Message(messages.onSuccessfulRegister(),
						MessageStyle.SUCCESS));
		LOGGER.log(Level.INFO,
				"onRegisterCallSucceededOK(): User registration OK! Email: "
						+ userDto.getEmail());
	}

	private void onRegisterCallSucceededKO(UserDto userDto) {
		DisplayMessageEvent.fire(LoginPresenter.this,
				new Message(messages.duplicateEmailForRegister(),
						MessageStyle.ERROR));
		LOGGER.log(
				Level.WARNING,
				"onRegisterCallSuccededKO(): Email duplicate: "
						+ userDto.getEmail());
	}
}

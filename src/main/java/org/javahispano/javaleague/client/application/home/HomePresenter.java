/**
 * 
 */
package org.javahispano.javaleague.client.application.home;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.NewCookie;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.event.ActionBarVisibilityEvent;
import org.javahispano.javaleague.client.application.event.DisplayMessageEvent;
import org.javahispano.javaleague.client.application.event.UserLoginEvent;
import org.javahispano.javaleague.client.application.home.HomePresenter.MyProxy;
import org.javahispano.javaleague.client.application.home.HomePresenter.MyView;
import org.javahispano.javaleague.client.application.login.LoginPresenter;
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
import org.javahispano.javaleague.shared.dto.CurrentUserDto;

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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest.Builder;

/**
 * @author alfonso
 *
 */
public class HomePresenter extends Presenter<MyView, MyProxy> implements
		HomeUiHandlers {

	interface MyView extends View, HasUiHandlers<HomeUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.HOME)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	private static final Logger LOGGER = Logger.getLogger(HomePresenter.class
			.getName());
    private static final int REMEMBER_ME_DAYS = 14;

	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;
	private final ResourceDelegate<SessionResource> sessionResource;
	private final CurrentUser currentUser;
	private final LoginMessages messages;

	@Inject
	HomePresenter(EventBus eventBus, MyView view, MyProxy proxy,
			PlaceManager placeManager, DispatchAsync dispatcher,
			ResourceDelegate<SessionResource> sessionResource,
			CurrentUser currentUser,
			LoginMessages messages) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		this.sessionResource = sessionResource;
		this.currentUser = currentUser;
		this.messages = messages;

		getView().setUiHandlers(this);
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
                DisplayMessageEvent.fire(HomePresenter.this, new Message(messages.unableToContactServer(),
                        MessageStyle.ERROR));

                LOGGER.log(Level.SEVERE, "callServerLoginAction(): Server failed to process login call.", e);
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
            }
        });
    }

    private void onLoginCallSucceededForCookie(CurrentUserDto currentUserDto) {

        if (currentUserDto.isLoggedIn()) {
            onLoginCallSucceeded(currentUserDto);
        }
    }

    private void onLoginCallSucceeded(CurrentUserDto currentUserDto) {
        if (currentUserDto.isLoggedIn()) {
            currentUser.fromCurrentUserDto(currentUserDto);

            //redirectToLoggedOnPage();

            UserLoginEvent.fire(this);
            //DisplayMessageEvent.fire(this, new Message(messages.onSuccessfulLogin(), MessageStyle.SUCCESS));
        } else {
            DisplayMessageEvent.fire(this, new Message(messages.invalidEmailOrPassword(), MessageStyle.ERROR));
        }
    }

    private void redirectToLoggedOnPage() {
        String token = placeManager
                .getCurrentPlaceRequest()
                .getParameter(ParameterTokens.REDIRECT, NameTokens.getOnLoginDefaultPage());
        PlaceRequest placeRequest = new Builder().nameToken(token).build();

        placeManager.revealPlace(placeRequest);
    }

    private void setLoggedInCookie(String value) {
        String path = "/";
        String domain = getDomain();
        int maxAge = REMEMBER_ME_DAYS * 24 * 60 * 60 * 1000;
        boolean secure = false;

        NewCookie newCookie = new NewCookie(ApiParameters.LOGIN_COOKIE, value, path, domain, "", maxAge, secure);
        sessionResource.withoutCallback().rememberMe(newCookie);

        LOGGER.info("LoginPresenter.setLoggedInCookie() Set client cookie=" + value);
    }

    private String getDomain() {
        String domain = GWT.getHostPageBaseURL()
                .replaceAll(".*//", "")
                .replaceAll("/", "")
                .replaceAll(":.*", "");

        return "localhost".equalsIgnoreCase(domain) ? null : domain;
    }

    private void tryLoggingInWithCookieFirst() {
        LogInAction logInAction = new LogInAction(getLoggedInCookie());
        callServerLoginAction(logInAction);
    }

    private String getLoggedInCookie() {
        return Cookies.getCookie(ApiParameters.LOGIN_COOKIE);
    }

}

/**
 * 
 */
package org.javahispano.javaleague.client.application.home;

import java.util.logging.Logger;

import org.javahispano.javaleague.client.application.home.HomePresenter.MyProxy;
import org.javahispano.javaleague.client.application.home.HomePresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.security.CurrentUser;
import org.javahispano.javaleague.shared.api.SessionResource;

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

	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;
	private final ResourceDelegate<SessionResource> sessionResource;
	private final CurrentUser currentUser;

	@Inject
	HomePresenter(EventBus eventBus, MyView view, MyProxy proxy,
			PlaceManager placeManager, DispatchAsync dispatcher,
			ResourceDelegate<SessionResource> sessionResource,
			CurrentUser currentUser) {
		super(eventBus, view, proxy, RevealType.RootLayout);

		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		this.sessionResource = sessionResource;
		this.currentUser = currentUser;

		getView().setUiHandlers(this);
	}

}

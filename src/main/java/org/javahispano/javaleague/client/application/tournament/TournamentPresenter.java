/**
 * 
 */
package org.javahispano.javaleague.client.application.tournament;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.tournament.TournamentPresenter.MyProxy;
import org.javahispano.javaleague.client.application.tournament.TournamentPresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.security.CurrentUser;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 * @author alfonso
 *
 */
public class TournamentPresenter extends Presenter<MyView, MyProxy> implements
		TournamentUiHandlers {
	interface MyView extends View, HasUiHandlers<TournamentUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.TOURNAMENT)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<TournamentPresenter> {
	}

	private static final Logger LOGGER = Logger
			.getLogger(TournamentPresenter.class.getName());

	private final DispatchAsync dispatcher;
	private final CurrentUser currentUser;

	@Inject
	TournamentPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			DispatchAsync dispatcher, CurrentUser currentUser) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

		this.dispatcher = dispatcher;
		this.currentUser = currentUser;
	}
}

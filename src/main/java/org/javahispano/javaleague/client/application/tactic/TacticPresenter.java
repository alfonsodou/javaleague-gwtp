/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.tactic.TacticPresenter.MyProxy;
import org.javahispano.javaleague.client.application.tactic.TacticPresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.security.CurrentUser;
import org.javahispano.javaleague.shared.api.UserResource;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 * @author alfonso
 *
 */
public class TacticPresenter extends Presenter<MyView, MyProxy> implements TacticUiHandlers {
	interface MyView extends View, HasUiHandlers<TacticUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.TACTIC)
	interface MyProxy extends ProxyPlace<TacticPresenter> {
	}
	
	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;
	private final ResourceDelegate<UserResource> userResource;
	private final CurrentUser currentUser;

	@Inject
	TacticPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			PlaceManager placeManager, DispatchAsync dispatcher,
			ResourceDelegate<UserResource> userResource,
			CurrentUser currentUser) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);
		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		this.userResource = userResource;
		this.currentUser = currentUser;
		
		getView().setUiHandlers(this);
	}
}
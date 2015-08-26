/**
 * 
 */
package org.javahispano.javaleague.client.application.register;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.register.RegisterPresenter.MyProxy;
import org.javahispano.javaleague.client.application.register.RegisterPresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
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
public class RegisterPresenter extends Presenter<MyView, MyProxy> implements RegisterUiHandlers {
	interface MyView extends View, HasUiHandlers<RegisterUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.CONFIRM_REGISTER)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<RegisterPresenter> {
	}
	
	private final PlaceManager placeManager;

	@Inject
	RegisterPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			PlaceManager placeManager) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

		this.placeManager = placeManager;
		
		getView().setUiHandlers(this);
	}
}

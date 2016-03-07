/**
 * 
 */
package org.javahispano.javaleague.client.application.documentation;

import java.util.logging.Logger;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.documentation.DocumentationPresenter.MyProxy;
import org.javahispano.javaleague.client.application.documentation.DocumentationPresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
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
 * @author adou
 *
 */
public class DocumentationPresenter extends Presenter<MyView, MyProxy>
		implements DocumentationUiHandlers {

	interface MyView extends View, HasUiHandlers<DocumentationUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.DOCUMENTATION)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<DocumentationPresenter> {
	}

	private static final Logger LOGGER = Logger
			.getLogger(DocumentationPresenter.class.getName());

	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;

	@Inject
	DocumentationPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			PlaceManager placeManager, DispatchAsync dispatcher) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

		this.placeManager = placeManager;
		this.dispatcher = dispatcher;

		getView().setUiHandlers(this);
	}

};

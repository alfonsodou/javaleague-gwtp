/**
 * 
 */
package org.javahispano.javaleague.client.application.rules;

import java.util.logging.Logger;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.rules.RulesPresenter.MyProxy;
import org.javahispano.javaleague.client.application.rules.RulesPresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

/**
 * @author adou
 *
 */
public class RulesPresenter extends Presenter<MyView, MyProxy> implements
		RulesUiHandlers {

	interface MyView extends View, HasUiHandlers<RulesUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.RULES)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<RulesPresenter> {
	}

	private static final Logger LOGGER = Logger.getLogger(RulesPresenter.class
			.getName());

	@Inject
	RulesPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

		getView().setUiHandlers(this);
	}
}

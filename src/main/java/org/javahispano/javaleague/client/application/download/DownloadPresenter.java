/**
 * 
 */
package org.javahispano.javaleague.client.application.download;

import java.util.logging.Logger;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.download.DownloadPresenter.MyProxy;
import org.javahispano.javaleague.client.application.download.DownloadPresenter.MyView;
import org.javahispano.javaleague.client.place.NameTokens;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author adou
 *
 */
public class DownloadPresenter extends Presenter<MyView, MyProxy> implements
		DownloadUiHandlers {

	interface MyView extends View, HasUiHandlers<DownloadUiHandlers> {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.DOWNLOAD)
	@NoGatekeeper
	interface MyProxy extends ProxyPlace<DownloadPresenter> {
	}

	private static final Logger LOGGER = Logger
			.getLogger(DownloadPresenter.class.getName());

	@Inject
	DownloadPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);
		
		getView().setUiHandlers(this);
	}
}

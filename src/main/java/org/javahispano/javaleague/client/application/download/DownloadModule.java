/**
 * 
 */
package org.javahispano.javaleague.client.application.download;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author adou
 *
 */
public class DownloadModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindPresenter(DownloadPresenter.class, DownloadPresenter.MyView.class,
				DownloadView.class, DownloadPresenter.MyProxy.class);

		bind(DownloadUiHandlers.class).to(DownloadPresenter.class);
	}
}

/**
 * 
 */
package org.javahispano.javaleague.client.application.home;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author alfonso
 *
 */
public class HomeModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindPresenter(HomePresenter.class, HomePresenter.MyView.class,
				HomeView.class, HomePresenter.MyProxy.class);

		bind(HomeUiHandlers.class).to(HomePresenter.class);
	}

}

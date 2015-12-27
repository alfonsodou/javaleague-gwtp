/**
 * 
 */
package org.javahispano.javaleague.client.application.tournament;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author alfonso
 *
 */
public class TournamentModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindPresenter(TournamentPresenter.class,
				TournamentPresenter.MyView.class, TournamentView.class,
				TournamentPresenter.MyProxy.class);
		
		bind(TournamentUiHandlers.class).to(TournamentPresenter.class);
	}

}

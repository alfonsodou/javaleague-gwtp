/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author alfonso
 *
 */
public class TacticModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindPresenter(TacticPresenter.class, TacticPresenter.MyView.class,
				TacticView.class, TacticPresenter.MyProxy.class);

		bind(TacticUiHandlers.class).to(TacticPresenter.class);
	}

}

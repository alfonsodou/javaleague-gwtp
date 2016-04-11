/**
 * 
 */
package org.javahispano.javaleague.client.application.rules;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author adou
 *
 */
public class RulesModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindPresenter(RulesPresenter.class, RulesPresenter.MyView.class,
				RulesView.class, RulesPresenter.MyProxy.class);

		bind(RulesUiHandlers.class).to(RulesPresenter.class);
	}
}
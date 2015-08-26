package org.javahispano.javaleague.client.application.register;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class RegisterModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindPresenter(RegisterPresenter.class, RegisterPresenter.MyView.class,
				RegisterView.class, RegisterPresenter.MyProxy.class);

		bind(RegisterUiHandlers.class).to(RegisterPresenter.class);
	}

}

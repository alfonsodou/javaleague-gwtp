/**
 * 
 */
package org.javahispano.javaleague.client.application.documentation;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

/**
 * @author adou
 *
 */
public class DocumentationModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bindPresenter(DocumentationPresenter.class,
				DocumentationPresenter.MyView.class, DocumentationView.class,
				DocumentationPresenter.MyProxy.class);

		bind(DocumentationUiHandlers.class).to(DocumentationPresenter.class);
	}

}

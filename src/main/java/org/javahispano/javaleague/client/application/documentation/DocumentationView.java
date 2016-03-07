/**
 * 
 */
package org.javahispano.javaleague.client.application.documentation;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author adou
 *
 */
public class DocumentationView extends
		ViewWithUiHandlers<DocumentationUiHandlers> implements
		DocumentationPresenter.MyView {

	interface Binder extends UiBinder<Widget, DocumentationView> {
	}

	@Inject
	DocumentationView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}

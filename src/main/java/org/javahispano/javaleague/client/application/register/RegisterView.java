/**
 * 
 */
package org.javahispano.javaleague.client.application.register;

import org.javahispano.javaleague.client.application.home.HomeView;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author alfonso
 *
 */
public class RegisterView extends ViewWithUiHandlers<RegisterUiHandlers>
		implements RegisterPresenter.MyView {

	interface Binder extends UiBinder<Widget, RegisterView> {
	}

	@Inject
	RegisterView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}

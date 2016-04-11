/**
 * 
 */
package org.javahispano.javaleague.client.application.rules;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author adou
 *
 */
public class RulesView extends ViewWithUiHandlers<RulesUiHandlers> implements
		RulesPresenter.MyView {

	interface Binder extends UiBinder<Widget, RulesView> {
	}

	@Inject
	RulesView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}

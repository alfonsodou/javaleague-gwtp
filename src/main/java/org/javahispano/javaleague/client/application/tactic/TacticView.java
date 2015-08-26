/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author alfonso
 *
 */
public class TacticView extends ViewWithUiHandlers<TacticUiHandlers> implements
		TacticPresenter.MyView {

	interface Binder extends UiBinder<Widget, TacticView> {
	}

	@Inject
	TacticView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}

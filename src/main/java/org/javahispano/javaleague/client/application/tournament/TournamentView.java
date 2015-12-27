/**
 * 
 */
package org.javahispano.javaleague.client.application.tournament;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author alfonso
 *
 */
public class TournamentView extends ViewWithUiHandlers<TournamentUiHandlers>
		implements TournamentPresenter.MyView {

	interface Binder extends UiBinder<Widget, TournamentView> {
	}

	@Inject
	TournamentView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}

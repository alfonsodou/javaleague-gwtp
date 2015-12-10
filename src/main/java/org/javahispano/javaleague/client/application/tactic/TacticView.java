/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import gwtupload.client.SingleUploader;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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

	@UiField
	SingleUploader singleUploader;
	@UiField
	TextBox teamName;
	@UiField
	Label packageName;
	@UiField
	Label packageNameUser;
	@UiField
	Button playGame;

	@Inject
	TacticView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));

		teamName.getElement().setAttribute("placeholder", "Nombre Equipo");
	}
	
	@Override
	public SingleUploader getSingleUploader() {
		return singleUploader;
	}

	@Override
	public TextBox getTeamName() {
		return teamName;
	}

	@UiHandler("updateTactic")
	void onClickUpdteTactic(ClickEvent e) {
		doUpdateTactic();
	}

	@UiHandler("playGame")
	void onClickPlayGame(ClickEvent e) {
		doPlayGame();
	}

	private void doPlayGame() {
		getUiHandlers().playGame();
	}

	private void doUpdateTactic() {
		getUiHandlers().updateTeamNameTactic(teamName.getValue());
	}

	@Override
	public Label getPackageName() {
		return packageName;
	}

	@Override
	public Label getPackageNameUser() {
		return packageNameUser;
	}

	@Override
	public Button getPlayGame() {
		return playGame;
	}
}

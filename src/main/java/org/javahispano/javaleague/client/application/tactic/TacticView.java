/**
 * 
 */
package org.javahispano.javaleague.client.application.tactic;

import gwtupload.client.MultiUploader;
import gwtupload.client.SingleUploader;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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

	/*@UiField
	FormPanel formPanelTactic;*/
	@UiField
	SingleUploader singleUploader;

	@Inject
	TacticView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));

	}

	/*@Override
	public FormPanel getFormPanelTactic() {
		return formPanelTactic;
	}*/

	@Override
	public SingleUploader getSingleUploader() {
		return singleUploader;
	}

}

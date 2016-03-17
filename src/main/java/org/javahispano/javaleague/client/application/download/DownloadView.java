/**
 * 
 */
package org.javahispano.javaleague.client.application.download;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author adou
 *
 */
public class DownloadView extends ViewWithUiHandlers<DownloadUiHandlers> implements
DownloadPresenter.MyView {

	interface Binder extends UiBinder<Widget, DownloadView> {
	}

	@Inject
	DownloadView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}

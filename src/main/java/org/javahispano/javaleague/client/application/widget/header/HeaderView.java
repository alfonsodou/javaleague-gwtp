/*
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.javahispano.javaleague.client.application.widget.header;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.ListDropDown;
import org.javahispano.javaleague.client.resources.WidgetResources;
import org.javahispano.javaleague.client.security.CurrentUser;
import org.javahispano.javaleague.shared.dto.UserDto;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class HeaderView extends ViewWithUiHandlers<HeaderUiHandlers> implements
		HeaderPresenter.MyView {
	interface Binder extends UiBinder<Widget, HeaderView> {
	}

	private final WidgetResources widgetRes;

	@UiField
	AnchorListItem login;
	@UiField
	AnchorListItem tactic;
	@UiField
	ListDropDown userLogin;
	@UiField
	AnchorButton userName;

	@Inject
	HeaderView(Binder uiBinder, WidgetResources widgetResources) {
		this.widgetRes = widgetResources;

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void enableUserOptions(CurrentUser currentUser) {
		UserDto userDto = currentUser.getUser();
		login.setVisible(false);
		tactic.setVisible(true);
		userLogin.setVisible(true);
		userName.setText(userDto.getUserName());
	}

	@Override
	public void disableUserOptions() {
		login.setVisible(true);
		tactic.setVisible(false);
		userLogin.setVisible(false);
		userName.setText("");

	}

	@Override
	public void setMenuItemActive(String nameToken) {
	}

	@UiHandler("logout")
	void onLogoutClicked(ClickEvent event) {
		getUiHandlers().logout();
	}
}

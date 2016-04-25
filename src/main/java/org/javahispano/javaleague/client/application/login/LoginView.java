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

package org.javahispano.javaleague.client.application.login;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.form.error.BasicEditorError;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class LoginView extends ViewWithUiHandlers<LoginUiHandlers> implements LoginPresenter.MyView {
	interface Binder extends UiBinder<Widget, LoginView> {
	}

	@UiField
	Button login;
	@UiField
	Input password;
	@UiField
	TextBox email;
	@UiField
	Form formLogin;
	@UiField
	Form formRegister;
	@UiField
	TextBox emailRegister;
	@UiField
	TextBox confirmEmail;
	@UiField
	TextBox userName;
	@UiField
	Input passwordRegister;
	@UiField
	Input confirmPassword;
	@UiField
	Button register;

	@Inject
	LoginView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));

		email.getElement().setAttribute("placeholder", "Email");
		password.getElement().setAttribute("placeholder", "Contraseña");

		email.addValidator(new org.gwtbootstrap3.client.ui.form.validator.RegExValidator(
				"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$", "Email no válido"));

		password.addValidator(new org.gwtbootstrap3.client.ui.form.validator.SizeValidator<String>(4, 12,
				"La contraseña debe tener entre 4 y 12 caracteres"));

		emailRegister.addValidator(new org.gwtbootstrap3.client.ui.form.validator.RegExValidator(
				"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$", "Email no válido"));

		passwordRegister.addValidator(new org.gwtbootstrap3.client.ui.form.validator.SizeValidator<String>(4, 12,
				"La contraseña debe tener entre 4 y 12 caracteres"));

		confirmEmail.addValidator(new org.gwtbootstrap3.client.ui.form.validator.RegExValidator(
				"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$", "Email no válido"));

		confirmPassword.addValidator(new org.gwtbootstrap3.client.ui.form.validator.SizeValidator<String>(4, 12,
				"La contraseña debe tener entre 4 y 12 caracteres"));

		passwordRegister.addValidator(new org.gwtbootstrap3.client.ui.form.validator.Validator<String>() {

			@Override
			public int getPriority() {
				return Priority.MEDIUM;
			}

			@Override
			public List<EditorError> validate(Editor<String> editor, String value) {
				List<EditorError> result = new ArrayList<EditorError>();
				String valueStr = value == null ? "" : value.toString();
				if (!confirmPassword.getText().equals(valueStr)) {
					result.add(new BasicEditorError(passwordRegister, value, "Las contraseñas no coinciden"));
				}

				return result;
			}

		});

		emailRegister.addValidator(new org.gwtbootstrap3.client.ui.form.validator.Validator<String>() {

			@Override
			public int getPriority() {
				return Priority.MEDIUM;
			}

			@Override
			public List<EditorError> validate(Editor<String> editor, String value) {
				List<EditorError> result = new ArrayList<EditorError>();
				String valueStr = value == null ? "" : value.toString();
				if (!confirmEmail.getText().equals(valueStr)) {
					result.add(new BasicEditorError(emailRegister, value, "El email no coincide"));
				}

				return result;
			}
		});

		confirmPassword.addValidator(new org.gwtbootstrap3.client.ui.form.validator.Validator<String>() {

			@Override
			public int getPriority() {
				return Priority.MEDIUM;
			}

			@Override
			public List<EditorError> validate(Editor<String> editor, String value) {
				List<EditorError> result = new ArrayList<EditorError>();
				String valueStr = value == null ? "" : value.toString();
				if (!passwordRegister.getText().equals(valueStr)) {
					result.add(new BasicEditorError(confirmPassword, value, "Las contraseñas no coinciden"));
				}

				return result;
			}

		});

		confirmEmail.addValidator(new org.gwtbootstrap3.client.ui.form.validator.Validator<String>() {

			@Override
			public int getPriority() {
				return Priority.MEDIUM;
			}

			@Override
			public List<EditorError> validate(Editor<String> editor, String value) {
				List<EditorError> result = new ArrayList<EditorError>();
				String valueStr = value == null ? "" : value.toString();
				if (!emailRegister.getText().equals(valueStr)) {
					result.add(new BasicEditorError(confirmEmail, value, "El email no coincide"));
				}

				return result;
			}

		});

		emailRegister.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				formRegister.validate();
			}
		});

		passwordRegister.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				formRegister.validate();
			}

		});

		confirmEmail.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				formRegister.validate();
			}
		});

		confirmPassword.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				formRegister.validate();
			}

		});
	}

	@Override
	public void setLoginButtonEnabled(boolean enabled) {
		login.setEnabled(enabled);
	}

	@Override
	public void setFormLoginReset() {
		formLogin.reset();
	}

	@Override
	public void setFormRegisterReset() {
		formRegister.reset();
	}

	@UiHandler("login")
	void onLoginClicked(ClickEvent event) {
		processLogin();
	}

	@UiHandler("password")
	void onPasswordKeyUp(KeyUpEvent event) {
		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			processLogin();
		}
	}

	@UiHandler("register")
	void onRegisterClicked(ClickEvent event) {
		processRegister();
	}

	private void processLogin() {
		getUiHandlers().login(email.getValue(), password.getValue());
	}

	private void processRegister() {
		getUiHandlers().registerUser(userName.getValue(), passwordRegister.getValue(), emailRegister.getValue());
	}

	@Override
	public TextBox getEmail() {
		return email;
	}

}

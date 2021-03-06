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

package org.javahispano.javaleague.client.application.cars.car.widget;

import javax.inject.Inject;

import org.javahispano.javaleague.shared.dto.CarPropertiesDto;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class CarPropertiesEditor extends Composite implements Editor<CarPropertiesDto> {
    interface Binder extends UiBinder<Widget, CarPropertiesEditor> {
    }

    @UiField
    TextBox someString;
    @UiField
    IntegerBox someNumber;
    @UiField
    DateBox someDate;

    @Inject
    CarPropertiesEditor(
            Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));

        someString.getElement().setAttribute("placeholder", "Property #1");
        someNumber.getElement().setAttribute("placeholder", "Property #2");
        someDate.getElement().setAttribute("placeholder", "Property #3");
    }
}

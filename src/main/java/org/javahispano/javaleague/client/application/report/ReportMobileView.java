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

package org.javahispano.javaleague.client.application.report;

import java.util.List;

import javax.inject.Inject;

import org.javahispano.javaleague.client.application.report.renderer.ReportCell;
import org.javahispano.javaleague.client.resources.MobileDataListStyle;
import org.javahispano.javaleague.shared.dto.ManufacturerRatingDto;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.gwtplatform.mvp.client.ViewImpl;

public class ReportMobileView extends ViewImpl implements ReportPresenter.MyView {
    interface Binder extends UiBinder<Widget, ReportMobileView> {
    }

    @UiField(provided = true)
    CellList<ManufacturerRatingDto> reportList;

    private final ListDataProvider<ManufacturerRatingDto> ratingsProvider;

    @Inject
    ReportMobileView(
            Binder uiBinder,
            ReportCell reportCell,
            MobileDataListStyle listStyle) {
        reportList = new CellList<>(reportCell, listStyle);

        initWidget(uiBinder.createAndBindUi(this));

        ratingsProvider = new ListDataProvider<>();
        ratingsProvider.addDataDisplay(reportList);
    }

    @Override
    public void displayReport(List<ManufacturerRatingDto> manufacturerRatings) {
        ratingsProvider.getList().clear();
        ratingsProvider.getList().addAll(manufacturerRatings);
    }
}

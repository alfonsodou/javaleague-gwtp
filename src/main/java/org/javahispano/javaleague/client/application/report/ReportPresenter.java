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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.event.ActionBarVisibilityEvent;
import org.javahispano.javaleague.client.application.event.ChangeActionBarEvent;
import org.javahispano.javaleague.client.application.event.ChangeActionBarEvent.ActionType;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.util.AbstractAsyncCallback;
import org.javahispano.javaleague.shared.api.ManufacturersResource;
import org.javahispano.javaleague.shared.dto.ManufacturerRatingDto;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class ReportPresenter extends Presenter<ReportPresenter.MyView, ReportPresenter.MyProxy> {
    interface MyView extends View {
        void displayReport(List<ManufacturerRatingDto> manufacturerRatings);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.REPORT)
    interface MyProxy extends ProxyPlace<ReportPresenter> {
    }

    private final ResourceDelegate<ManufacturersResource> manufacturersDelegate;

    @Inject
    ReportPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            ResourceDelegate<ManufacturersResource> manufacturersDelegate) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

        this.manufacturersDelegate = manufacturersDelegate;
    }

    @Override
    protected void onReveal() {
        ActionBarVisibilityEvent.fire(this, true);
        ChangeActionBarEvent.fire(this, new ArrayList<ActionType>(), true);

        manufacturersDelegate
                .withCallback(new AbstractAsyncCallback<List<ManufacturerRatingDto>>() {
                    @Override
                    public void onSuccess(List<ManufacturerRatingDto> manufacturerRatings) {
                        getView().displayReport(manufacturerRatings);
                    }
                })
                .getAverageRatings();
    }
}
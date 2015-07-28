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

package org.javahispano.javaleague.client.application.manufacturer;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.event.ActionBarEvent;
import org.javahispano.javaleague.client.application.event.ActionBarVisibilityEvent;
import org.javahispano.javaleague.client.application.event.ChangeActionBarEvent;
import org.javahispano.javaleague.client.application.event.ChangeActionBarEvent.ActionType;
import org.javahispano.javaleague.client.application.manufacturer.ManufacturerPresenter.MyProxy;
import org.javahispano.javaleague.client.application.manufacturer.ManufacturerPresenter.MyView;
import org.javahispano.javaleague.client.application.manufacturer.event.ManufacturerAddedEvent;
import org.javahispano.javaleague.client.application.manufacturer.ui.EditManufacturerPresenter;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.util.AbstractAsyncCallback;
import org.javahispano.javaleague.client.util.ErrorHandlerAsyncCallback;
import org.javahispano.javaleague.shared.api.ManufacturersResource;
import org.javahispano.javaleague.shared.dto.ManufacturerDto;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest.Builder;

public class ManufacturerPresenter extends Presenter<MyView, MyProxy>
        implements ManufacturerUiHandlers, ActionBarEvent.ActionBarHandler {

    interface MyView extends View, HasUiHandlers<ManufacturerUiHandlers> {
        void addManufacturer(ManufacturerDto manufacturerDto);

        void displayManufacturers(List<ManufacturerDto> manufacturerDtos);

        void removeManufacturer(ManufacturerDto manufacturerDto);

        void replaceManufacturer(ManufacturerDto oldManufacturer, ManufacturerDto newManufacturer);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.MANUFACTURER)
    interface MyProxy extends ProxyPlace<ManufacturerPresenter> {
    }

    private final PlaceManager placeManager;
    private final ResourceDelegate<ManufacturersResource> manufacturersDelegate;
    private final EditManufacturerPresenter editManufacturerPresenter;

    private ManufacturerDto editingManufacturer;

    @Inject
    ManufacturerPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            PlaceManager placeManager,
            ResourceDelegate<ManufacturersResource> manufacturersDelegate,
            EditManufacturerPresenter editManufacturerPresenter) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

        this.placeManager = placeManager;
        this.manufacturersDelegate = manufacturersDelegate;
        this.editManufacturerPresenter = editManufacturerPresenter;

        getView().setUiHandlers(this);
    }

    @Override
    public void onActionEvent(ActionBarEvent event) {
        if (event.getActionType() == ActionType.ADD && event.isTheSameToken(NameTokens.getManufacturer())) {
            placeManager.revealPlace(new Builder().nameToken(NameTokens.getDetailManufacturer()).build());
        }
    }

    @Override
    public void onDetail(ManufacturerDto manufacturerDto) {
        PlaceRequest placeRequest = new Builder().nameToken(NameTokens.getDetailManufacturer())
                .with("id", String.valueOf(manufacturerDto.getId()))
                .build();

        placeManager.revealPlace(placeRequest);
    }

    @Override
    public void onEdit(ManufacturerDto manufacturerDto) {
        editingManufacturer = manufacturerDto;
        editManufacturerPresenter.edit(manufacturerDto);
    }

    @Override
    public void onCreate() {
        editingManufacturer = null;
        editManufacturerPresenter.createNew();
    }

    @Override
    public void onDelete(final ManufacturerDto manufacturerDto) {
        manufacturersDelegate
                .withCallback(new ErrorHandlerAsyncCallback<Void>(this) {
                    @Override
                    public void onSuccess(Void nothing) {
                        getView().removeManufacturer(manufacturerDto);
                    }
                })
                .delete(manufacturerDto.getId());
    }

    @Override
    protected void onReveal() {
        ActionBarVisibilityEvent.fire(this, true);
        ChangeActionBarEvent.fire(this, Arrays.asList(ActionType.ADD), true);

        manufacturersDelegate
                .withCallback(new AbstractAsyncCallback<List<ManufacturerDto>>() {
                    @Override
                    public void onSuccess(List<ManufacturerDto> manufacturers) {
                        getView().displayManufacturers(manufacturers);
                    }
                })
                .getManufacturers();
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(ActionBarEvent.getType(), this);
    }

    @ProxyEvent
    void onManufacturerAdded(ManufacturerAddedEvent event) {
        if (editingManufacturer != null) {
            getView().replaceManufacturer(editingManufacturer, event.getManufacturer());
        } else {
            getView().addManufacturer(event.getManufacturer());
        }

        editingManufacturer = event.getManufacturer();
    }
}

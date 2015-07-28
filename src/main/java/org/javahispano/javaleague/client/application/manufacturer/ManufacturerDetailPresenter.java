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
import org.javahispano.javaleague.client.application.event.ChangeActionBarEvent;
import org.javahispano.javaleague.client.application.event.ChangeActionBarEvent.ActionType;
import org.javahispano.javaleague.client.application.event.DisplayMessageEvent;
import org.javahispano.javaleague.client.application.event.GoBackEvent;
import org.javahispano.javaleague.client.application.manufacturer.ManufacturerDetailPresenter.MyProxy;
import org.javahispano.javaleague.client.application.manufacturer.ManufacturerDetailPresenter.MyView;
import org.javahispano.javaleague.client.application.widget.message.Message;
import org.javahispano.javaleague.client.application.widget.message.MessageStyle;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.resources.EditManufacturerMessages;
import org.javahispano.javaleague.client.util.AbstractAsyncCallback;
import org.javahispano.javaleague.client.util.ErrorHandlerAsyncCallback;
import org.javahispano.javaleague.shared.api.ManufacturersResource;
import org.javahispano.javaleague.shared.dto.ManufacturerDto;

import com.google.common.base.Strings;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest.Builder;

public class ManufacturerDetailPresenter extends Presenter<MyView, MyProxy>
        implements GoBackEvent.GoBackHandler, ActionBarEvent.ActionBarHandler, ManufacturerDetailUiHandlers {

    interface MyView extends View, HasUiHandlers<ManufacturerDetailUiHandlers> {
        void edit(ManufacturerDto manufacturerDto);

        void getManufacturer();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.DETAIL_MANUFACTURER)
    interface MyProxy extends ProxyPlace<ManufacturerDetailPresenter> {
    }

    private final ResourceDelegate<ManufacturersResource> manufacturersDelegate;
    private final PlaceManager placeManager;
    private final EditManufacturerMessages messages;

    private ManufacturerDto currentManufacturer;
    private Boolean createNew;

    @Inject
    ManufacturerDetailPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            ResourceDelegate<ManufacturersResource> manufacturersDelegate,
            PlaceManager placeManager,
            EditManufacturerMessages messages) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

        this.manufacturersDelegate = manufacturersDelegate;
        this.placeManager = placeManager;
        this.messages = messages;

        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        String param = request.getParameter("id", null);
        createNew = Strings.isNullOrEmpty(param);

        if (!createNew) {
            Long id = Long.parseLong(param);
            manufacturersDelegate
                    .withCallback(new AbstractAsyncCallback<ManufacturerDto>() {
                        @Override
                        public void onSuccess(ManufacturerDto manufacturer) {
                            currentManufacturer = manufacturer;
                            getView().edit(currentManufacturer);
                        }
                    })
                    .get(id);
        } else {
            currentManufacturer = new ManufacturerDto();
            getView().edit(currentManufacturer);
        }
    }

    @Override
    public void onGoBack(GoBackEvent event) {
        placeManager.revealPlace(new Builder().nameToken(NameTokens.getManufacturer()).build());
    }

    @Override
    public void onActionEvent(ActionBarEvent event) {
        if (event.isTheSameToken(NameTokens.getDetailManufacturer())) {
            switch (event.getActionType()) {
                case UPDATE:
                    getView().getManufacturer();
                    break;
                case DONE:
                    getView().getManufacturer();
                    break;
                case DELETE:
                    deleteManufacturer();
                    break;
            }
        }
    }

    @Override
    public void onSave(ManufacturerDto manufacturerDto) {
        manufacturersDelegate

                .withCallback(new ErrorHandlerAsyncCallback<ManufacturerDto>(this) {
                    @Override
                    public void onSuccess(ManufacturerDto savedManufacturerDto) {
                        DisplayMessageEvent.fire(ManufacturerDetailPresenter.this,
                                new Message(messages.manufacturerSaved(), MessageStyle.SUCCESS));
                        placeManager.revealPlace(new Builder().nameToken(NameTokens.getManufacturer()).build());
                    }
                })
                .saveOrCreate(manufacturerDto);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(GoBackEvent.getType(), this);
        addRegisteredHandler(ActionBarEvent.getType(), this);
    }

    @Override
    protected void onReveal() {
        List<ActionType> actions;
        if (createNew) {
            actions = Arrays.asList(ActionType.DONE);
            ChangeActionBarEvent.fire(this, actions, false);
        } else {
            actions = Arrays.asList(ActionType.DELETE, ActionType.UPDATE);
            ChangeActionBarEvent.fire(this, actions, false);
        }
    }

    private void deleteManufacturer() {
        Boolean confirm = Window.confirm("Are you sure you want to delete " + currentManufacturer.getName() + "?");
        if (confirm) {
            manufacturersDelegate
                    .withCallback(new ErrorHandlerAsyncCallback<Void>(this) {
                        @Override
                        public void onSuccess(Void nothing) {
                            placeManager.revealPlace(new Builder().nameToken(NameTokens.getManufacturer()).build());
                        }
                    })
                    .delete(currentManufacturer.getId());
        }
    }
}

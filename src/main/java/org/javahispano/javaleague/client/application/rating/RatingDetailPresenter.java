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

package org.javahispano.javaleague.client.application.rating;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.javahispano.javaleague.client.application.ApplicationPresenter;
import org.javahispano.javaleague.client.application.event.ActionBarEvent;
import org.javahispano.javaleague.client.application.event.ChangeActionBarEvent;
import org.javahispano.javaleague.client.application.event.ChangeActionBarEvent.ActionType;
import org.javahispano.javaleague.client.application.event.DisplayMessageEvent;
import org.javahispano.javaleague.client.application.event.GoBackEvent;
import org.javahispano.javaleague.client.application.rating.RatingDetailPresenter.MyProxy;
import org.javahispano.javaleague.client.application.rating.RatingDetailPresenter.MyView;
import org.javahispano.javaleague.client.application.widget.message.Message;
import org.javahispano.javaleague.client.application.widget.message.MessageStyle;
import org.javahispano.javaleague.client.place.NameTokens;
import org.javahispano.javaleague.client.resources.EditRatingMessages;
import org.javahispano.javaleague.client.util.AbstractAsyncCallback;
import org.javahispano.javaleague.client.util.ErrorHandlerAsyncCallback;
import org.javahispano.javaleague.shared.api.CarsResource;
import org.javahispano.javaleague.shared.api.RatingResource;
import org.javahispano.javaleague.shared.dto.CarDto;
import org.javahispano.javaleague.shared.dto.RatingDto;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.delegates.client.ResourceDelegate;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest.Builder;

public class RatingDetailPresenter extends Presenter<MyView, MyProxy>
        implements RatingDetailUiHandlers, ActionBarEvent.ActionBarHandler, GoBackEvent.GoBackHandler {

    interface MyView extends View, HasUiHandlers<RatingDetailUiHandlers> {
        void edit(RatingDto ratingDto);

        void setAllowedCars(List<CarDto> carDtos);

        void getRating();
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.DETAIL_RATING)
    interface MyProxy extends ProxyPlace<RatingDetailPresenter> {
    }

    private final ResourceDelegate<CarsResource> carsDelegate;
    private final ResourceDelegate<RatingResource> ratingDelegate;
    private final EditRatingMessages messages;
    private final PlaceManager placeManager;

    @Inject
    RatingDetailPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            ResourceDelegate<CarsResource> carsDelegate,
            ResourceDelegate<RatingResource> ratingDelegate,
            EditRatingMessages messages,
            PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN_CONTENT);

        this.carsDelegate = carsDelegate;
        this.ratingDelegate = ratingDelegate;
        this.messages = messages;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void onGoBack(GoBackEvent event) {
        placeManager.revealPlace(new Builder().nameToken(NameTokens.getRating()).build());
    }

    @Override
    public void onActionEvent(ActionBarEvent event) {
        if (event.getActionType() == ActionType.DONE && event.isTheSameToken(NameTokens.getDetailRating())) {
            getView().getRating();
        }
    }

    @Override
    public void onSave(RatingDto ratingDto) {
        ratingDelegate
                .withCallback(new ErrorHandlerAsyncCallback<RatingDto>(this) {
                    @Override
                    public void onSuccess(RatingDto savedRating) {
                        DisplayMessageEvent.fire(RatingDetailPresenter.this, new Message(messages.ratingSaved(),
                                MessageStyle.SUCCESS));
                        placeManager.revealPlace(new Builder().nameToken(NameTokens.getRating()).build());
                    }
                })
                .saveOrCreate(ratingDto);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(GoBackEvent.getType(), this);
        addRegisteredHandler(ActionBarEvent.getType(), this);
    }

    @Override
    protected void onReveal() {
        List<ActionType> actions = Arrays.asList(ActionType.DONE);
        ChangeActionBarEvent.fire(this, actions, false);

        carsDelegate
                .withCallback(new AbstractAsyncCallback<List<CarDto>>() {
                    @Override
                    public void onSuccess(List<CarDto> cars) {
                        onGetCarsSuccess(cars);
                    }
                })
                .getCars(null, 0, CarsResource.LIMIT_ALL);
    }

    private void onGetCarsSuccess(List<CarDto> carDtos) {
        getView().setAllowedCars(carDtos);
        getView().edit(new RatingDto());
    }
}

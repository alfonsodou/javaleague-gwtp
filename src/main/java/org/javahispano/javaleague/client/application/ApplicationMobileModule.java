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

package org.javahispano.javaleague.client.application;

import org.javahispano.javaleague.client.application.cars.CarsMobileModule;
import org.javahispano.javaleague.client.application.login.LoginMobileModule;
import org.javahispano.javaleague.client.application.manufacturer.ManufacturerMobileModule;
import org.javahispano.javaleague.client.application.rating.RatingMobileModule;
import org.javahispano.javaleague.client.application.report.ReportMobileModule;
import org.javahispano.javaleague.client.application.stats.StatisticsModule;
import org.javahispano.javaleague.client.application.widget.WidgetModule;
import org.javahispano.javaleague.client.application.widget.message.MessagesModule;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationMobileModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new UnauthorizedModule());
        install(new LoginMobileModule());
        install(new ManufacturerMobileModule());
        install(new CarsMobileModule());
        install(new RatingMobileModule());
        install(new WidgetModule());
        install(new ReportMobileModule());

        // TODO should we make a messaging module for mobile
        install(new MessagesModule());

        install(new StatisticsModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationMobileView.class,
                ApplicationPresenter.MyProxy.class);
    }
}

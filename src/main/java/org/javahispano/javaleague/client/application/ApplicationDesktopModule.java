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

import org.javahispano.javaleague.client.application.cars.CarsDesktopModule;
import org.javahispano.javaleague.client.application.home.HomeModule;
import org.javahispano.javaleague.client.application.login.LoginModule;
import org.javahispano.javaleague.client.application.manufacturer.ManufacturerModule;
import org.javahispano.javaleague.client.application.rating.RatingModule;
import org.javahispano.javaleague.client.application.register.RegisterModule;
import org.javahispano.javaleague.client.application.report.ReportModule;
import org.javahispano.javaleague.client.application.stats.StatisticsModule;
import org.javahispano.javaleague.client.application.tactic.TacticModule;
import org.javahispano.javaleague.client.application.tournament.TournamentModule;
import org.javahispano.javaleague.client.application.widget.WidgetModule;
import org.javahispano.javaleague.client.application.widget.message.MessagesModule;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationDesktopModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		install(new UnauthorizedModule());
		install(new LoginModule());
		install(new ManufacturerModule());
		install(new CarsDesktopModule());
		install(new RatingModule());
		install(new WidgetModule());
		install(new MessagesModule());
		install(new ReportModule());
		install(new StatisticsModule());
		install(new HomeModule());
		install(new RegisterModule());
		install(new TacticModule());
		install(new TournamentModule());

		bindPresenter(ApplicationPresenter.class,
				ApplicationPresenter.MyView.class, ApplicationView.class,
				ApplicationPresenter.MyProxy.class);
	}
}

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

package org.javahispano.javaleague.server.guice;

import javax.inject.Singleton;

import org.javahispano.javaleague.server.servlet.AuthenticateUserServlet;
import org.javahispano.javaleague.server.servlet.ClasificationServlet;
import org.javahispano.javaleague.server.servlet.DispatchMatchServlet;
import org.javahispano.javaleague.server.servlet.FrameworkServlet;
import org.javahispano.javaleague.server.servlet.GWTUploadTacticServlet;
import org.javahispano.javaleague.server.servlet.ImageServlet;
import org.javahispano.javaleague.server.servlet.PlayMatchServlet;
import org.javahispano.javaleague.server.servlet.ServeMatchBinServlet;
import org.javahispano.javaleague.server.servlet.ServeMatchServlet;
import org.javahispano.javaleague.server.servlet.TournamentServlet;
import org.javahispano.javaleague.shared.api.ApiPaths;

import com.arcbees.guicyresteasy.GuiceRestEasyFilterDispatcher;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {
	@Override
	public void configureServlets() {
		filter("/*").through(ObjectifyFilter.class);
		filter(ApiPaths.ROOT + "/*").through(
				GuiceRestEasyFilterDispatcher.class);

		serve("/" + ActionImpl.DEFAULT_SERVICE_NAME + "*").with(
				DispatchServiceImpl.class);

		serve("/authenticate").with(AuthenticateUserServlet.class);

		serve("/frameworkServlet").with(FrameworkServlet.class);
		bind(FrameworkServlet.class).in(Singleton.class);
		
		serve("/cron/dispatchMatchServlet").with(DispatchMatchServlet.class);
		bind(DispatchMatchServlet.class).in(Singleton.class);

		serve("/playMatchServlet").with(PlayMatchServlet.class);
		bind(PlayMatchServlet.class).in(Singleton.class);

		serve("/serveMatchBinServlet").with(ServeMatchBinServlet.class);
		bind(ServeMatchBinServlet.class).in(Singleton.class);

		serve("/serveMatchServlet").with(ServeMatchServlet.class);
		bind(ServeMatchServlet.class).in(Singleton.class);

		serve("/tournamentServlet").with(TournamentServlet.class);
		bind(TournamentServlet.class).in(Singleton.class);
		
		serve("/imageTransform").with(ImageServlet.class);
		bind(ImageServlet.class).in(Singleton.class);
		
		serve("/clasificationServlet").with(ClasificationServlet.class);
		bind(ClasificationServlet.class).in(Singleton.class);
		
		bind(GWTUploadTacticServlet.class).in(Singleton.class);
		serve("*.gupld").with(GWTUploadTacticServlet.class);
	}
}

/*
 * Copyright 2014 ArcBees Inc.
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

package org.javahispano.javaleague.server.dispatch;

import org.javahispano.javaleague.shared.dispatch.clasification.ListClasificationAction;
import org.javahispano.javaleague.shared.dispatch.journey.ListJourneyAction;
import org.javahispano.javaleague.shared.dispatch.login.LogInAction;
import org.javahispano.javaleague.shared.dispatch.match.ListFinalMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchAction;
import org.javahispano.javaleague.shared.dispatch.match.ListMatchLeagueAction;
import org.javahispano.javaleague.shared.dispatch.match.RegisterMatchAction;
import org.javahispano.javaleague.shared.dispatch.register.RegisterAction;
import org.javahispano.javaleague.shared.dispatch.tactic.UpdateTacticAction;
import org.javahispano.javaleague.shared.dispatch.time.GetServerTimeAction;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

public class DispatchModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        install(new com.gwtplatform.dispatch.rpc.server.guice.DispatchModule());

        bindHandler(LogInAction.class, LogInHandler.class);
        bindHandler(RegisterAction.class, RegisterHandler.class);
        bindHandler(UpdateTacticAction.class, UpdateTacticHandler.class);
        bindHandler(RegisterMatchAction.class, RegisterMatchHandler.class);
        bindHandler(ListMatchAction.class, ListMatchHandler.class);
        bindHandler(ListMatchLeagueAction.class, ListMatchLeagueHandler.class);
        bindHandler(ListClasificationAction.class, ListClasificationHandler.class);
        bindHandler(ListJourneyAction.class, ListJourneyHandler.class);
        bindHandler(GetServerTimeAction.class, GetServerTimeHandler.class);
        bindHandler(ListFinalMatchAction.class, ListFinalMatchHandler.class);
    }
}

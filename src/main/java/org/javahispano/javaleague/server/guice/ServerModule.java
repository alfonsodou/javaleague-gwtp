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

import org.javahispano.javaleague.server.DevBootStrapper;
import org.javahispano.javaleague.server.api.ApiModule;
import org.javahispano.javaleague.server.authentication.BCryptPasswordSecurity;
import org.javahispano.javaleague.server.authentication.PasswordSecurity;
import org.javahispano.javaleague.server.dispatch.DispatchModule;

import com.google.inject.AbstractModule;
import com.googlecode.objectify.ObjectifyFilter;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ApiModule());
        install(new DispatchModule());

        bind(PasswordSecurity.class).to(BCryptPasswordSecurity.class).in(Singleton.class);
        bind(ObjectifyFilter.class).in(Singleton.class);
        bind(DevBootStrapper.class).asEagerSingleton();
    }
}

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

package org.javahispano.javaleague.server.api;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Cookie;

import org.javahispano.javaleague.server.authentication.Authenticator;
import org.javahispano.javaleague.server.authentication.CurrentUserDtoProvider;
import org.javahispano.javaleague.shared.api.SessionResource;
import org.javahispano.javaleague.shared.dto.CurrentUserDto;

public class SessionResourceImpl implements SessionResource {
    private final Logger logger;
    private final Authenticator authenticator;
    private final CurrentUserDtoProvider currentUserDtoProvider;

    @Inject
    SessionResourceImpl(
            Logger logger,
            Authenticator authenticator,
            CurrentUserDtoProvider currentUserDtoProvider) {
        this.logger = logger;
        this.authenticator = authenticator;
        this.currentUserDtoProvider = currentUserDtoProvider;
    }

    @Override
    public CurrentUserDto getCurrentUser() {
        return currentUserDtoProvider.get();
    }

    @Override
    public void logout() {
        authenticator.logout();
    }

    @Override
    public void rememberMe(Cookie cookie) {
        logger.info("Remember me: " + String.valueOf(cookie));
    }
}

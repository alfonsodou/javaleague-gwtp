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

package org.javahispano.javaleague.client.application.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class UserLoginEvent extends GwtEvent<UserLoginEvent.UserLoginHandler> {
    public interface UserLoginHandler extends EventHandler {
        void onLogin(UserLoginEvent event);
    }

    private static final Type<UserLoginHandler> TYPE = new Type<>();

    UserLoginEvent() {
    }

    public static Type<UserLoginHandler> getType() {
        return TYPE;
    }

    public static void fire(HasHandlers source) {
        source.fireEvent(new UserLoginEvent());
    }

    @Override
    public Type<UserLoginHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserLoginHandler handler) {
        handler.onLogin(this);
    }
}

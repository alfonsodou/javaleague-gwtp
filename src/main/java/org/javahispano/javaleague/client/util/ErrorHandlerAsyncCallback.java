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

package org.javahispano.javaleague.client.util;

import org.javahispano.javaleague.client.application.event.DisplayMessageEvent;
import org.javahispano.javaleague.client.application.widget.message.Message;
import org.javahispano.javaleague.client.application.widget.message.MessageStyle;
import org.javahispano.javaleague.client.util.exceptiontranslators.ForeignTranslator;
import org.javahispano.javaleague.client.util.exceptiontranslators.NotNullTranslator;
import org.javahispano.javaleague.client.util.exceptiontranslators.Translator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.http.client.Response;
import com.gwtplatform.dispatch.rest.client.RestCallback;

public abstract class ErrorHandlerAsyncCallback<R> implements RestCallback<R> {
    private final HasHandlers hasHandlers;

    public ErrorHandlerAsyncCallback(
            HasHandlers hasHandlers) {
        this.hasHandlers = hasHandlers;
    }

    @Override
    public void onFailure(Throwable caught) {
        Message message = new Message(translateCauses(caught), MessageStyle.ERROR);
        DisplayMessageEvent.fire(hasHandlers, message);
    }

    @Override
    public void setResponse(Response response) {
        GWT.log("HTTP " + response.getStatusCode() + ": " + response.getStatusText());
    }

    private String translateCauses(Throwable caught) {
        StringBuilder sb = new StringBuilder(translateCause(caught));

        for (Throwable t = caught.getCause(); t != null; t = t.getCause()) {
            sb = sb.append(translateCause(t)).append("<br />");
        }

        return sb.toString();
    }

    private String translateCause(Throwable caught) {
        String message = caught.getMessage();

        Translator translator = new NotNullTranslator(message);
        if (translator.isMatching()) {
            return translator.getTranslatedMessage();
        }

        translator = new ForeignTranslator(message);
        if (translator.isMatching()) {
            return translator.getTranslatedMessage();
        }

        return message;
    }
}

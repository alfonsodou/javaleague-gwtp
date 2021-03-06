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

package org.javahispano.javaleague.client.application.widget;

import org.javahispano.javaleague.client.application.widget.footer.FooterPresenter;
import org.javahispano.javaleague.client.application.widget.footer.FooterUiHandlers;
import org.javahispano.javaleague.client.application.widget.footer.FooterView;
import org.javahispano.javaleague.client.application.widget.header.HeaderPresenter;
import org.javahispano.javaleague.client.application.widget.header.HeaderUiHandlers;
import org.javahispano.javaleague.client.application.widget.header.HeaderView;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class WidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindSingletonPresenterWidget(HeaderPresenter.class, HeaderPresenter.MyView.class,
                HeaderView.class);
        bindSingletonPresenterWidget(FooterPresenter.class, FooterPresenter.MyView.class,
                FooterView.class);

        bind(HeaderUiHandlers.class).to(HeaderPresenter.class);
        bind(FooterUiHandlers.class).to(FooterPresenter.class);
    }
}

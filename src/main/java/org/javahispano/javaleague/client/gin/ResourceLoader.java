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

package org.javahispano.javaleague.client.gin;

import org.javahispano.javaleague.client.resources.AppResources;
import org.javahispano.javaleague.client.resources.WidgetResources;

import com.google.inject.Inject;

public class ResourceLoader {
    @Inject
    ResourceLoader(
            AppResources resources,
            WidgetResources widgetResources) {
        resources.styles().ensureInjected();
        widgetResources.header().ensureInjected();
    }
}

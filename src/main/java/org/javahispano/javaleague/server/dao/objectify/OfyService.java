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

package org.javahispano.javaleague.server.dao.objectify;

import org.javahispano.javaleague.server.dao.domain.Car;
import org.javahispano.javaleague.server.dao.domain.CarProperties;
import org.javahispano.javaleague.server.dao.domain.Clasification;
import org.javahispano.javaleague.server.dao.domain.FinalMatch;
import org.javahispano.javaleague.server.dao.domain.Journey;
import org.javahispano.javaleague.server.dao.domain.League;
import org.javahispano.javaleague.server.dao.domain.Manufacturer;
import org.javahispano.javaleague.server.dao.domain.Match;
import org.javahispano.javaleague.server.dao.domain.MatchProperties;
import org.javahispano.javaleague.server.dao.domain.Rating;
import org.javahispano.javaleague.server.dao.domain.RegisterMatch;
import org.javahispano.javaleague.server.dao.domain.User;
import org.javahispano.javaleague.server.dao.domain.UserSession;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
    static {
        factory().register(Car.class);
        factory().register(CarProperties.class);
        factory().register(Manufacturer.class);
        factory().register(Rating.class);
        factory().register(User.class);
        factory().register(UserSession.class);
        factory().register(RegisterMatch.class);
        factory().register(Match.class);;
        factory().register(MatchProperties.class);
        factory().register(League.class);
        factory().register(Clasification.class);
        factory().register(Journey.class);
        factory().register(FinalMatch.class);
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }
}

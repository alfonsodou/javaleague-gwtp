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

package org.javahispano.javaleague.server.api;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.CarDao;
import org.javahispano.javaleague.server.dao.domain.Car;
import org.javahispano.javaleague.shared.api.CarResource;
import org.javahispano.javaleague.shared.dto.CarDto;

import com.google.inject.assistedinject.Assisted;

public class CarResourceImpl implements CarResource {
    private final CarDao carDao;
    private final Long carId;

    @Inject
    CarResourceImpl(
            CarDao carDao,
            @Assisted Long carId) {
        this.carDao = carDao;
        this.carId = carId;
    }

    @Override
    public CarDto get() {
        return Car.createDto(carDao.get(carId));
    }

    @Override
    public void delete() {
        carDao.delete(carId);
    }
}

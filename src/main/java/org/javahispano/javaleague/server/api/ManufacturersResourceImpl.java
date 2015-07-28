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

import java.util.List;

import javax.inject.Inject;

import org.javahispano.javaleague.server.dao.ManufacturerDao;
import org.javahispano.javaleague.server.dao.RatingDao;
import org.javahispano.javaleague.server.dao.domain.Manufacturer;
import org.javahispano.javaleague.server.dao.domain.Rating;
import org.javahispano.javaleague.server.service.ReportService;
import org.javahispano.javaleague.shared.api.ManufacturersResource;
import org.javahispano.javaleague.shared.dto.ManufacturerDto;
import org.javahispano.javaleague.shared.dto.ManufacturerRatingDto;
import org.javahispano.javaleague.shared.dto.RatingDto;

public class ManufacturersResourceImpl implements ManufacturersResource {
    private final ManufacturerDao manufacturerDao;
    private final RatingDao ratingDao;
    private final ReportService reportService;

    @Inject
    ManufacturersResourceImpl(
            ManufacturerDao manufacturerDao,
            RatingDao ratingDao,
            ReportService reportService) {
        this.manufacturerDao = manufacturerDao;
        this.ratingDao = ratingDao;
        this.reportService = reportService;
    }

    @Override
    public List<ManufacturerDto> getManufacturers() {
        return Manufacturer.createDto(manufacturerDao.getAll());
    }

    @Override
    public ManufacturerDto get(Long id) {
        return Manufacturer.createDto(manufacturerDao.get(id));
    }

    @Override
    public ManufacturerDto saveOrCreate(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerDao.put(Manufacturer.create(manufacturerDto));

        return Manufacturer.createDto(manufacturer);
    }

    @Override
    public void delete(Long id) {
        manufacturerDao.delete(id);
    }

    @Override
    public List<ManufacturerRatingDto> getAverageRatings() {
        List<Rating> ratings = ratingDao.getAll();
        List<RatingDto> ratingDtos = Rating.createDto(ratings);

        return reportService.getAverageCarRatings(ratingDtos);
    }
}

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

package org.javahispano.javaleague.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ManufacturerRatingDto implements Dto {
    private String manufacturer;
    private Double rating;

    public ManufacturerRatingDto() {
    }

    public ManufacturerRatingDto(
            @JsonProperty("manufacturer") String manufacturer,
            @JsonProperty("rating") Double rating) {
        this.manufacturer = manufacturer;
        this.rating = rating;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Double getRating() {
        return rating;
    }
}
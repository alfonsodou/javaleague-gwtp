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

package org.javahispano.javaleague.client.application.rating.ui;

import org.javahispano.javaleague.client.columninitializer.Column;
import org.javahispano.javaleague.client.columninitializer.ColumnInitializer;
import org.javahispano.javaleague.client.columninitializer.ColumnsDefinition;
import org.javahispano.javaleague.shared.dto.RatingDto;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;

@ColumnsDefinition(definitionFor = RatingDto.class)
public interface RatingColumnsDefinition extends ColumnInitializer<RatingDto> {
    @Column(cellType = NumberCell.class, cellReturnType = Number.class, headerName = "ID")
    String getId();

    @Column(cellType = TextCell.class, cellReturnType = String.class, headerName = "Car")
    String toString();

    @Column(cellType = NumberCell.class, cellReturnType = Number.class, headerName = "Rating")
    String getRating();
}

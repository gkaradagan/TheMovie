/*
 * Copyright 2021 Görkem Karadoğan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gorkem.popularmovie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gorkem.common.data.model.GenreResponseModel
import com.gorkem.common.domain.model.GenreDomainModel

@Entity(tableName = "movie_genre")
data class MovieGenreEntity(@PrimaryKey val id: Int, val name: String, val languageCode: String)

fun MovieGenreEntity.mapToDomainModel() = GenreDomainModel(
    id = this.id,
    name = this.name
)

fun GenreResponseModel.mapToEntityModel(languageCode: String) = MovieGenreEntity(
    id = this.id,
    name = this.name,
    languageCode = languageCode
)

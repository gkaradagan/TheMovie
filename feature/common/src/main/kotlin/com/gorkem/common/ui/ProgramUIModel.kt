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
package com.gorkem.common.ui

import com.gorkem.common.data.local.entity.FavouriteProgramEntity

data class PopularUIModel(
    val page: Int,
    val results: List<ProgramUIModel>,
    val totalPages: Int,
)

data class ProgramUIModel(
    val genreList: List<String>,
    val id: Int,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
) {
    var isFavourite = false
}

fun ProgramUIModel.mapToEntity() = FavouriteProgramEntity(
    genreList = this.genreList,
    id = this.id,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate,
    title = this.title,
    voteAverage = this.voteAverage
)

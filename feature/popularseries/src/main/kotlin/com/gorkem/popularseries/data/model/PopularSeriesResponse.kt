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
package com.gorkem.popularseries.data.model

import com.gorkem.common.domain.model.GenreDomainModel
import com.gorkem.common.ui.PopularUIModel
import com.gorkem.common.ui.ProgramUIModel
import com.squareup.moshi.Json

data class PopularSeriesResponse(
    val page: Int,
    val results: List<TvShowResponseModel>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
)

data class TvShowResponseModel(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "first_air_date")
    val firstAirDate: String,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    val name: String,
    @Json(name = "origin_country")
    val originCountry: List<String>,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_name")
    val originalName: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
)

fun PopularSeriesResponse.mapToUIModel(genreList: List<GenreDomainModel>): PopularUIModel =
    PopularUIModel(
        page = this.page,
        results = this.results.filter { it.posterPath != null }.map { it.mapToUIModel(genreList) },
        totalPages = this.totalPages
    )

fun TvShowResponseModel.mapToUIModel(genreList: List<GenreDomainModel>): ProgramUIModel =
    ProgramUIModel(
        genreList = this.genreIds.map { id -> genreList.first { it.id == id } }
            .map { it.name },
        id = this.id,
        posterPath = this.posterPath,
        releaseDate = this.firstAirDate,
        title = this.name,
        voteAverage = this.voteAverage,
    )

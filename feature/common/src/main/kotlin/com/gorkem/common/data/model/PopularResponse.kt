package com.gorkem.common.data.model

import com.gorkem.common.domain.model.GenreDomainModel
import com.gorkem.common.ui.ProgramUIModel
import com.squareup.moshi.Json

data class PopularResponse(
    val page: Int,
    val results: List<ProgramResponseModel>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
)

data class ProgramResponseModel(
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
)

fun ProgramResponseModel.mapToUIModel(genreList: List<GenreDomainModel>): ProgramUIModel =
    ProgramUIModel(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreList = this.genreIds.map { id -> genreList.first { it.id == id } }
            .map { it.name },
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )

package com.gorkem.common.ui

data class ProgramUIModel(
    val adult: Boolean,
    val backdropPath: String?,
    val genreList: List<String>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
)

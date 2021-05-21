package com.gorkem.common.data.model

import com.gorkem.common.domain.model.GenreDomainModel

data class GenreResponse(
    val genres: List<GenreResponseModel>,
)

data class GenreResponseModel(
    val id: Int,
    val name: String,
)

fun GenreResponseModel.mapToDomainModel() = GenreDomainModel(
    id = this.id,
    name = this.name
)

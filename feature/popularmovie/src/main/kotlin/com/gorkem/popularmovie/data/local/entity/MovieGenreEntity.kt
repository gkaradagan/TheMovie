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

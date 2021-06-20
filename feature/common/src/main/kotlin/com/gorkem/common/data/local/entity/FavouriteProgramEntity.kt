package com.gorkem.common.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gorkem.common.data.local.FavouriteConverters
import com.gorkem.common.ui.ProgramUIModel

@Entity(tableName = "favourite_program")
@TypeConverters(value = [FavouriteConverters::class])
data class FavouriteProgramEntity(
    val genreList: List<String>,
    @PrimaryKey val id: Int,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
)

fun FavouriteProgramEntity.mapToUIModel() = ProgramUIModel(
    genreList = this.genreList,
    id = this.id,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate,
    title = this.title,
    voteAverage = this.voteAverage
)

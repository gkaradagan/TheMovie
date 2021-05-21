package com.gorkem.popularmovie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorkem.popularmovie.data.local.entity.MovieGenreEntity

@Dao
interface MovieGenreDao {

    @Query("SELECT * FROM movie_genre where languageCode = :languageCode")
    fun getAllGenre(languageCode: String): List<MovieGenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(list: List<MovieGenreEntity>)
}

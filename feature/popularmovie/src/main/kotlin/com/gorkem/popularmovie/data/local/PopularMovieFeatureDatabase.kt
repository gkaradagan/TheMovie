package com.gorkem.popularmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gorkem.popularmovie.data.local.dao.MovieGenreDao
import com.gorkem.popularmovie.data.local.entity.MovieGenreEntity

@Database(entities = [MovieGenreEntity::class], version = 1)
abstract class PopularMovieFeatureDatabase : RoomDatabase() {
    abstract fun movieGenreDao(): MovieGenreDao
}

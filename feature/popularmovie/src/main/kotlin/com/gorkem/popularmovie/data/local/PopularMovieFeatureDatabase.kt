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
package com.gorkem.popularmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gorkem.popularmovie.data.local.dao.MovieGenreDao
import com.gorkem.popularmovie.data.local.entity.MovieGenreEntity

@Database(entities = [MovieGenreEntity::class], version = 1)
abstract class PopularMovieFeatureDatabase : RoomDatabase() {
    abstract fun movieGenreDao(): MovieGenreDao
}

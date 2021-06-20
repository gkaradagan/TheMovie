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
package com.gorkem.popularmovie.di

import android.content.Context
import androidx.room.Room
import com.gorkem.common.data.repository.FavouriteProgramRepository
import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularmovie.data.api.MovieService
import com.gorkem.popularmovie.data.local.PopularMovieFeatureDatabase
import com.gorkem.popularmovie.data.local.dao.MovieGenreDao
import com.gorkem.popularmovie.data.repository.MovieRepository
import com.gorkem.popularmovie.data.repository.MovieRepositoryImpl
import com.gorkem.popularmovie.domain.interactor.PopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class PopularMovieSingletonModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    fun providePopularMovieFeatureDatabase(@ApplicationContext appContext: Context): PopularMovieFeatureDatabase =
        Room.databaseBuilder(
            appContext,
            PopularMovieFeatureDatabase::class.java,
            "PopularMovieFeature"
        ).build()

    @Provides
    fun provideMovieGenreDao(appDatabase: PopularMovieFeatureDatabase): MovieGenreDao =
        appDatabase.movieGenreDao()
}

@Module
@InstallIn(ViewModelComponent::class)
class PopularMovieViewModule {

    @Provides
    fun providePopularMovieUseCase(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        api: MovieRepository,
        favouriteProgramRepository: FavouriteProgramRepository,
    ): PopularMoviesUseCase =
        PopularMoviesUseCase(appCoroutineDispatchers, api, favouriteProgramRepository)

    @Provides
    fun provideMovieRepository(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        api: MovieService,
        movieGenreDao: MovieGenreDao,
    ): MovieRepository =
        MovieRepositoryImpl(appCoroutineDispatchers, api, movieGenreDao)
}

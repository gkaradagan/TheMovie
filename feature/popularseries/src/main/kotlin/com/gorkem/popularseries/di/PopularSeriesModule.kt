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
package com.gorkem.popularseries.di

import android.content.Context
import androidx.room.Room
import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularseries.data.api.TvShowService
import com.gorkem.popularseries.data.local.PopularSeriesFeatureDatabase
import com.gorkem.popularseries.data.local.dao.TvShowGenreDao
import com.gorkem.popularseries.data.repository.TvShowRepository
import com.gorkem.popularseries.data.repository.TvShowRepositoryImpl
import com.gorkem.popularseries.domain.interactor.PopularTvShowsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PopularSeriesSingletonModule {

    @Provides
    @Singleton
    fun provideTvShowService(retrofit: Retrofit): TvShowService =
        retrofit.create(TvShowService::class.java)

    @Provides
    @Singleton
    fun providePopularSeriesFeatureDatabase(@ApplicationContext appContext: Context): PopularSeriesFeatureDatabase =
        Room.databaseBuilder(
            appContext,
            PopularSeriesFeatureDatabase::class.java,
            "PopularSeriesFeature"
        ).build()

    @Provides
    fun provideTvShowGenreDao(appDatabase: PopularSeriesFeatureDatabase): TvShowGenreDao =
        appDatabase.tvShowGenreDao()
}

@Module
@InstallIn(ViewModelComponent::class)
class PopularSeriesViewModule {

    @Provides
    fun providePopularTvShowsUseCase(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        api: TvShowRepository,
    ): PopularTvShowsUseCase =
        PopularTvShowsUseCase(appCoroutineDispatchers, api)

    @Provides
    fun provideTvShowRepository(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        api: TvShowService,
        movieGenreDao: TvShowGenreDao,
    ): TvShowRepository =
        TvShowRepositoryImpl(appCoroutineDispatchers, api, movieGenreDao)
}

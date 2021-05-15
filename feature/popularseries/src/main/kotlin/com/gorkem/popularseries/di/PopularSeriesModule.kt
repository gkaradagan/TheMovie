package com.gorkem.popularseries.di

import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularseries.data.api.TvShowService
import com.gorkem.popularseries.data.repository.TvShowRepository
import com.gorkem.popularseries.data.repository.TvShowRepositoryImpl
import com.gorkem.popularseries.domain.interactor.PopularTvShowsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PopularMovieSingletonModule {

    @Provides
    @Singleton
    fun provideTvShowService(retrofit: Retrofit): TvShowService =
        retrofit.create(TvShowService::class.java)
}

@Module
@InstallIn(ViewModelComponent::class)
class PopularMovieViewModule {

    @Provides
    fun providePopularMovieUseCase(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        api: TvShowRepository,
    ): PopularTvShowsUseCase =
        PopularTvShowsUseCase(appCoroutineDispatchers, api)

    @Provides
    fun provideMovieRepository(api: TvShowService): TvShowRepository =
        TvShowRepositoryImpl(api)
}

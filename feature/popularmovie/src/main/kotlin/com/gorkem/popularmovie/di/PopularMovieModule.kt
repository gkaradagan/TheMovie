package com.gorkem.popularmovie.di

import com.gorkem.core.util.AppCoroutineDispatchers
import com.gorkem.popularmovie.data.api.MovieService
import com.gorkem.popularmovie.data.repository.MovieRepository
import com.gorkem.popularmovie.data.repository.MovieRepositoryImpl
import com.gorkem.popularmovie.domain.interactor.PopularMoviesUseCase
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
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)
}

@Module
@InstallIn(ViewModelComponent::class)
class PopularMovieViewModule {

    @Provides
    fun providePopularMovieUseCase(
        appCoroutineDispatchers: AppCoroutineDispatchers,
        api: MovieRepository,
    ): PopularMoviesUseCase =
        PopularMoviesUseCase(appCoroutineDispatchers, api)

    @Provides
    fun provideMovieRepository(api: MovieService): MovieRepository =
        MovieRepositoryImpl(api)
}
